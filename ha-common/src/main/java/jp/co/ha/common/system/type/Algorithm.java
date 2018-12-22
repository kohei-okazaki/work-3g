package jp.co.ha.common.system.type;

import jp.co.ha.common.type.BaseEnum;

/**
 * ハッシュ生成のアルゴリズム列挙<br>
 *
 */
public enum Algorithm implements BaseEnum {

	/** SHA-256 */
	SHA_256("SHA-256");

	/**
	 * コンストラクタ
	 *
	 * @param value
	 *     value
	 */
	private Algorithm(String value) {
		this.value = value;
	}

	/** 値 */
	private String value;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValue() {
		return this.value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Algorithm of(String value) {
		return BaseEnum.of(this.getClass(), value);
	}
}
