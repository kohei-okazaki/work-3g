package jp.co.ha.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import jp.co.ha.business.api.request.deserialize.RequestTypeDeserializer;
import jp.co.ha.business.api.type.RequestType;

/**
 * Jsonの設定クラス
 *
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
		return mapper;
	}

	/**
	 * JSONデシリアライズするモジュールを取得
	 *
	 * @return Module
	 */
	private Module getDeserializeModule() {
		SimpleModule module = new SimpleModule();
		module.addDeserializer(RequestType.class, new RequestTypeDeserializer());
		return module;
	}

}
