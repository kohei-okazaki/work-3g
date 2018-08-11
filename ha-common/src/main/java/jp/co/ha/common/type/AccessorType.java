package jp.co.ha.common.type;

import jp.co.ha.common.util.BeanUtil;

/**
 * メソッドのアクセス列挙<br>
 * @see BeanUtil#getAccessor(String, Class, AccessorType)
 *
 */
public enum AccessorType {
	SETTER, GETTER;
}
