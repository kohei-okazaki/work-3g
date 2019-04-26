package jp.co.ha.dashboard.service;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.dashboard.form.HealthInfoFileSettingForm;

/**
 * 健康情報ファイル設定サービスインターフェース
 *
 */
public interface HealthInfoFileSettingService {

	/**
	 * 健康情報ファイル設定のメイン処理を行う
	 *
	 * @param form
	 *     健康情報ファイル設定form
	 * @throws BaseException
	 *     基底例外
	 */
	void execute(HealthInfoFileSettingForm form) throws BaseException;
}
