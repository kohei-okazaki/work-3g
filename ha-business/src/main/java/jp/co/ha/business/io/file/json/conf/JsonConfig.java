package jp.co.ha.business.io.file.json.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.co.ha.business.api.healthinfoapp.response.BaseAppApiResponse.ResultType;
import jp.co.ha.business.api.healthinfoapp.response.BaseAppApiResponse.ResultTypeSerializer;
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
                .addModule(getSerializeModule())
                .build();
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
