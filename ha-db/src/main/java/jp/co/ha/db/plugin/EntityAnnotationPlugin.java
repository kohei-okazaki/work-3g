package jp.co.ha.db.plugin;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import jp.co.ha.common.db.annotation.Entity;

/**
 * 各XXXXEntityに{@linkplain Entity}を付与するためのプラグインクラス
 *
 * @version 1.0.0
 */
public class EntityAnnotationPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {

        // クラスにアノテーションを追加
        topLevelClass.addAnnotation("@Entity");

        // import文の追加
        topLevelClass.addImportedType("jp.co.ha.common.db.annotation.Entity");

        return true;
    }

}
