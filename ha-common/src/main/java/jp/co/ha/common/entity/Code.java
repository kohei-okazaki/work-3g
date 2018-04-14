package jp.co.ha.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 定数Entity<br>
 *
 */
@Entity
@Table(name = "CODE")
public class Code implements Serializable {

	/** シリアルバージョンUID */
	private static final long serialVersionUID = 1L;

	@Column(name = "MAIN_KEY", nullable = false)
	private String mainKey;

	@Column(name = "SUB_KEY", nullable = false)
	private String subKey;

	@Column(name = "VALUE", nullable = false)
	private String value;

	/**
	 * mainKeyを返す
	 * @return mainKey
	 */
	public String getMainKey() {
		return mainKey;
	}

	/**
	 * mainKeyを設定する
	 * @param mainKey
	 */
	public void setMainKey(String mainKey) {
		this.mainKey = mainKey;
	}

	/**
	 * subKeyを返す
	 * @return subKey
	 */
	public String getSubKey() {
		return subKey;
	}

	/**
	 * subKeyを設定する
	 * @param subKey
	 */
	public void setSubKey(String subKey) {
		this.subKey = subKey;
	}

	/**
	 * valueを返す
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * valueを設定する
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
