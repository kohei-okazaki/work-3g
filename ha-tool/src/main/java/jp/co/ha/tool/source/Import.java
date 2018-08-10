package jp.co.ha.tool.source;

import java.util.StringJoiner;

public class Import {

	private Class<?> clazz;

	public Import(Field field) {
		this.clazz = field.getClassType();
	}

	@Override
	public String toString() {
		StringJoiner result = new StringJoiner(" ");
		result.add("import");
		result.add(clazz.getName() + ";");
		return result.toString();
	}

	public boolean isEquals(Import im) {
		return im.getClass().equals(clazz);
	}
}
