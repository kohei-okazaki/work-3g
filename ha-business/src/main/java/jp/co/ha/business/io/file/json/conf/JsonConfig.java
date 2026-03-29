package jp.co.ha.business.io.file.json.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.co.ha.business.api.healthinfoapp.response.BaseAppApiResponse.ResultType;
import jp.co.ha.business.api.healthinfoapp.response.BaseAppApiResponse.ResultTypeSerializer;
import jp.co.ha.business.api.healthinfoapp.type.TestMode;
import jp.co.ha.business.api.healthinfoapp.type.TestMode.TestModeDeserializer;
import tools.jackson.databind.JacksonModule;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

/**
 * JSONの設定クラス
 *
 * @version 1.0.0
 */
@Configuration
public class JsonConfig {

    /**
     * JsonMapperをBeanに登録
     *
     * @return JsonMapper
     */
    @Bean
    JsonMapper jsonObjectMapper() {
        return JsonMapper.builder()
                .addModule(getDeserializeModule())
                .addModule(getSerializeModule())
                .build();
    }

    /**
     * JSONデシリアライズするModuleを返す
     *
     * @return JacksonModule
     */
    private JacksonModule getDeserializeModule() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(TestMode.class, new TestModeDeserializer());
        return module;
    }

    /**
     * JSONシリアライズするModuleを返す
     *
     * @return JacksonModule
     */
    private JacksonModule getSerializeModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(ResultType.class, new ResultTypeSerializer());
        return module;
    }
}