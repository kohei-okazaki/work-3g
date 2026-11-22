package jp.co.ha.api.config;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Properties;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.util.ReflectionTestUtils;

import jp.co.ha.business.exception.ApiErrorCode;
import jp.co.ha.business.exception.BusinessErrorCode;
import jp.co.ha.common.exception.BaseErrorCode;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.ValidateErrorCode;

/**
 * 共通設定・両環境のYAMLから、MessageSourceが全メッセージを参照できることを確認する。
 */
class ErrorMessageConfigurationTest {

    @ParameterizedTest
    @ValueSource(strings = {"default", "local", "dev"})
    void resolvesAllRegisteredMessagesUsingYamlConfiguration(String profile) {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        if ("default".equals(profile)) {
            yaml.setResources(new ClassPathResource("application.yml"));
        } else {
            yaml.setResources(new ClassPathResource("application.yml"),
                    new ClassPathResource("application-" + profile + ".yml"));
        }
        Properties properties = yaml.getObject();
        assertNotNull(properties);

        // 設定値とMessageSourceだけを読み、DB・API・認証のBeanは起動しない。
        new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(MessageSourceAutoConfiguration.class))
                .withPropertyValues("spring.profiles.active=" + profile)
                .withInitializer(context -> context.getEnvironment().getPropertySources()
                        .addFirst(new PropertiesPropertySource("api-" + profile, properties)))
                .run(context -> {
                    assertNull(context.getStartupFailure());
                    ResourceBundleMessageSource messages = assertInstanceOf(
                            ResourceBundleMessageSource.class,
                            context.getBean("messageSource", MessageSource.class));
                    assertEquals(StandardCharsets.UTF_8,
                            ReflectionTestUtils.invokeGetterMethod(messages, "getDefaultCharset"));
                    assertNull(ReflectionTestUtils.invokeGetterMethod(messages, "getDefaultLocale"));
                    assertEquals(Long.valueOf(1000),
                            ReflectionTestUtils.invokeGetterMethod(messages, "getCacheMillis"));
                    for (String basename : new String[] {"messages", "apiErrorCode",
                            "businessErrorCode", "commonErrorCode", "validateErrorCode"}) {
                        Properties expected = new Properties();
                        try (var input = new ClassPathResource(basename + ".properties")
                                .getInputStream()) {
                            expected.load(input);
                        }
                        for (String key : expected.stringPropertyNames()) {
                            assertEquals(expected.getProperty(key),
                                    messages.getMessage(key, null, Locale.JAPAN),
                                    basename + ": " + key);
                        }
                    }
                    for (BaseErrorCode[] codes : new BaseErrorCode[][] {ApiErrorCode.values(),
                            BusinessErrorCode.values(), CommonErrorCode.values(),
                            ValidateErrorCode.values()}) {
                        for (BaseErrorCode code : codes) {
                            assertFalse(messages.getMessage(code.getOuterErrorCode(),
                                    null, Locale.JAPAN).isBlank(), code.getOuterErrorCode());
                        }
                    }
                });
    }
}
