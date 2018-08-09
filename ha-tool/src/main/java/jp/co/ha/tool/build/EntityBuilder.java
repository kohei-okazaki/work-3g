package jp.co.ha.tool.build;

import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.ha.tool.reader.PropertyReader;

public class EntityBuilder {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	/** Tableリスト */
	private List<String> tableList;

	public EntityBuilder() {
		this.init();
	}

	private void init() {

		Properties prop = new PropertyReader().getProperty("target.properties");
		String target = prop.getProperty("target");
		if (Objects.nonNull(target)) {
			this.tableList = Stream.of(target.split(",")).collect(Collectors.toList());
		}

	}

	public void execute() {

	}
}
