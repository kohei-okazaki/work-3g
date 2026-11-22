package jp.co.ha.root.config;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.MessageSource;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;

import jp.co.ha.business.exception.ApiErrorCode;
import jp.co.ha.business.exception.BusinessErrorCode;
import jp.co.ha.common.exception.BaseErrorCode;
import jp.co.ha.common.exception.CommonErrorCode;

/**
 * 共通設定と各環境の設定から、実際に配布するエラーメッセージを解決できることを確認する。
 */
class ErrorMessageConfigurationTest {

    @ParameterizedTest
    @ValueSource(strings = {"default", "local", "dev"})
    void resolvesErrorMessagesUsingApplicationConfiguration(String profile) {
        Properties properties = loadApplicationProperties(profile);

        // 設定値だけを読み、設定のimportによるDBや外部サービスの初期化は行わない。
        new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(MessageSourceAutoConfiguration.class))
                .withInitializer(context -> context.getEnvironment().getPropertySources()
                        .addFirst(new PropertiesPropertySource("root-" + profile, properties)))
                .run(context -> {
                    assertNull(context.getStartupFailure());
                    MessageSource messages = context.getBean("messageSource", MessageSource.class);
                    for (String bundle : new String[] {"apiErrorCode", "businessErrorCode",
                            "commonErrorCode", "validateErrorCode"}) {
                        assertBundleMessages(messages, bundle);
                    }
                    assertErrorCodeMessages(messages, CommonErrorCode.values());
                    assertErrorCodeMessages(messages, BusinessErrorCode.values());
                    assertErrorCodeMessages(messages, ApiErrorCode.values());
                    assertEquals("ヘルスチェックに失敗しました", messages.getMessage(
                            BusinessErrorCode.HEALTH_CHECK_ERROR.getOuterErrorCode(),
                            null, Locale.JAPAN));
                    assertEquals("項目が未入力です", messages.getMessage(
                            "validate.message.NotEmpty", new Object[] {"項目"}, Locale.JAPAN));
                });
    }

    private Properties loadApplicationProperties(String profile) {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        if ("default".equals(profile)) {
            yaml.setResources(new ClassPathResource("application.yml"));
        } else {
            yaml.setResources(new ClassPathResource("application.yml"),
                    new ClassPathResource("application-" + profile + ".yml"));
        }
        Properties properties = yaml.getObject();
        assertNotNull(properties);
        return properties;
    }

    private void assertBundleMessages(MessageSource messages, String bundle) throws IOException {
        Properties expected = new Properties();
        try (var input = new ClassPathResource(bundle + ".properties").getInputStream()) {
            expected.load(input);
        }
        assertFalse(expected.isEmpty());
        for (String code : expected.stringPropertyNames()) {
            assertEquals(expected.getProperty(code), messages.getMessage(code, null, Locale.JAPAN),
                    () -> bundle + ": " + code);
        }
    }

    private void assertErrorCodeMessages(MessageSource messages, BaseErrorCode[] errorCodes) {
        for (BaseErrorCode errorCode : errorCodes) {
            assertFalse(messages.getMessage(errorCode.getOuterErrorCode(), null, Locale.JAPAN)
                    .isBlank(), errorCode.getOuterErrorCode());
        }
    }
}
