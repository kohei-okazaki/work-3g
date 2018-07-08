package jp.co.ha.web.service;

import jp.co.ha.web.form.HealthInfoFileSettingForm;

/**
 * 健康情報ファイル設定サービスインターフェース<br>
 *
 */
public interface HealthInfoFileSettingService {

	/**
	 * 健康情報ファイル設定のメイン処理を行う<br>
	 * @param form
	 */
	void execute(HealthInfoFileSettingForm form);
}
