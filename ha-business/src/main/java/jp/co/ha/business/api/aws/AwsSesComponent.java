package jp.co.ha.business.api.aws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jp.co.ha.business.exception.BusinessErrorCode;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.system.SystemProperties;
import jp.co.ha.common.type.BaseEnum;
import jp.co.ha.common.type.Charset;
import jp.co.ha.common.util.StringUtil;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.Body;
import software.amazon.awssdk.services.ses.model.Content;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.MailFromDomainNotVerifiedException;
import software.amazon.awssdk.services.ses.model.Message;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;
import software.amazon.awssdk.services.ses.model.SendEmailResponse;
import software.amazon.awssdk.services.ses.model.VerifyEmailIdentityRequest;
import software.amazon.awssdk.services.ses.model.VerifyEmailIdentityResponse;

/**
 * AWS-Simple Email ServiceのComponent
 *
 * @version 1.0.0
 */
@Component
public class AwsSesComponent {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(AwsSesComponent.class);
    /** 文字コード */
    private static final Charset CHARSET = Charset.UTF_8;

    /** AWS設定ファイル情報 */
    @Autowired
    private AwsProperties awsConfig;
    /** システム設定ファイル情報 */
    @Autowired
    private SystemProperties systemConfig;
    /** AWS認証情報Component */
    @Autowired
    private AwsAuthComponent awsAuthComponent;
    /** S3 Component */
    @Autowired
    private AwsS3Component awsS3Component;
    /** FreeMarker設定 */
    @Autowired
    private Configuration freemarkerConfig;

    /**
     * 認証結果区分
     *
     * @version 1.0.0
     */
    public static enum VerifyResultType implements BaseEnum {

        /** 認証済 */
        AUTHED("0"),
        /** 未認証 */
        STILL("1");

        /** 値 */
        private String value;

        /**
         * コンストラクタ
         *
         * @param value
         *     値
         */
        private VerifyResultType(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return this.value;
        }

        /**
         * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
         * @param value
         *     値
         * @return VerifyResultType
         */
        public static VerifyResultType of(String value) {
            return BaseEnum.of(VerifyResultType.class, value);
        }

    }

    /**
     * Eメール送信結果区分
     *
     * @version 1.0.0
     */
    public static enum EmailSendResultType implements BaseEnum {

        /** 送信成功 */
        SUCCESS("0"),
        /** 送信失敗 */
        FAILED("1"),
        /** 未送信 */
        NOT_SEND("2");

        /** 値 */
        private String value;

        /**
         * コンストラクタ
         *
         * @param value
         *     値
         */
        private EmailSendResultType(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return this.value;
        }

        /**
         * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
         * @param value
         *     値
         * @return VerifyResultType
         */
        public static EmailSendResultType of(String value) {
            return BaseEnum.of(EmailSendResultType.class, value);
        }
    }

    /**
     * Eメールアドレスの検証を行う
     *
     * @param mailAddress
     *     検証用Eメールアドレス
     * @return 認証結果
     */
    public VerifyResultType verifyEmailAddress(String mailAddress) {

        try (SesClient sesClient = getSesClient()) {
            VerifyEmailIdentityRequest request = VerifyEmailIdentityRequest.builder()
                    .emailAddress(mailAddress)
                    .build();

            VerifyEmailIdentityResponse response = sesClient.verifyEmailIdentity(request);

            VerifyResultType res = StringUtil
                    .isEmpty(response.responseMetadata().requestId())
                            ? VerifyResultType.STILL
                            : VerifyResultType.AUTHED;

            LOG.debug("認証メール送信完了 認証結果=" + res);

            return res;
        }
    }

    /**
     * Eメールを送信する
     *
     * @param to
     *     宛先メールアドレス
     * @param titleText
     *     メール件名
     * @param s3Key
     *     S3キー
     * @return Eメール送信結果区分
     * @throws BaseException
     *     メール送信に失敗した場合
     * @see #sendMail(String, String, String, Map)
     */
    public EmailSendResultType sendMail(String to, String titleText, AwsS3Key s3Key)
            throws BaseException {
        return sendMail(to, titleText, s3Key.getValue(), Collections.emptyMap());
    }

    /**
     * Eメールを送信する
     *
     * @param to
     *     宛先メールアドレス
     * @param titleText
     *     メール件名
     * @param templateId
     *     テンプレートID
     * @return Eメール送信結果区分
     * @throws BaseException
     *     メール送信に失敗した場合
     * @see #sendMail(String, String, String, Map)
     */
    public EmailSendResultType sendMail(String to, String titleText, String templateId)
            throws BaseException {
        return sendMail(to, titleText, templateId, Collections.emptyMap());
    }

    /**
     * Eメールを送信する
     *
     * @param to
     *     宛先メールアドレス
     * @param titleText
     *     メール件名
     * @param s3Key
     *     S3キー
     * @param bodyMap
     *     メール本文を置換するmap
     * @return Eメール送信結果区分
     * @throws BaseException
     *     メール送信に失敗した場合
     */
    public EmailSendResultType sendMail(String to, String titleText, AwsS3Key s3Key,
            Map<String, String> bodyMap) throws BaseException {
        return sendMail(to, titleText, s3Key.getValue(), bodyMap);
    }

    /**
     * Eメールを送信する
     *
     * @param to
     *     宛先メールアドレス
     * @param titleText
     *     メール件名
     * @param templateId
     *     テンプレートID
     * @param bodyMap
     *     メール本文を置換するmap
     * @return Eメール送信結果区分
     * @throws BaseException
     *     メール送信に失敗した場合
     */
    public EmailSendResultType sendMail(String to, String titleText, String templateId,
            Map<String, String> bodyMap) throws BaseException {

        if (awsConfig.isSesStubFlag()) {
            return EmailSendResultType.NOT_SEND;
        }

        try (SesClient sesClient = getSesClient()) {

            LOG.debug("Amazon SES region=" + awsConfig.getRegion().id()
                    + ",to_mail_address=" + to);

            Destination destination = Destination.builder()
                    .toAddresses(to)
                    .bccAddresses(systemConfig.getSystemMailAddress())
                    .build();

            Body body = getBody(templateId, bodyMap);
            Message message = Message.builder()
                    .subject(getContent(titleText))
                    .body(body)
                    .build();

            SendEmailRequest request = SendEmailRequest.builder()
                    .source(systemConfig.getSystemMailAddress())
                    .destination(destination)
                    .message(message)
                    .build();

            SendEmailResponse response = sesClient.sendEmail(request);
            LOG.debug("SES response ID=" + response.messageId());

            return EmailSendResultType.SUCCESS;

        } catch (MailFromDomainNotVerifiedException e) {
            throw new BusinessException(
                    BusinessErrorCode.AWS_SES_MAIL_ADDRESS_VERRIFIED_ERROR,
                    "メールアドレスがSESに認証されていません.管理画面から認証してください", e);
        }
    }

    /**
     * {@linkplain SesClient}を返す
     * 
     * @return SesClient
     */
    private SesClient getSesClient() {

        // HttpClient にタイムアウトを設定する
        SdkHttpClient httpClient = ApacheHttpClient.builder()
                .connectionTimeout(Duration.ofMillis(awsConfig.getSesTimeout()))
                .socketTimeout(Duration.ofMillis(awsConfig.getSesTimeout()))
                .build();

        return SesClient.builder()
                .region(awsConfig.getRegion())
                .credentialsProvider(awsAuthComponent.getAWSCredentialsProvider())
                .httpClient(httpClient)
                .build();
    }

    /**
     * {@linkplain Content}を返す
     *
     * @param text
     *     本文
     * @return Content
     */
    private Content getContent(String text) {
        return Content.builder()
                .charset(CHARSET.getValue())
                .data(text)
                .build();
    }

    /**
     * {@linkplain Body}を返す
     *
     * @param templateId
     *     テンプレートID
     * @param bodyMap
     *     メール本文の置換用Map
     * @return Body
     * @throws BaseException
     *     メールテンプレートの取得に失敗した場合
     */
    private Body getBody(String templateId, Map<String, String> bodyMap)
            throws BaseException {

        if (StringUtil.isEmpty(templateId)) {
            // テンプレートIDが未指定の場合
            // TODO エラーコードは別途発番
            throw new SystemException(CommonErrorCode.RUNTIME_ERROR,
                    "メールテンプレートIDが未指定です。");
        }

        try {
            StringWriter stringWriter = new StringWriter();

            Template template = freemarkerConfig.getTemplate(templateId);
            template.process(bodyMap, stringWriter);

            return Body.builder()
                    .html(getContent(stringWriter.toString()))
                    .build();

        } catch (Exception e) {
            throw new SystemException(e);
        }

    }

    /**
     * {@linkplain Body}を返す
     *
     * @param templateId
     *     テンプレートID
     * @param bodyMap
     *     メール本文の置換用Map
     * @return Body
     * @throws BaseException
     *     メールテンプレートの取得に失敗した場合
     */
    @Deprecated
    private Body getBodyFromS3(String templateId, Map<String, String> bodyMap)
            throws BaseException {

        String bodyText = replace(getBodyTemplateFromS3(templateId), bodyMap);

        return Body.builder()
                .html(getContent(bodyText))
                .build();
    }

    /**
     * 指定されたtemplateファイルのメール本文を返す
     *
     * @param templateId
     *     テンプレートID
     * @return メール本文
     * @throws BaseException
     *     メールテンプレートの取得に失敗した場合
     */
    @Deprecated
    private String getBodyTemplateFromS3(String templateId) throws BaseException {

        try (InputStream is = awsS3Component.getS3ObjectByKey(templateId);
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(is, CHARSET.getValue()))) {

            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();

        } catch (IOException e) {
            throw new SystemException(CommonErrorCode.FILE_OR_DIR_ERROR,
                    "メールテンプレートの取得に失敗しました.templateId=" + templateId, e);
        }
    }

    /**
     * メールテンプレートの置換キー(${key})とそのキーと一致するbodyMapの値で置換する
     *
     * @param bodyText
     *     置換前テンプレート文字列
     * @param bodyMap
     *     置換Map
     * @return 置換後のメールテンプレート
     */
    private String replace(String bodyText, Map<String, String> bodyMap) {

        String text = bodyText;
        for (Entry<String, String> entry : bodyMap.entrySet()) {
            text = text.replace(entry.getKey(), entry.getValue());
        }

        return text;
    }
}
