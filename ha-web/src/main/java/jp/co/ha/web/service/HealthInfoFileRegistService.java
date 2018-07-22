package jp.co.ha.web.service;

import java.util.List;

import jp.co.ha.api.request.HealthInfoRegistRequest;
import jp.co.ha.common.exception.BaseAppException;
import jp.co.ha.common.exception.HealthInfoException;
import jp.co.ha.web.file.csv.model.HealthInfoCsvUploadModel;

/**
 * 健康情報ファイル入力画面サービス
 *
 */
public interface HealthInfoFileRegistService {

	/**
	 * 健康情報CSVアップロードモデルリストから健康情報APIリクエストのリストに変換する<br>
	 *
	 * @param modelList
	 *     健康情報CSVアップロードモデルリスト
	 * @param userId
	 *     ユーザID
	 * @return
	 * @throws BaseAppException
	 */
	List<HealthInfoRegistRequest> toRequestList(List<HealthInfoCsvUploadModel> modelList, String userId) throws BaseAppException;

	/**
	 * 健康情報CSVアップロードモデルリストに不正なデータが含まれていないかチェックを行う
	 *
	 * @param modelList
	 *     健康情報CSVアップロードモデルリスト
	 *
	 * @param modelList
	 * @throws HealthInfoException
	 *     健康情報例外
	 */
	void formatCheck(List<HealthInfoCsvUploadModel> modelList) throws HealthInfoException;

}
