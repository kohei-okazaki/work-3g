package jp.co.ha.api.request;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import jp.co.ha.common.api.RequestType;

/**
 * Jsonのリクエスト種別のデシリアライズクラス<br>
 *
 */
public class RequestTypeDeserializer extends JsonDeserializer<RequestType> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RequestType deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		final String reqType = parser.getValueAsString("requestType");
		return RequestType.of(reqType);
	}

}
