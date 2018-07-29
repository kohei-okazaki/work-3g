package jp.co.ha.web.service;

import java.util.List;

import jp.co.ha.common.exception.BaseAppException;
import jp.co.ha.common.exception.HealthInfoException;
import jp.co.ha.web.file.csv.model.HealthInfoCsvUploadModel;

/**
 * 健康情報ファイル入力画面サービス
 *
 */
public interface HealthInfoFileRegistService {

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

	/**
	 * 指定されたモデルリストの登録処理を行う<br>
	 *
	 * @param modelList
	 *     健康情報CSVアップロードモデルリスト
	 * @param userId
	 *     ユーザID
	 * @throws BaseAppException
	 */
	void regist(List<HealthInfoCsvUploadModel> modelList, String userId) throws BaseAppException;

}
