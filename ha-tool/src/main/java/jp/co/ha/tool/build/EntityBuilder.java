package jp.co.ha.tool.build;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.ha.tool.config.FileConfig;
import jp.co.ha.tool.factory.FileFactory;

public class EntityBuilder extends BaseBuilder {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	public void execute() {



		for (String table : this.tableList) {
			FileConfig fileConf = new FileConfig();
			fileConf.setFileName(toJavaFileName(table) + ".java");
			fileConf.setOutputPath(super.baseDir + "\\ha-tool\\src\\main\\java\\jp\\co\\ha\\common\\entity");
			fileConf.setData("package jp.co.ha.common.entity;\r\n\r\npublic class " + toJavaFileName(table) + " {\r\n\r\n}");
			new FileFactory().create(fileConf);
		}
	}

	private String toJavaFileName(String fileName) {

		String result = fileName.toLowerCase();

		// 1文字目を大文字にする
		Character startChar = result.charAt(0);
		Character large = Character.toUpperCase(result.charAt(0));
		result = result.replaceFirst(startChar.toString(), large.toString());

		// _c を Cに変換
		while (result.indexOf("_") != -1) {
			int pos = result.indexOf("_");
			String target = result.substring(pos, pos + 2);
			String after = target.replace("_", "").toUpperCase();
			result = result.replaceFirst(target, after);
		}
		return result;
	}
}
