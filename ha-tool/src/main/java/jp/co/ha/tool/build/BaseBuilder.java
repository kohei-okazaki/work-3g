package jp.co.ha.tool.build;

import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jp.co.ha.tool.read.PropertyReader;
import jp.co.ha.tool.type.PropertyType;

public abstract class BaseBuilder {

	private Properties property;

	protected List<String> tableList;
	protected String baseDir;

	public BaseBuilder() {
		this.property = new PropertyReader().getProperty("target.properties");
		this.init();
	}

	private void init() {
		String target = get(PropertyType.TARGET_TABLE);
		if (Objects.nonNull(target)) {
			this.tableList = Stream.of(target.split(",")).collect(Collectors.toList());
		}

		this.baseDir = get(PropertyType.BASE_DIR);
	}

	private String get(PropertyType propType) {
		return this.property.getProperty(propType.getValue());
	}
}
