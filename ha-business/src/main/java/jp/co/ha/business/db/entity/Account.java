package jp.co.ha.business.db.entity;

import java.io.Serializable;
import java.util.Date;

import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.common.log.annotation.Mask;

public class Account implements Serializable {
	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column account.USER_ID
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	private String userId;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column account.PASSWORD
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	@Mask
	private String password;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column account.DELETE_FLAG
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	private String deleteFlag;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column account.PASSWORD_EXPIRE
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	private Date passwordExpire;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column account.REMARKS
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	private String remarks;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column account.UPDATE_DATE
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	private Date updateDate;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column account.REG_DATE
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	private Date regDate;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column account.API_KEY
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	private String apiKey;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database table account
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	@Ignore
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column account.USER_ID
	 *
	 * @return the value of account.USER_ID
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column account.USER_ID
	 *
	 * @param userId
	 *     the value for account.USER_ID
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column account.PASSWORD
	 *
	 * @return the value of account.PASSWORD
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column account.PASSWORD
	 *
	 * @param password
	 *     the value for account.PASSWORD
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column account.DELETE_FLAG
	 *
	 * @return the value of account.DELETE_FLAG
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	public String getDeleteFlag() {
		return deleteFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column account.DELETE_FLAG
	 *
	 * @param deleteFlag
	 *     the value for account.DELETE_FLAG
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column account.PASSWORD_EXPIRE
	 *
	 * @return the value of account.PASSWORD_EXPIRE
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	public Date getPasswordExpire() {
		return passwordExpire;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column account.PASSWORD_EXPIRE
	 *
	 * @param passwordExpire
	 *     the value for account.PASSWORD_EXPIRE
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	public void setPasswordExpire(Date passwordExpire) {
		this.passwordExpire = passwordExpire;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column account.REMARKS
	 *
	 * @return the value of account.REMARKS
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column account.REMARKS
	 *
	 * @param remarks
	 *     the value for account.REMARKS
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column account.UPDATE_DATE
	 *
	 * @return the value of account.UPDATE_DATE
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column account.UPDATE_DATE
	 *
	 * @param updateDate
	 *     the value for account.UPDATE_DATE
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column account.REG_DATE
	 *
	 * @return the value of account.REG_DATE
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	public Date getRegDate() {
		return regDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column account.REG_DATE
	 *
	 * @param regDate
	 *     the value for account.REG_DATE
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column account.API_KEY
	 *
	 * @return the value of account.API_KEY
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column account.API_KEY
	 *
	 * @param apiKey
	 *     the value for account.API_KEY
	 *
	 * @mbg.generated Fri Sep 21 22:06:33 JST 2018
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
}