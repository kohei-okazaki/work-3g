package jp.co.ha.business.api.node;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import jp.co.ha.business.api.node.response.BaseHealthinfoCalcResponse.Result;

/**
 * JSONの設定クラス
 *
 * @version 1.0.0
 */
@Configuration
public class HealthInfoCalcJsonConfig {

    /**
     * ObjectMapperをBeanに登録
     *
     * @return ObjectMapper
     */
    @Bean
    public ObjectMapper jsonObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(getSerializeModule());
        return mapper;
    }

    /**
     * JSONシリアライズするModuleを返す
     *
     * @return Module
     */
    private Module getSerializeModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Result.class, new ResultSerializer());
        return module;
    }

    /**
     * 処理結果の列挙のシリアライズクラス
     *
     * @version 1.0.0
     */
    public static class ResultSerializer extends JsonSerializer<Result> {

        @Override
        public void serialize(Result result, JsonGenerator gen,
                SerializerProvider serializers) throws IOException {
            gen.writeString(result.getValue());
        }
    }

}
