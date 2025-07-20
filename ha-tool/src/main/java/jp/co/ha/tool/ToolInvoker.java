package jp.co.ha.tool;

import jp.co.ha.tool.gen.BaseGenerator.GenerateType;
import jp.co.ha.tool.gen.GenerateInvoker;

/**
 * 自動生成起動クラス
 *
 * @version 1.0.0
 */
public class ToolInvoker {

    /**
     * 実行したい自動生成の列挙を指定してください
     * <ul>
     * <li>{@linkplain GenerateType#ADD_COLUMN} = カラム追加SQLを作成(未実装)</li>
     * <li>{@linkplain GenerateType#DDL} = createテーブルSQLを作成</li>
     * <li>{@linkplain GenerateType#TABLE_DEFINE} =テーブルの定義SQLを作成</li>
     * <li>{@linkplain GenerateType#DROP} = テーブルを削除するSQLを作成</li>
     * <li>{@linkplain GenerateType#ENTITY} = Entityを作成</li>
     * </ul>
     *
     * @param args
     *     使わない
     */
    public static void main(String[] args) {
        // GenerateInvoker.invoke(GenerateType.TABLE_DEFINE);
        GenerateInvoker.invoke(GenerateType.DDL);
        // GenerateInvoker.invoke(GenerateType.DROP);
        // GenerateInvoker.invoke(GenerateType.DML);
        // GenerateInvoker.invoke(GenerateType.ENTITY);
    }

}
