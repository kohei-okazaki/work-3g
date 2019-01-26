package jp.co.ha.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.co.ha.business.api.service.ApiConnectionExecutor;
import jp.co.ha.business.db.DataBaseCommonExecutor;

/**
 * AOPのBean登録クラス
 *
 */
@Configuration
public class AopConfig {

	/**
	 * DB共通処理
	 *
	 * @return DataBaseCommonExecutor
	 */
	@Bean
	public DataBaseCommonExecutor dataBaseCommonExecutor() {
		return new DataBaseCommonExecutor();
	}

	/**
	 * API通信共通処理
	 *
	 * @return ApiConnectionExecutor
	 */
	@Bean
	public ApiConnectionExecutor apiConnectionExecutor() {
		return new ApiConnectionExecutor();
	}
}
