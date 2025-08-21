package jp.co.ha.business.api.aws;

import java.io.StringWriter;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jp.co.ha.business.api.aws.AwsS3Component.AwsS3Key;
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
    private AwsProperties awsProps;
    /** システム設定ファイル情報 */
    @Autowired
    private SystemProperties systemProps;
    /** AWS認証情報Component */
    @Autowired
    private AwsAuthComponent auth;
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
     * メールキー
     * 
     * @version 1.0.0
     */
    public static enum MailTemplateKey implements BaseEnum {

        /** 健康管理アプリパスワード再設定メールのテンプレートキー */
        ACCOUNT_RECOVERY_TEMPLATE("account-recovery-template.ftl"),
        /** 健康管理ヘルスチェックのテンプレートキー */
        HEALTHINFO_CHECK_TEMPLATE("health-check-template.ftl"),
        /** 健康情報登録完了メールのテンプレートキー */
        HEALTHINFO_REGIST_TEMPLATE("healthinfo-regist-template.ftl");

        /**
         * コンストラクタ
         *
         * @param value
         *     値
         */
        private MailTemplateKey(String value) {
            this.value = value;
        }

        /** 値 */
        private String value;

        @Override
        public String getValue() {
            return this.value;
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

            VerifyResultType resultType = StringUtil
                    .isEmpty(response.responseMetadata().requestId())
                            ? VerifyResultType.STILL
                            : VerifyResultType.AUTHED;

            LOG.debug("認証メール送信完了 認証結果=" + resultType);

            return resultType;
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
    public EmailSendResultType sendMail(String to, String titleText,
            MailTemplateKey s3Key, Map<String, String> bodyMap) throws BaseException {
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

        if (awsProps.isSesStubFlag()) {
            return EmailSendResultType.NOT_SEND;
        }

        try (SesClient sesClient = getSesClient()) {

            LOG.debug("Amazon SES region=" + awsProps.getRegion().id()
                    + ",to_mail_address=" + to);

            Destination destination = Destination.builder()
                    .toAddresses(to)
                    .bccAddresses(systemProps.getSystemMailAddress())
                    .build();

            Body body = getBody(templateId, bodyMap);
            Message message = Message.builder()
                    .subject(getContent(titleText))
                    .body(body)
                    .build();

            SendEmailRequest request = SendEmailRequest.builder()
                    .source(systemProps.getSystemMailAddress())
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
                .connectionTimeout(Duration.ofMillis(awsProps.getSesTimeout()))
                .socketTimeout(Duration.ofMillis(awsProps.getSesTimeout()))
                .build();

        return SesClient.builder()
                .region(awsProps.getRegion())
                .credentialsProvider(auth.getAWSCredentialsProvider())
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

}
