package jp.co.ha.db.plugin;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import jp.co.ha.common.log.annotation.Ignore;

/**
 * 各XXXXEntity#serialVerisonUIDに{@linkplain Ignore}を付与するためのプラグインクラス
 *
 * @version 1.0.0
 */
public class AnnotatedSerializablePlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        addSerializableWithAnnotatedUID(topLevelClass);
        return true;
    }

    /**
     * Ignoreアノテーションを付与する
     * 
     * @param topLevelClass
     *     TopLevelClass
     */
    private void addSerializableWithAnnotatedUID(TopLevelClass topLevelClass) {

        // 既にserialVersionUID があるなら、スキップ
        for (Field field : topLevelClass.getFields()) {
            if ("serialVersionUID".equals(field.getName())) {
                return;
            }
        }

        // implements Serializable
        topLevelClass.addImportedType("java.io.Serializable");

        // フィールドの作成
        Field field = new Field("serialVersionUID", new FullyQualifiedJavaType("long"));
        field.setStatic(true);
        field.setFinal(true);
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setInitializationString("1L");

        // アノテーション付与
        topLevelClass.addImportedType("jp.co.ha.common.log.annotation.Ignore");
        field.addAnnotation("@Ignore");

        // 追加
        topLevelClass.addField(field);
    }

}
