package jp.co.ha.common.db;

import org.springframework.stereotype.Component;

/**
 * crypt.propertiesのBean
 *
 */
@Component
public class CryptConfig {

	/** Mode */
	private String mode;
	/** Key */
	private String key;

	/**
	 * modeを返す
	 *
	 * @return mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * modeを設定する
	 *
	 * @param mode
	 *     Mode
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * keyを返す
	 *
	 * @return key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * keyを設定する
	 *
	 * @param key
	 *     Key
	 */
	public void setKey(String key) {
		this.key = key;
	}

}
