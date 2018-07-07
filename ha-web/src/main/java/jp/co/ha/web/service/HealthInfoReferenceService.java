package jp.co.ha.web.service;

import java.util.List;

import jp.co.ha.api.response.HealthInfoReferenceResponse;
import jp.co.ha.web.file.csv.model.ReferenceCsvModel;
import jp.co.ha.web.form.HealthInfoReferenceForm;

/**
 * 健康情報照会画面サービスインターフェース<br>
 *
 */
public interface HealthInfoReferenceService {

	/**
	 * 健康情報を取得する<br>
	 *
	 * @param form
	 *     結果照会画面フォーム
	 * @param userId
	 *     ユーザID
	 * @return
	 */
	List<HealthInfoReferenceResponse> getHealthInfoResponseList(HealthInfoReferenceForm form, String userId);

	/**
	 * 結果照会CSVモデルリストに変換する
	 *
	 * @param userId
	 *     ユーザID
	 * @param resultList
	 *     List<HealthInfoReferenceResponse>
	 * @return modelList
	 */
	List<ReferenceCsvModel> toModelList(String userId, List<HealthInfoReferenceResponse> resultList);

}
