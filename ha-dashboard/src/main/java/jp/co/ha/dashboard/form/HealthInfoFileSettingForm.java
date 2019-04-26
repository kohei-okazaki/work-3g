package jp.co.ha.dashboard.form;

import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.common.validator.annotation.Flag;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.common.web.form.BaseForm;

/**
 * 健康情報ファイル設定フォームクラス
 *
 */
public class HealthInfoFileSettingForm implements BaseForm {

	/** シリアルバージョンUID */
	@Ignore
	private static final long serialVersionUID = 1L;

	/** ユーザID */
	@Required(message = "ユーザIDが未入力です")
	private String userId;
	/** ヘッダ利用有無フラグ */
	@Required(message = "ヘッダ利用有無フラグが未入力です")
	@Flag(message = "ヘッダ利用有無フラグの値が不正です")
	private String headerFlag;
	/** フッタ利用有無フラグ */
	@Required(message = "フッタ利用有無フラグが未入力です")
	@Flag(message = "フッタ利用有無フラグの値が不正です")
	private String footerFlag;
	/** マスク利用有無フラグ */
	@Required(message = "マスク利用有無フラグが未入力です")
	@Flag(message = "マスク利用有無フラグの値が不正です")
	private String maskFlag;
	/** 囲み文字利用有無フラグ */
	@Required(message = "囲み文字利用有無フラグが未入力です")
	@Flag(message = "囲み文字利用有無フラグの値が不正です")
	private String enclosureCharFlag;

	/**
	 * userIdを返す
	 *
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * userIdを設定する
	 *
	 * @param userId
	 *     ユーザID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * headerFlagを返す
	 *
	 * @return headerFlag
	 */
	public String getHeaderFlag() {
		return headerFlag;
	}

	/**
	 * headerFlagを設定する
	 *
	 * @param headerFlag
	 *     ヘッダー利用フラグ
	 */
	public void setHeaderFlag(String headerFlag) {
		this.headerFlag = headerFlag;
	}

	/**
	 * footerFlagを返す
	 *
	 * @return footerFlag
	 */
	public String getFooterFlag() {
		return footerFlag;
	}

	/**
	 * footerFlagを設定する
	 *
	 * @param footerFlag
	 *     フッター利用フラグ
	 */
	public void setFooterFlag(String footerFlag) {
		this.footerFlag = footerFlag;
	}

	/**
	 * maskFlagを返す
	 *
	 * @return maskFlag
	 */
	public String getMaskFlag() {
		return maskFlag;
	}

	/**
	 * maskFlagを設定する
	 *
	 * @param maskFlag
	 *     マスク利用フラグ
	 */
	public void setMaskFlag(String maskFlag) {
		this.maskFlag = maskFlag;
	}

	/**
	 * enclosureCharFlagを返す
	 *
	 * @return enclosureCharFlag
	 */
	public String getEnclosureCharFlag() {
		return enclosureCharFlag;
	}

	/**
	 * enclosureCharFlagを設定する
	 *
	 * @param enclosureCharFlag
	 *     囲い文字利用フラグ
	 */
	public void setEnclosureCharFlag(String enclosureCharFlag) {
		this.enclosureCharFlag = enclosureCharFlag;
	}

}
