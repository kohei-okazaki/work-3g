package jp.co.ha.business.dto;

/**
 * 健康情報ファイルDTO
 *
 */
public class HealthInfoFileSettingDto {

	/** ユーザID */
	private String userId;
	/** ヘッダ利用有無フラグ */
	private String headerFlag;
	/** フッタ利用有無フラグ */
	private String footerFlag;
	/** マスク利用有無フラグ */
	private String maskFlag;
	/** 囲み文字利用有無フラグ */
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
	 */
	public void setEnclosureCharFlag(String enclosureCharFlag) {
		this.enclosureCharFlag = enclosureCharFlag;
	}

}