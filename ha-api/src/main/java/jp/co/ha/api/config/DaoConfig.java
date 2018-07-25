package jp.co.ha.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.co.ha.common.dao.AccountDao;
import jp.co.ha.common.dao.HealthInfoDao;
import jp.co.ha.common.dao.impl.AccountDaoImpl;
import jp.co.ha.common.dao.impl.HealthInfoDaoImpl;

/**
 * Dao設定クラス<br>
 *
 */
@Configuration
public class DaoConfig {

	/**
	 * アカウント情報Dao<br>
	 *
	 * @return
	 */
	@Bean
	public AccountDao accountDao() {
		return new AccountDaoImpl();
	}

	/**
	 * 健康情報Dao<br>
	 *
	 * @return
	 */
	@Bean
	public HealthInfoDao healthInfoDao() {
		return new HealthInfoDaoImpl();
	}

}
