package jp.co.ha.web.service;

import java.util.List;

import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.web.form.ResultSearchForm;

/**
 * 結果照会画面サービスインターフェース<br>
 *
 */
public interface ResultReferenceService {

	/**
	 * 結果照会画面のフォームクラスの設定を行う<br>
	 *
	 * @param form
	 *            ResultSearchForm
	 */
	void setUpForm(ResultSearchForm form);

	/**
	 * 健康情報を取得する<br>
	 *
	 * @param form
	 *            ResultSearchForm
	 * @param userId
	 *            ユーザID
	 * @return
	 */
	List<HealthInfo> getHealthInfo(ResultSearchForm form, String userId);

}
