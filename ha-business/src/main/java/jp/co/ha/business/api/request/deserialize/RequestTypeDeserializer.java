package jp.co.ha.business.api.request.deserialize;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import jp.co.ha.business.api.type.RequestType;

/**
 * JSONのリクエスト種別のデシリアライズクラス<br>
 * 文字列型のJSONをJavaのクラスに変換する<br>
 *
 */
public class RequestTypeDeserializer extends JsonDeserializer<RequestType> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RequestType deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		return RequestType.of(parser.getValueAsString("requestType"));
	}

}
