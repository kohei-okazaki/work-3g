package jp.co.ha.web.service;

import java.math.BigDecimal;

import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.web.form.HealthInfoForm;

/**
 * 健康情報入力画面サービスインターフェース<br>
 *
 */
public interface HealthInfoService {

	/**
	 * 入力した体重と最後に入力した体重との差からメッセージを返す<br>
	 * @param form
	 * @param lastHealthInfo
	 * @return 体重差のメッセージ
	 */
	String getDiffMessage(HealthInfoForm form, HealthInfo lastHealthInfo);

	/**
	 * 最後に入力した体重とフォームから体重差を返却
	 * @param form
	 * @param lastHealthInfo
	 * @return 体重差
	 */
	BigDecimal getDiffWeight(HealthInfoForm form, HealthInfo lastHealthInfo);

	/**
	 * 健康情報formを健康情報Entityにつめる<br>
	 * @param form
	 * @param userId
	 * @param lastHealthInfo
	 * @return
	 */
	HealthInfo convertHealthInfo(HealthInfoForm form, String userId, HealthInfo lastHealthInfo);

	/**
	 * 指定されたユーザIDが初回登録かどうか判定する<br>
	 * 初回登録の場合true, それ以外の場合falseを返す<br>
	 * @param userId
	 * @return
	 */
	boolean isFirstReg(String userId);
}
