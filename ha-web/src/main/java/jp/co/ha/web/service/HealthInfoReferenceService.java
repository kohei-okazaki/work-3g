package jp.co.ha.web.service;

import java.util.List;

import jp.co.ha.business.healthInfo.result.HealthInfoReferenceResult;
import jp.co.ha.business.io.file.csv.model.ReferenceCsvDownloadModel;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.web.form.HealthInfoReferenceForm;

/**
 * 健康情報照会画面サービスインターフェース
 *
 */
public interface HealthInfoReferenceService {

	/**
	 * 健康情報レスポンスリストを取得する
	 *
	 * @param form
	 *     結果照会画面フォーム
	 * @param userId
	 *     ユーザID
	 * @return 健康情報レスポンスリスト
	 * @throws BaseException
	 *     基底例外
	 */
	List<HealthInfoReferenceResult> getHealthInfoResponseList(HealthInfoReferenceForm form, String userId)
			throws BaseException;

	/**
	 * 結果照会CSVモデルリストに変換する
	 *
	 * @param userId
	 *     ユーザID
	 * @param resultList
	 *     健康情報照会レスポンスリスト
	 * @return modelList
	 */
	List<ReferenceCsvDownloadModel> toModelList(String userId, List<HealthInfoReferenceResult> resultList);

	/**
	 * 指定した健康情報ファイル設定からCSV設定情報を返す
	 *
	 * @param entity
	 *     健康情報ファイル設定
	 * @return CSV設定情報
	 */
	CsvConfig getCsvConfig(HealthInfoFileSetting entity);
}
