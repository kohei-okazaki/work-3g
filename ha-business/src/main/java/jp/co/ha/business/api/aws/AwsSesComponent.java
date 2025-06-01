package jp.co.ha.business.api.aws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.ClientConfigurationFactory;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.MailFromDomainNotVerifiedException;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.VerifyEmailIdentityRequest;
import com.amazonaws.services.simpleemail.model.VerifyEmailIdentityResult;

import jp.co.ha.business.exception.BusinessErrorCode;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.BaseEnum;
import jp.co.ha.common.type.Charset;
import jp.co.ha.common.util.StringUtil;

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

    /** {@linkplain AwsConfig} */
    @Autowired
    private AwsConfig awsConfig;
    /** {@linkplain AwsAuthComponent} */
    @Autowired
    private AwsAuthComponent awsAuthComponent;
    /** {@linkplain AwsS3Component} */
    @Autowired
    private AwsS3Component awsS3Component;

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

        VerifyEmailIdentityRequest verifyEmailIdentityRequest = new VerifyEmailIdentityRequest();
        // 検証用メールアドレス
        verifyEmailIdentityRequest.setEmailAddress(mailAddress);
        // AWS認証情報
        verifyEmailIdentityRequest.setRequestCredentialsProvider(
                awsAuthComponent.getAWSCredentialsProvider());

        VerifyEmailIdentityResult result = getAmazonSES()
                .verifyEmailIdentity(verifyEmailIdentityRequest);

        VerifyResultType res = StringUtil.isEmpty(
                result.getSdkResponseMetadata().getRequestId()) ? VerifyResultType.STILL
                        : VerifyResultType.AUTHED;

        LOG.debug("認証メール送信完了 認証結果=" + res);

        return res;
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
            // 料金節約のため、SESを利用しない場合メールは送信しない
            return EmailSendResultType.NOT_SEND;
        }

        try {

            LOG.debug("Amazon SES region=" + awsConfig.getRegion().getName()
                    + ",to_mail_address=" + to);
            Destination destination = new Destination().withToAddresses(to)
                    .withBccAddresses(awsConfig.getMailAddress());

            Body body = getBody(templateId, bodyMap);
            Message message = new Message().withSubject(getContent(titleText))
                    .withBody(body);

            SendEmailRequest request = new SendEmailRequest()
                    .withSource(awsConfig.getMailAddress()).withDestination(destination)
                    .withMessage(message).withRequestCredentialsProvider(
                            awsAuthComponent.getAWSCredentialsProvider());

            getAmazonSES().sendEmail(request);

            return EmailSendResultType.SUCCESS;

        } catch (MailFromDomainNotVerifiedException e) {
            throw new BusinessException(
                    BusinessErrorCode.AWS_SES_MAIL_ADDRESS_VERRIFIED_ERROR,
                    "メールアドレスがSESに認証されていません.管理画面から認証してください", e);
        }
    }

    /**
     * {@linkplain AmazonSimpleEmailService} を返す
     *
     * @return AmazonSimpleEmailService
     */
    public AmazonSimpleEmailService getAmazonSES() {
        return AmazonSimpleEmailServiceClientBuilder
                .standard()
                .withCredentials(awsAuthComponent.getAWSCredentialsProvider())
                .withClientConfiguration(getClientConfiguration())
                .withRegion(awsConfig.getRegion())
                .build();
    }

    /**
     * {@linkplain ClientConfiguration}を返す
     *
     * @return ClientConfiguration
     */
    private ClientConfiguration getClientConfiguration() {
        return new ClientConfigurationFactory().getConfig()
                .withConnectionTimeout(awsConfig.getSesTimeout());
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

        String bodyText = replace(getBodyTemplate(templateId), bodyMap);
        return new Body().withHtml(getContent(bodyText));
    }

    /**
     * {@linkplain Content}を返す
     *
     * @param text
     *     本文
     * @return Content
     */
    private Content getContent(String text) {
        return new Content().withCharset(CHARSET.getValue()).withData(text);
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
    private String getBodyTemplate(String templateId) throws BaseException {

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
