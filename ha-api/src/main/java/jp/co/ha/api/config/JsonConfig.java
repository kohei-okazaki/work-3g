package jp.co.ha.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import jp.co.ha.business.api.request.deserialize.RequestTypeDeserializer;
import jp.co.ha.business.api.request.deserialize.TestModeDeserializer;
import jp.co.ha.business.api.type.RequestType;
import jp.co.ha.business.api.type.TestMode;
import jp.co.ha.web.convert.ResultTypeSerializer;
import jp.co.ha.web.form.BaseApiResponse.ResultType;

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
        return mapper;
    }

    /**
     * JSONデシリアライズするModuleを返す
     *
     * @return Module
     */
    private Module getDeserializeModule() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(RequestType.class, new RequestTypeDeserializer());
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
