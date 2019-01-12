package jp.co.ha.web.service;

import java.util.List;

import org.springframework.ui.Model;

import jp.co.ha.api.response.HealthInfoRegistResponse;
import jp.co.ha.business.io.file.csv.model.HealthInfoCsvDownloadModel;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.web.form.HealthInfoForm;

/**
 * 健康情報入力画面サービスインターフェース
 *
 */
public interface HealthInfoService {

	/**
	 * Modelに表示用のメッセージを追加する
	 *
	 * @param model
	 *     Model
	 * @param form
	 *     健康情報入力フォーム
	 * @param lastHealthInfo
	 *     最後に登録した健康情報
	 */
	void addModel(Model model, HealthInfoForm form, HealthInfo lastHealthInfo);

	/**
	 * 指定されたユーザIDが初回登録かどうか判定する<br>
	 * 初回登録の場合true, それ以外の場合falseを返す<br>
	 *
	 * @param userId
	 *     ユーザID
	 * @return 判定結果
	 * @throws BaseException
	 *     基底例外
	 */
	boolean isFirstReg(String userId) throws BaseException;

	/**
	 * 指定した健康情報リストの中に指定した健康情報IDが含まれるかどうか返す
	 *
	 * @param entityList
	 *     健康情報リスト
	 * @param healthInfoId
	 *     健康情報ID
	 * @return 判定結果
	 */
	boolean hasRecord(List<HealthInfo> entityList, Integer healthInfoId);

	/**
	 * CSVモデルリストに変換する
	 *
	 * @param healthInfo
	 *     健康情報
	 * @return CSVモデルリスト
	 */
	List<HealthInfoCsvDownloadModel> toModelList(HealthInfo healthInfo);

	/**
	 * 健康情報を登録する
	 *
	 * @param form
	 *     健康情報入力フォーム
	 * @param userId
	 *     ユーザID
	 * @return HealthInfoRegistResponse
	 * @throws BaseException
	 *     基底例外
	 */
	HealthInfoRegistResponse regist(HealthInfoForm form, String userId) throws BaseException;

	/**
	 * CSV設定情報を取得する
	 *
	 * @param fileName
	 *     ファイル名
	 * @param healthInfoFileSetting
	 *     健康情報ファイル設定
	 * @return CsvConfig
	 */
	CsvConfig getCsvConfig(String fileName, HealthInfoFileSetting healthInfoFileSetting);

}
