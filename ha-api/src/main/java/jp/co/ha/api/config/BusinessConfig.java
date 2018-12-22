package jp.co.ha.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.co.ha.business.api.service.ApiConnectionService;
import jp.co.ha.business.db.DataBaseCommonExecutor;
import jp.co.ha.business.db.crud.create.HealthInfoCreateService;
import jp.co.ha.business.db.crud.create.impl.HealthInfoCreateServiceImpl;
import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.db.crud.read.impl.AccountSearchServiceImpl;
import jp.co.ha.business.db.crud.read.impl.HealthInfoSearchServiceImpl;
import jp.co.ha.business.healthInfo.HealthInfoCalcService;
import jp.co.ha.business.healthInfo.HealthInfoFunctionService;
import jp.co.ha.business.healthInfo.impl.HealthInfoCalcServiceImpl;
import jp.co.ha.business.healthInfo.impl.HealthInfoFunctionServiceImpl;

/**
 * Businessの設定クラス<br>
 *
 */
@Configuration
public class BusinessConfig {

	/**
	 * アカウント情報検索サービス
	 *
	 * @return AccountSearchService
	 */
	@Bean
	public AccountSearchService accountSearchService() {
		return new AccountSearchServiceImpl();
	}

	/**
	 * 健康情報検索サービス
	 *
	 * @return HealthInfoSearchService
	 */
	@Bean
	public HealthInfoSearchService healthInfoSearchService() {
		return new HealthInfoSearchServiceImpl();
	}

	/**
	 * 健康情報作成サービス
	 *
	 * @return HealthInfoCreateService
	 */
	@Bean
	public HealthInfoCreateService healthInfoCreateService() {
		return new HealthInfoCreateServiceImpl();
	}

	/**
	 * 健康情報計算サービス
	 *
	 * @return HealthInfoCalcService
	 */
	@Bean
	public HealthInfoCalcService healthInfoCalcService() {
		return new HealthInfoCalcServiceImpl();
	}

	/**
	 * 健康情報利用機能サービス
	 *
	 * @return HealthInfoFunctionService
	 */
	@Bean
	public HealthInfoFunctionService healthInfoFunctionService() {
		return new HealthInfoFunctionServiceImpl();
	}

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
	 * API共通処理
	 *
	 * @return ApiConnectionService
	 */
	@Bean
	public ApiConnectionService apiConnectionService() {
		return new ApiConnectionService();
	}

}
