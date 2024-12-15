package jp.co.ha.business.io.file.json.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jp.co.ha.business.api.healthinfoapp.response.BaseAppApiResponse.ResultType;
import jp.co.ha.business.api.healthinfoapp.response.BaseAppApiResponse.ResultTypeSerializer;
import jp.co.ha.business.api.healthinfoapp.type.TestMode;
import jp.co.ha.business.api.healthinfoapp.type.TestMode.TestModeDeserializer;

/**
 * JSONの設定クラス
 *
 * @version 1.0.0
 */
@Configuration
public class JsonConfig {

    /**
     * ObjectMapperをBeanに登録
     *
     * @return ObjectMapper
     */
    @Bean
    public ObjectMapper jsonObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(getDeserializeModule());
        mapper.registerModule(getSerializeModule());
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    /**
     * JSONデシリアライズするModuleを返す
     *
     * @return Module
     */
    private Module getDeserializeModule() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(TestMode.class, new TestModeDeserializer());
        return module;
    }

    /**
     * JSONシリアライズするModuleを返す
     *
     * @return Module
     */
    private Module getSerializeModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(ResultType.class, new ResultTypeSerializer());
        return module;
    }

}
