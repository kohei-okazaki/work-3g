package jp.co.ha.web.service;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.web.form.HealthInfoFileSettingForm;

/**
 * 健康情報ファイル設定サービスインターフェース<br>
 *
 */
public interface HealthInfoFileSettingService {

	/**
	 * 健康情報ファイル設定のメイン処理を行う<br>
	 *
	 * @param form
	 *     健康情報ファイル設定form
	 * @throws BaseException
	 */
	void execute(HealthInfoFileSettingForm form) throws BaseException;
}
