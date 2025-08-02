package jp.co.ha.db.plugin;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.VisitableElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * MyBatisで自動生成するxmlファイルのテーブル名を大文字に変換するプラグインクラス
 * 
 * @version 1.0.0
 */
public class UpperCaseTableNamePlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    /* INSERT */
    @Override
    public boolean sqlMapInsertElementGenerated(XmlElement element,
            IntrospectedTable introspectedTable) {
        overrideTableName(element, introspectedTable);
        return true;
    }

    @Override
    public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element,
            IntrospectedTable introspectedTable) {
        overrideTableName(element, introspectedTable);
        return true;
    }

    /* DELETE */
    @Override
    public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element,
            IntrospectedTable introspectedTable) {
        overrideTableName(element, introspectedTable);
        return true;
    }

    @Override
    public boolean sqlMapDeleteByExampleElementGenerated(XmlElement element,
            IntrospectedTable introspectedTable) {
        overrideTableName(element, introspectedTable);
        return true;
    }

    /* UPDATE */
    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
        overrideTableName(element, introspectedTable);
        return true;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(XmlElement element,
            IntrospectedTable introspectedTable) {
        overrideTableName(element, introspectedTable);
        return true;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(XmlElement element,
            IntrospectedTable introspectedTable) {
        overrideTableName(element, introspectedTable);
        return true;
    }

    @Override
    public boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(XmlElement element,
            IntrospectedTable introspectedTable) {
        overrideTableName(element, introspectedTable);
        return true;
    }

    @Override
    public boolean sqlMapUpdateByExampleWithBLOBsElementGenerated(XmlElement element,
            IntrospectedTable introspectedTable) {
        overrideTableName(element, introspectedTable);
        return true;
    }

    @Override
    public boolean sqlMapUpdateByExampleSelectiveElementGenerated(XmlElement element,
            IntrospectedTable introspectedTable) {
        overrideTableName(element, introspectedTable);
        return true;
    }

    /* SELECT */
    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element,
            IntrospectedTable introspectedTable) {
        overrideTableName(element, introspectedTable);
        return true;
    }

    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element,
            IntrospectedTable introspectedTable) {
        overrideTableName(element, introspectedTable);
        return true;
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element,
            IntrospectedTable introspectedTable) {
        overrideTableName(element, introspectedTable);
        return true;
    }

    /**
     * テーブル名を大文字で上書きする
     *
     * @param element
     *     XmlElement
     * @param introspectedTable
     *     IntrospectedTable
     */
    private void overrideTableName(XmlElement element,
            IntrospectedTable introspectedTable) {

        // 変換前テーブル名
        String originalTableName = introspectedTable
                .getFullyQualifiedTableNameAtRuntime();
        // 変換後テーブル名
        String upperCaseTableName = originalTableName.toUpperCase();

        for (VisitableElement e : element.getElements()) {
            if (e instanceof TextElement) {
                TextElement textElement = (TextElement) e;
                String content = textElement.getContent();
                // 元のテーブル名を見つけて大文字に置換
                if (content.contains(originalTableName)) {
                    element.getElements().set(
                            element.getElements().indexOf(e),
                            new TextElement(content.replace(originalTableName,
                                    upperCaseTableName)));
                }
            }
        }
    }

}
