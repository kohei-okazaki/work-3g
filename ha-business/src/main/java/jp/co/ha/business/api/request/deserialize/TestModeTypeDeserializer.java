package jp.co.ha.business.api.request.deserialize;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import jp.co.ha.business.api.type.TestModeType;

public class TestModeTypeDeserializer extends JsonDeserializer<TestModeType> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TestModeType deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		return TestModeType.of(parser.getValueAsString("testModeType"));
	}

}
