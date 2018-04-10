package jp.co.ha.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * アカウント情報Entity<br>
 *
 */
@Entity
@Table(name = "ACCOUNT")
public class Account implements Serializable {

	/** シリアルバージョンUID */
	private static final Long serialVersionUID = 1L;

	/** ユーザID */
	@Id
	@Column(name = "USER_ID", nullable = false, length = 16)
	private String userId;

	/** パスワード */
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

	/** 更新日時 */
	@Column(name = "UPDATE_DATE", nullable = true)
	private Date updateDate;

	/** 登録日時 */
	@Column(name = "REG_DATE", nullable = false)
	private Date regDate;

	/**
	 * userIdを返す
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * userIdを設定する
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * passwordを返す
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * passwordを設定する
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * deleteFlagを返す
	 * @return deleteFlag
	 */
	public String getDeleteFlag() {
		return deleteFlag;
	}

	/**
	 * deleteFlagを設定する
	 * @param deleteFlag
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
	 * passwordExpireを返す
	 * @return passwordExpire
	 */
	public Date getPasswordExpire() {
		return passwordExpire;
	}

	/**
	 * passwordExpireを設定する
	 * @param passwordExpire
	 */
	public void setPasswordExpire(Date passwordExpire) {
		this.passwordExpire = passwordExpire;
	}

	/**
	 * remarksを返す
	 * @return remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * remarksを設定する
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * fileEnclosureCharFlagを返す
	 * @return fileEnclosureCharFlag
	 */
	public String getFileEnclosureCharFlag() {
		return fileEnclosureCharFlag;
	}

	/**
	 * fileEnclosureCharFlagを設定する
	 * @param fileEnclosureCharFlag
	 */
	public void setFileEnclosureCharFlag(String fileEnclosureCharFlag) {
		this.fileEnclosureCharFlag = fileEnclosureCharFlag;
	}

	/**
	 * updateDateを返す
	 * @return updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * updateDateを設定する
	 * @param updateDate
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * regDateを返す
	 * @return regDate
	 */
	public Date getRegDate() {
		return regDate;
	}

	/**
	 * regDateを設定する
	 * @param regDate
	 */
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

}

