package jp.co.ha.dashboard.service;

import java.util.List;

import jp.co.ha.business.dto.HealthInfoReferenceDto;
import jp.co.ha.business.io.file.csv.model.ReferenceCsvDownloadModel;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.db.entity.HealthInfoFileSetting;

/**
 * 健康情報照会画面サービスインターフェース
 * 
 * @since 1.0
 */
public interface HealthInfoReferService {

	/**
	 * 健康情報レスポンスリストを取得する
	 *
	 * @param dto
	 *     健康情報照会DTO
	 * @param userId
	 *     ユーザID
	 * @return 健康情報レスポンスリスト
	 * @throws BaseException
	 *     基底例外
	 */
	List<HealthInfoReferenceDto> getHealthInfoResponseList(HealthInfoReferenceDto dto, String userId)
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
	List<ReferenceCsvDownloadModel> toModelList(String userId, List<HealthInfoReferenceDto> resultList);

	/**
	 * 指定した健康情報ファイル設定からCSV設定情報を返す
	 *
	 * @param entity
	 *     健康情報ファイル設定
	 * @return CSV設定情報
	 */
	CsvConfig getCsvConfig(HealthInfoFileSetting entity);
}
