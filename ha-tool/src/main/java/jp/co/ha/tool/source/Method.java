package jp.co.ha.tool.source;

import jp.co.ha.tool.type.AccessType;

public abstract class Method {

	protected Field field;
	protected AccessType accessType;

	public Method(Field field, AccessType accessType) {
		this.field = field;
		this.accessType = accessType;
	}

	protected abstract String getMethodName();

}
