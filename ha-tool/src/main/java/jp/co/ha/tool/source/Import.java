package jp.co.ha.tool.source;

import java.util.StringJoiner;

import jp.co.ha.common.util.StringUtil;

public class Import {

	private Class<?> clazz;

	public Import(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Import(Field field) {
		this.clazz = field.getClassType();
	}

	@Override
	public String toString() {
		String prefix = "import";
		String suffix = ";";
		StringJoiner body = new StringJoiner(StringUtil.SPACE);
		body.add(prefix);
		body.add(this.clazz.getName() + suffix);
		return body.toString();
	}

}
