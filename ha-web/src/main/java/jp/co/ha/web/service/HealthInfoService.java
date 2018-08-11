package jp.co.ha.web.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.ui.Model;

import jp.co.ha.api.response.HealthInfoRegistResponse;
import jp.co.ha.business.db.entity.HealthInfo;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.web.file.csv.model.HealthInfoCsvDownloadModel;
import jp.co.ha.web.form.HealthInfoForm;

/**
 * 健康情報入力画面サービスインターフェース<br>
 *
 */
public interface HealthInfoService {

	/**
	 * Modelに表示用のメッセージを追加する<br>
	 *
	 * @param model
	 *     Model
	 * @param form
	 *     健康情報入力フォーム
	 * @param lastHealthInfo
	 *     健康情報
	 */
	void addModel(Model model, HealthInfoForm form, HealthInfo lastHealthInfo);

	/**
	 * 指定されたユーザIDが初回登録かどうか判定する<br>
	 * 初回登録の場合true, それ以外の場合falseを返す<br>
	 *
	 * @param userId
	 *     ユーザID
	 * @return
	 * @throws BaseException
	 */
	boolean isFirstReg(String userId) throws BaseException;

	/**
	 * 指定した健康情報リストの中に指定した健康情報IDが含まれるかどうか返す<br>
	 *
	 * @param entityList
	 *     健康情報リスト
	 * @param healthInfoId
	 *     健康情報ID
	 * @return
	 */
	boolean hasRecord(List<HealthInfo> entityList, BigDecimal healthInfoId);

	/**
	 * CSVモデルリストに変換する<br>
	 *
	 * @param healthInfo
	 *     健康情報
	 * @return
	 */
	List<HealthInfoCsvDownloadModel> toModelList(HealthInfo healthInfo);

	/**
	 * 健康情報を登録する<br>
	 * @param form
	 *     健康情報入力フォーム
	 * @param userId
	 *     ユーザID
	 * @return
	 * @throws BaseException
	 */
	HealthInfoRegistResponse regist(HealthInfoForm form, String userId) throws BaseException;

}
