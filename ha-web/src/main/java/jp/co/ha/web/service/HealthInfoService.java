package jp.co.ha.web.service;

import java.math.BigDecimal;
import java.util.List;

import jp.co.ha.api.request.HealthInfoRegistRequest;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.web.form.HealthInfoForm;

/**
 * 健康情報入力画面サービスインターフェース<br>
 *
 */
public interface HealthInfoService {

	/**
	 * 入力した体重と最後に入力した体重との差からメッセージを返す<br>
	 *
	 * @param form
	 *            健康情報入力フォーム
	 * @param lastHealthInfo
	 *            HealthInfo
	 * @return 体重差のメッセージ
	 */
	String getDiffMessage(HealthInfoForm form, HealthInfo lastHealthInfo);

	/**
	 * 最後に入力した体重とフォームから体重差を返却
	 *
	 * @param form
	 *            健康情報入力フォーム
	 * @param lastHealthInfo
	 *            HealthInfo
	 * @return 体重差
	 */
	BigDecimal getDiffWeight(HealthInfoForm form, HealthInfo lastHealthInfo);

	/**
	 * 指定されたユーザIDが初回登録かどうか判定する<br>
	 * 初回登録の場合true, それ以外の場合falseを返す<br>
	 *
	 * @param userId
	 *            ユーザID
	 * @return
	 */
	boolean isFirstReg(String userId);

	/**
	 * 健康情報登録APIリクエストの設定を行う<br>
	 *
	 * @param form
	 *            健康情報入力フォーム
	 * @param userId
	 *            ユーザID
	 */
	HealthInfoRegistRequest setUpApiRequest(HealthInfoForm form, String userId);

	/**
	 * 指定した健康情報リストの中に指定したデータIDが含まれるかどうか返す<br>
	 *
	 * @param entityList
	 *            健康情報リスト
	 * @param dataId
	 *            データID
	 * @return
	 */
	boolean hasRecord(List<HealthInfo> entityList, String dataId);

}
