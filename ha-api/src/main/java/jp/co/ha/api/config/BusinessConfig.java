package jp.co.ha.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.co.ha.business.api.service.HealthInfoReferenceService;
import jp.co.ha.business.api.service.HealthInfoRegistService;
import jp.co.ha.business.api.service.impl.HealthInfoReferenceServiceImpl;
import jp.co.ha.business.api.service.impl.HealthInfoRegistServiceImpl;
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
 * BusinessのBean登録クラス
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
	 * 健康情報登録APIサービス
	 *
	 * @return HealthInfoRegistService
	 */
	@Bean
	public HealthInfoRegistService healthInfoRegistService() {
		return new HealthInfoRegistServiceImpl();
	}

	/**
	 * 健康情報照会APIサービス
	 *
	 * @return HealthInfoReferenceService
	 */
	@Bean
	public HealthInfoReferenceService healthInfoReferenceService() {
		return new HealthInfoReferenceServiceImpl();
	}

}
