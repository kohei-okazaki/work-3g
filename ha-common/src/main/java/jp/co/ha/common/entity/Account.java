package jp.co.ha.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.common.log.annotation.Mask;

/**
 * アカウント情報Entity<br>
 *
 */
@Entity
@Table(name = "ACCOUNT")
public class Account implements Serializable {

	/** シリアルバージョンUID */
	@Ignore
	private static final long serialVersionUID = 1L;

	/** ユーザID */
	@Id
	@Column(name = "USER_ID", nullable = false, length = 16)
	private String userId;

	/** パスワード */
	@Mask
	@Column(name = "PASSWORD", nullable = false, length = 16)
	private String password;

	/** 削除フラグ */
	@Column(name = "DELETE_FLAG", nullable = false, length = 1)
	private String deleteFlag;

	/** パスワード有効期限 */
	@Column(name = "PASSWORD_EXPIRE", nullable = false)
	private Date passwordExpire;

	/** 備考 */
	@Column(name = "REMARKS", nullable = false)
	private String remarks;

	/** ファイル囲い文字利用フラグ */
	@Column(name = "FILE_ENCLOSURE_CHAR_FLAG", nullable = true)
	private String fileEnclosureCharFlag;

	/** 健康情報マスク利用フラグ */
	@Column(name = "HEALTH_INFO_MASK_FLAG", nullable = true)
	private String healthInfoMaskFlag;

	/** 更新日時 */
	@Column(name = "UPDATE_DATE", nullable = true)
	private Date updateDate;

	/** 登録日時 */
	@Column(name = "REG_DATE", nullable = false)
	private Date regDate;

	/**
	 * userIdを返す
	 *
	 * @return userId ユーザID
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
	 * passwordを返す
	 *
	 * @return password パスわード
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * passwordを設定する
	 *
	 * @param password
	 *     パスワード
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * deleteFlagを返す
	 *
	 * @return deleteFlag 削除フラグ
	 */
	public String getDeleteFlag() {
		return deleteFlag;
	}

	/**
	 * deleteFlagを設定する
	 *
	 * @param deleteFlag
	 *     削除フラグ
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
	 * passwordExpireを返す
	 *
	 * @return passwordExpire パスワード有効期限
	 */
	public Date getPasswordExpire() {
		return passwordExpire;
	}

	/**
	 * passwordExpireを設定する
	 *
	 * @param passwordExpire
	 *     パスワード有効期限
	 */
	public void setPasswordExpire(Date passwordExpire) {
		this.passwordExpire = passwordExpire;
	}

	/**
	 * remarksを返す
	 *
	 * @return remarks 備考
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * remarksを設定する
	 *
	 * @param remarks
	 *     備考
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * fileEnclosureCharFlagを返す
	 *
	 * @return fileEnclosureCharFlag ファイル囲い文字利用フラグ
	 */
	public String getFileEnclosureCharFlag() {
		return fileEnclosureCharFlag;
	}

	/**
	 * fileEnclosureCharFlagを設定する
	 *
	 * @param fileEnclosureCharFlag
	 *     ファイル囲い文字利用フラグ
	 */
	public void setFileEnclosureCharFlag(String fileEnclosureCharFlag) {
		this.fileEnclosureCharFlag = fileEnclosureCharFlag;
	}

	/**
	 * healthInfoMaskFlagを返す<br>
	 *
	 * @return healthInfoMaskFlag 健康情報マスク利用フラグ
	 */
	public String getHealthInfoMaskFlag() {
		return healthInfoMaskFlag;
	}

	/**
	 * healthInfoMaskFlagを設定する<br>
	 *
	 * @param healthInfoMaskFlag
	 *     健康情報マスク利用フラグ
	 */
	public void setHealthInfoMaskFlag(String healthInfoMaskFlag) {
		this.healthInfoMaskFlag = healthInfoMaskFlag;
	}

	/**
	 * updateDateを返す
	 *
	 * @return updateDate 更新日時
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * updateDateを設定する
	 *
	 * @param updateDate
	 *     更新日時
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * regDateを返す
	 *
	 * @return regDate 登録日時
	 */
	public Date getRegDate() {
		return regDate;
	}

	/**
	 * regDateを設定する
	 *
	 * @param regDate
	 *     登録日時
	 */
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

}
