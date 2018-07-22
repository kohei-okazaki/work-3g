package jp.co.ha.web.service;

import jp.co.ha.common.exception.BaseAppException;
import jp.co.ha.web.form.HealthInfoFileSettingForm;

/**
 * 健康情報ファイル設定サービスインターフェース<br>
 *
 */
public interface HealthInfoFileSettingService {

	/**
	 * 健康情報ファイル設定のメイン処理を行う<br>
	 * @param form
	 * @throws BaseAppException
	 */
	void execute(HealthInfoFileSettingForm form) throws BaseAppException;
}
