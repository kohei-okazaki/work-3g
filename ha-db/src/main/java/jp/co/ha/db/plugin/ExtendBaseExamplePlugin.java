package jp.co.ha.db.plugin;

import java.util.List;

import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import jp.co.ha.db.entity.BaseExample;

/**
 * 各XXXXExampleに{@linkplain BaseExample}を継承させるためのプラグインクラス
 *
 * @since 1.0
 */
public class ExtendBaseExamplePlugin extends PluginAdapter {

	/** クラス情報を保持するクラス */
	private FullyQualifiedJavaType bean;

	/**
	 * コンストラクタ
	 */
	public ExtendBaseExamplePlugin() {
		this.bean = new FullyQualifiedJavaType("jp.co.ha.db.entity.BaseExample");
	}

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		extendsBaseExample(topLevelClass, introspectedTable.getFullyQualifiedTable());
		return true;
	}

	/**
	 * {@linkplain BaseExample}を継承させる
	 *
	 * @param topLevelClass
	 *     TopLevelClass
	 * @param table
	 *     FullyQualifiedTable
	 */
	private void extendsBaseExample(TopLevelClass topLevelClass, FullyQualifiedTable table) {
		topLevelClass.addImportedType(bean);
		topLevelClass.setSuperClass(bean);
	}

}
