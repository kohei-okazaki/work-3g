package jp.co.ha.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import jp.co.ha.business.api.request.deserialize.RequestTypeDeserializer;
import jp.co.ha.business.api.request.deserialize.TestModeTypeDeserializer;
import jp.co.ha.business.api.type.RequestType;
import jp.co.ha.business.api.type.TestModeType;

/**
 * Jsonの設定クラス<br>
 *
 */
@Configuration
public class JsonConfig {

	@Bean
	public ObjectMapper jsonObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(getDeserializeModule());
		return mapper;
	}

	/**
	 * JSONデシリアライズするモジュールを取得<br>
	 *
	 * @return
	 */
	private Module getDeserializeModule() {
		SimpleModule module = new SimpleModule();
		module.addDeserializer(RequestType.class, new RequestTypeDeserializer());
		module.addDeserializer(TestModeType.class, new TestModeTypeDeserializer());
		return module;
	}

}
