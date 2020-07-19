package jp.co.ha.business.api.aws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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

import jp.co.ha.business.exception.BusinessErrorCode;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.Charset;

/**
 * AWS-SimpleEmailServiceのComponent
 *
 * @version 1.0.0
 */
@Component
public class AwsSesComponent {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(AwsSesComponent.class);
    /** 文字コード */
    private static final Charset CHARSET = Charset.UTF_8;

    /** AWS個別設定情報 */
    @Autowired
    private AwsConfig awsConfig;
    /** AWS認証情報 */
    @Autowired
    private AwsAuthComponent awsAuthComponent;

    /**
     * Eメールを送信する
     *
     * @param to
     *     宛先メールアドレス
     * @param titleText
     *     メール件名
     * @param template
     *     メール本文のテンプレート名
     * @param bodyMap
     *     メール本文を置換するmap
     * @throws BaseException
     *     メール送信に失敗した場合
     */
    public void sendMail(String to, String titleText, String template,
            Map<String, String> bodyMap) throws BaseException {

        if (awsConfig.isSesStubFlag()) {
            // 料金節約のため、SESを利用しない場合メールは送信しない
            return;
        }

        try {

            LOG.debug("Amazon SES region=" + awsConfig.getRegion().getName()
                    + ",to_mail_address=" + to);
            Destination destination = new Destination()
                    .withToAddresses(to)
                    .withBccAddresses(awsConfig.getMailAddress());

            Body body = getBody(template, bodyMap);
            Message message = new Message()
                    .withSubject(getContent(titleText))
                    .withBody(body);

            SendEmailRequest request = new SendEmailRequest()
                    .withSource(awsConfig.getMailAddress())
                    .withDestination(destination)
                    .withMessage(message);

            getAmazonSES().sendEmail(request);

        } catch (MailFromDomainNotVerifiedException e) {
            throw new BusinessException(BusinessErrorCode.AWS_S3_UPLOAD_ERROR,
                    "メールアドレスがSESに認証されていません.管理画面から認証してください", e);
        } catch (Exception e) {
            throw new BusinessException(BusinessErrorCode.AWS_CLIENT_CONNECT_ERROR,
                    "SESを介してメールの送信に失敗しました", e);
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
        ClientConfiguration config = new ClientConfigurationFactory().getConfig();
        config.setConnectionTimeout(awsConfig.getSesTimeout());
        return config;
    }

    /**
     * {@linkplain Body}を返す
     *
     * @param template
     *     メール本文テンプレート
     * @param bodyMap
     *     メール本文の置換用Map
     * @return Body
     */
    private Body getBody(String template, Map<String, String> bodyMap) {

        String bodyText = replace(getBodyTemplate(template), bodyMap);
        return new Body()
                .withHtml(getContent(bodyText));
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
     * @param template
     *     テンプレートファイル
     * @return メール本文
     */
    private String getBodyTemplate(String template) {

        StringBuilder sb = new StringBuilder();
        try (InputStream is = new ClassPathResource(template).getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
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
