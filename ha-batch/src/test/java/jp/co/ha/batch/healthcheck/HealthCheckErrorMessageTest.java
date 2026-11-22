package jp.co.ha.batch.healthcheck;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;
import java.util.stream.Stream;

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
import jp.co.ha.common.exception.ValidateErrorCode;

/**
 * 共通設定と各環境のバッチ設定から、すべてのエラー文言を取得できることを確認する。
 */
class HealthCheckErrorMessageTest {

    private static final String[] MESSAGE_BUNDLES = {
            "messages", "apiErrorCode", "businessErrorCode", "commonErrorCode", "validateErrorCode"
    };

    @ParameterizedTest
    @ValueSource(strings = {"default", "local", "dev"})
    void resolvesAllErrorMessagesUsingProfileConfiguration(String profile) {
        Properties properties = loadYaml("application.yml");
        if (!"default".equals(profile)) {
            properties.putAll(loadYaml("application-" + profile + ".yml"));
        }

        // YAMLの値だけを読み、設定のimportによるDBや外部サービスの初期化は行わない。
        new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(MessageSourceAutoConfiguration.class))
                .withInitializer(context -> context.getEnvironment().getPropertySources()
                        .addFirst(new PropertiesPropertySource("batch-" + profile, properties)))
                .run(context -> {
                    assertNull(context.getStartupFailure());
                    MessageSource messages = context.getBean("messageSource", MessageSource.class);
                    for (String bundle : MESSAGE_BUNDLES) {
                        Properties expected = loadMessages(bundle);
                        for (String key : expected.stringPropertyNames()) {
                            assertEquals(expected.getProperty(key),
                                    messages.getMessage(key, null, Locale.JAPAN),
                                    profile + ": " + bundle + ": " + key);
                        }
                    }

                    // enumへ追加したコードの文言登録漏れも検出する。
                    Stream.<BaseErrorCode[]>of(CommonErrorCode.values(), ValidateErrorCode.values(),
                            BusinessErrorCode.values(), ApiErrorCode.values())
                            .flatMap(Arrays::stream)
                            .forEach(error -> assertFalse(messages.getMessage(
                                    error.getOuterErrorCode(), null, Locale.JAPAN).isBlank(),
                                    profile + ": " + error.getOuterErrorCode()));

                    assertEquals("ヘルスチェックに失敗しました", messages.getMessage(
                            BusinessErrorCode.HEALTH_CHECK_ERROR.getOuterErrorCode(),
                            null, Locale.JAPAN));
                });
    }

    private Properties loadYaml(String resource) {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource(resource));
        Properties properties = yaml.getObject();
        assertNotNull(properties);
        return properties;
    }

    private Properties loadMessages(String bundle) throws IOException {
        Properties messages = new Properties();
        try (var input = new ClassPathResource(bundle + ".properties").getInputStream()) {
            messages.load(input);
        }
        return messages;
    }
}
