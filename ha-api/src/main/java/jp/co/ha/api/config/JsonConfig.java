package jp.co.ha.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import jp.co.ha.api.request.RequestTypeDeserializer;
import jp.co.ha.common.api.RequestType;

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
		return module;
	}

}
