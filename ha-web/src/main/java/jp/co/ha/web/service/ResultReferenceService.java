package jp.co.ha.web.service;

import java.util.List;

import jp.co.ha.api.response.HealthInfoRegistResponse;
import jp.co.ha.web.form.ResultSearchForm;

/**
 * 結果照会画面サービスインターフェース<br>
 *
 */
public interface ResultReferenceService {

	/**
	 * 健康情報を取得する<br>
	 *
	 * @param form
	 *            ResultSearchForm
	 * @param userId
	 *            ユーザID
	 * @return
	 */
	List<HealthInfoRegistResponse> getHealthInfoResponseList(ResultSearchForm form, String userId);

}
