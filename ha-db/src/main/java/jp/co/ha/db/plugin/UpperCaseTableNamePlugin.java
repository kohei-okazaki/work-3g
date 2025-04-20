package jp.co.ha.db.plugin;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.config.TableConfiguration;

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

    @Override
    public void initialized(IntrospectedTable introspectedTable) {

        // TableConfigurationを取得
        TableConfiguration tableConfiguration = introspectedTable.getTableConfiguration();

        // 元のテーブル名を取得して大文字に変換
        String originalTableName = tableConfiguration.getTableName();
        String upperCaseTableName = originalTableName.toUpperCase();

        // テーブル名を大文字に設定
        tableConfiguration.setTableName(upperCaseTableName);
    }
}
