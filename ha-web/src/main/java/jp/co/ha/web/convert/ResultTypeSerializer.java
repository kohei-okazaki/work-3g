package jp.co.ha.web.convert;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import jp.co.ha.web.form.BaseApiResponse.ResultType;

/**
 * JSONのAPIの結果コードのシリアライズクラス
 *
 * @version 1.0.0
 */
public class ResultTypeSerializer extends JsonSerializer<ResultType> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void serialize(ResultType resultType, JsonGenerator gen,
            SerializerProvider serializers) throws IOException {
        gen.writeString(resultType.getValue());
    }

}
