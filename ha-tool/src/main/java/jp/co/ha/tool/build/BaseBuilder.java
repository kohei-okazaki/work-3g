package jp.co.ha.tool.build;

import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jp.co.ha.tool.read.PropertyReader;

public abstract class BaseBuilder {

	protected List<String> tableList;

	public BaseBuilder() {
		Properties prop = new PropertyReader().getProperty("target.properties");

		String target = prop.getProperty("target");
		if (Objects.nonNull(target)) {
			this.tableList = Stream.of(target.split(",")).collect(Collectors.toList());
		}
	}
}
