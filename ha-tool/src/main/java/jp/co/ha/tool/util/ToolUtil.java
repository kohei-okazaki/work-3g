package jp.co.ha.tool.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import jp.co.ha.common.db.annotation.Crypt;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.common.util.FileUtil;
import jp.co.ha.common.util.FileUtil.LineFeedType;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.tool.db.Column;
import jp.co.ha.tool.db.Table;
import jp.co.ha.tool.excel.Cell;
import jp.co.ha.tool.excel.Row;
import jp.co.ha.tool.excel.type.CellPositionType;
import jp.co.ha.tool.excel.type.ColumnType;
import jp.co.ha.tool.gen.GenerateFile;
import jp.co.ha.tool.gen.ToolProperty;
import jp.co.ha.tool.source.Import;
import jp.co.ha.tool.source.JavaSource;
import jp.co.ha.tool.source.type.ClassType;

/**
 * 自動生成ツールのUtilクラス
 *
 * @version 1.0.0
 */
public class ToolUtil {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(ToolUtil.class);

    /**
     * プライベートコンストラクタ
     */
    private ToolUtil() {
    }

    /**
     * 自動生成ファイルの作成処理を行う
     *
     * @param genFileList
     *     自動生成ファイルリスト
     * @throws IOException
     *     ファイルの作成に失敗した場合
     */
    public static void createGenFileList(List<GenerateFile> genFileList)
            throws IOException {

        for (GenerateFile genFile : genFileList) {
            Path path = FileUtil.createFile(genFile.getOutputPath()
                    + FileUtil.FileSeparator.SYSTEM.getValue() + genFile.getFileName());
            Files.write(path,
                    genFile.getData().getBytes(genFile.getCharset().getValue()));
            LOG.debug("自動生成:" + path.toFile().getName());
        }

    }

    /**
     * 指定されたテーブル名(物理)が処理対象の行であるかどうか判定する
     *
     * @param row
     *     行情報
     * @param tableName
     *     テーブル名
     * @return 処理対象の場合true、それ以外の場合false
     */
    public static boolean isTargetTable(Row row, String tableName) {
        Cell cell = row.getCell(CellPositionType.PHYSICAL_NAME);
        return tableName.equals(cell.getValue());
    }

    /**
     * 指定された行情報からテーブル名(物理)を返す
     *
     * @param row
     *     行情報
     * @return 物理名
     */
    public static String getPhysicalName(Row row) {
        return row.getCell(CellPositionType.PHYSICAL_NAME).getValue();
    }

    /**
     * 指定された行情報からテーブル名(論理)を返す
     *
     * @param row
     *     行情報
     * @return 論理名
     */
    public static String getLogicalName(Row row) {
        return row.getCell(CellPositionType.LOGICAL_NAME).getValue();
    }

    /**
     * 指定された行情報からカラムタイプを返す
     *
     * @param row
     *     行情報
     * @return カラムタイプ
     */
    public static String getColumnType(Row row) {
        StringJoiner body = new StringJoiner(StringUtil.SPACE);
        String columnType = row.getCell(CellPositionType.COLUMN_TYPE).getValue();
        if (isCrypt(row)) {
            columnType = "VARBINARY";
        }
        String size = getSize(row);
        body.add(columnType + size);
        return body.toString();
    }

    /**
     * 指定された行情報からカラムサイズを返す
     *
     * @param row
     *     行情報
     * @return カラムサイズ
     */
    public static String getSize(Row row) {
        String size = row.getCell(CellPositionType.COLUMN_SIZE).getValue();
        if (StringUtil.isBrank(size)) {
            return StringUtil.EMPTY;
        } else if (isCrypt(row)) {
            return "(" + String.valueOf(Integer.valueOf(size) * 4) + ")";
        } else {
            return "(" + size + ")";
        }
    }

    /**
     * 暗号化カラムかどうかを判定する
     *
     * @param row
     *     行情報
     * @return 暗号化カラムの場合true、それ以外の場合false
     */
    public static boolean isCrypt(Row row) {
        return CommonFlag.TRUE == CommonFlag
                .of(row.getCell(CellPositionType.CRYPT).getValue());
    }

    /**
     * シーケンスカラムかどうかを判定する
     *
     * @param row
     *     行情報
     * @return シーケンスカラムの場合true、それ以外の場合false
     */
    public static boolean isSequence(Row row) {
        return CommonFlag.TRUE == CommonFlag
                .of(row.getCell(CellPositionType.SEQUENCE).getValue());
    }

    /**
     * Not NULLかどうかを判定する
     *
     * @param row
     *     行情報
     * @return Not NULLの場合true、それ以外の場合false
     */
    public static boolean isNotNull(Row row) {
        return CommonFlag.TRUE == CommonFlag
                .of(row.getCell(CellPositionType.NOT_NULL).getValue());
    }

    /**
     * プライマリーキーかどうかを判定する
     *
     * @param row
     *     行情報
     * @return プライマリーキーの場合true、それ以外の場合false
     */
    public static boolean isPrimaryKey(Row row) {
        return CommonFlag.TRUE == CommonFlag
                .of(row.getCell(CellPositionType.PRIMARY_KEY).getValue());
    }

    /**
     * 指定された行情報からフィールド名を返す
     *
     * @param row
     *     行情報
     * @return フィールド名
     */
    public static String getFieldName(Row row) {
        return row.getCell(CellPositionType.COLUMN_NAME).getValue();
    }

    /**
     * 指定された行情報からクラスタイプを返す
     *
     * @param row
     *     行情報
     * @return クラスタイプ
     */
    public static Class<?> getClassType(Row row) {
        String columnType = row.getCell(CellPositionType.COLUMN_TYPE).getValue();
        return ColumnType.of(columnType).getClassType();
    }

    /**
     * 指定された行情報からカラム名(コメント)を返す
     *
     * @param row
     *     行情報
     * @return カラム名(コメント)
     */
    public static String getColumnComment(Row row) {
        return row.getCell(CellPositionType.COLUMN_NAME_COMMENT).getValue();
    }

    /**
     * 指定された行情報からカラム名を返す
     *
     * @param row
     *     行情報
     * @return カラム名
     */
    public static String getColumnName(Row row) {
        return row.getCell(CellPositionType.COLUMN_NAME).getValue();
    }

    /**
     * 指定された行情報からフィールドに付与するアノテーションのMapを返す
     *
     * @param row
     *     行情報
     * @param source
     *     Javaソース
     * @return フィールドに付与するアノテーションのMap
     */
    public static Map<Class<?>, String> getFieldAnnotationMap(Row row,
            JavaSource source) {

        Map<Class<?>, String> map = new HashMap<>();

        Cell cryptCell = row.getCell(CellPositionType.CRYPT);
        if (StringUtil.hasValue(cryptCell.getValue())) {
            map.put(Mask.class, "");
            source.addImport(new Import(Mask.class));

            map.put(Crypt.class, "");
            source.addImport(new Import(Crypt.class));
        }
        return map;
    }

    /**
     * Javaファイル名に変換する<br>
     * (例)<br>
     * TEST_NAME -> TestName<br>
     *
     * @param fileName
     *     ファイル名
     * @return Javaファイル名
     */
    public static String toJavaFileName(String fileName) {

        String result = toCamelCase(fileName);
        // 先頭の文字列を大文字に変換
        Character startChar = result.charAt(0);
        Character large = Character.toUpperCase(startChar);

        return result.replaceFirst(startChar.toString(), large.toString());
    }

    /**
     * <code>name</code>をキャメルケースに変換する<br>
     * (例)<br>
     * TEST_NAME -> testName<br>
     *
     * @param name
     *     テーブル名
     * @return キャメルケースに変換した文字列
     */
    public static String toCamelCase(String name) {

        String result = name.toLowerCase();
        while (result.indexOf(StringUtil.UNDER_SCORE) != -1) {
            int pos = result.indexOf(StringUtil.UNDER_SCORE);
            String target = result.substring(pos, pos + 2);
            String after = target.replace(StringUtil.UNDER_SCORE, StringUtil.EMPTY)
                    .toUpperCase();
            result = result.replaceFirst(target, after);
        }

        return result;
    }

    /**
     * 指定されたJavaSourceからクラスの文字列表現を返す
     *
     * @param source
     *     JavaSource
     * @param prop
     *     JavaSource
     * @return Entityクラスの文字列表現
     */
    public static String toStrJavaSource(JavaSource source, ToolProperty prop) {

        StringJoiner result = new StringJoiner(
                LineFeedType.CRLF.getValue() + LineFeedType.CRLF.getValue());

        // package情報
        result.add(toStrPackage(source));

        // import情報
        result.add(toStrImportList(source));

        // class情報
        result.add(
                toStrClassJavaDoc(source, prop)
                        + LineFeedType.CRLF.getValue()
                        + toStrClassAnnotation(source)
                        + LineFeedType.CRLF.getValue()
                        + toStrClassName(source)
                        + toStrExtendsClass(source)
                        + toStrInterfaceList(source)
                        + " {");

        // field情報
        if (!CollectionUtil.isEmpty(source.getFieldList())) {
            result.add(toStrFieldList(source));
        }

        // method情報
        if (!CollectionUtil.isEmpty(source.getMethodList())) {
            result.add(toStrMethodList(source));
        }

        result.add("}");

        return result.toString();
    }

    /**
     * 指定されたJavaSourceのClassのパッケージの文字列表現を返す
     *
     * @param source
     *     JavaSource
     * @return Javadocの文字列表現
     */
    public static String toStrPackage(JavaSource source) {
        return source.getPackage().toString();
    }

    /**
     * 指定されたJavaSourceのClassのImport文の文字列表現を返す
     *
     * @param source
     *     JavaSource
     * @return Javadocの文字列表現
     */
    public static String toStrImportList(JavaSource source) {

        List<String> strImportList = new ArrayList<>();
        source.getImportList().stream()
                .filter(e -> !strImportList.contains(e.toString()))
                .map(e -> e.toString())
                .forEach(e -> strImportList.add(e));
        StringJoiner packageBody = new StringJoiner(StringUtil.NEW_LINE);
        strImportList.stream().forEach(e -> packageBody.add(e));

        return packageBody.toString();
    }

    /**
     * 指定されたJavaSourceのClassのJavaDocの文字列表現を返す
     *
     * @param source
     *     JavaSource
     * @param prop
     *     自動生成ツール設定ファイル
     * @return Javadocの文字列表現
     */
    public static String toStrClassJavaDoc(JavaSource source, ToolProperty prop) {
        StringJoiner sj = new StringJoiner(StringUtil.NEW_LINE);
        sj.add("/**");
        sj.add(" * " + source.getClassJavaDoc());
        sj.add(" *");
        sj.add(" * @version " + prop.getVersion());
        sj.add(" */");
        return sj.toString();
    }

    /**
     * 指定されたJavaSourceのClassに付与するアノテーション部分を組み立てる
     *
     * @param source
     *     JavaSource
     * @return クラスに付与するアノテーション部分
     */
    public static String toStrClassAnnotation(JavaSource source) {

        StringJoiner body = new StringJoiner(StringUtil.NEW_LINE);
        source.getClassAnnotationMap().entrySet().stream()
                .forEach(e -> body.add("@" + e.getKey().getSimpleName() + e.getValue()));

        return body.toString();
    }

    /**
     * 指定されたJavaSourceのClass名部分を組み立てる<br>
     * ex<br>
     * <code>public class XXXX</code>
     *
     * @param source
     *     生成するJavaファイルのリソース
     * @return クラス名
     */
    public static String toStrClassName(JavaSource source) {

        String accessType = source.getAccessType().getValue();
        String classType = source.getClassType().getValue();
        String className = source.getClassName();
        StringJoiner body = new StringJoiner(StringUtil.SPACE);

        return body.add(accessType).add(classType).add(className).toString();
    }

    /**
     * 指定されたJavaSourceのClassの継承部分を組み立てる<br>
     * <cpde>extends AAAA</code>
     *
     * @param source
     *     生成するJavaファイルのリソース
     * @return Classの継承部分
     */
    public static String toStrExtendsClass(JavaSource source) {

        if (BeanUtil.isNull(source.getExtendsClass())) {
            return "";
        }

        return " extends " + source.getExtendsClass().getSimpleName();
    }

    /**
     * interfaceのリストの継承部分を組み立てる<br>
     * <code>implements AAAA, BBBB</code><br>
     * or<br>
     * <code>extends AAAA, BBBB</code>
     *
     * @param source
     *     生成するJavaファイルのリソース
     * @return interfaceのリストの継承部分
     */
    public static String toStrInterfaceList(JavaSource source) {

        if (CollectionUtil.isEmpty(source.getImplInterfaceList())) {
            return "";
        }

        String prefix = ClassType.CLASS == source.getClassType()
                ? " implements "
                : " extends ";
        StringJoiner body = new StringJoiner(StringUtil.COMMA + StringUtil.SPACE);
        source.getImplInterfaceList().stream().forEach(e -> body.add(e.getSimpleName()));

        return prefix + body.toString();
    }

    /**
     * 指定されたJavaSourceのClassのFieldの文字列表現を返す
     *
     * @param source
     *     JavaSource
     * @return Javadocの文字列表現
     */
    public static String toStrFieldList(JavaSource source) {

        StringJoiner body = new StringJoiner(StringUtil.NEW_LINE);
        source.getFieldList().stream().forEach(e -> body.add(e.toString()));
        return body.toString();
    }

    /**
     * 指定されたJavaSourceのClassのMethodの文字列表現を返す
     *
     * @param source
     *     JavaSource
     * @return Javadocの文字列表現
     */
    public static String toStrMethodList(JavaSource source) {

        StringJoiner body = new StringJoiner(StringUtil.NEW_LINE + StringUtil.NEW_LINE);
        source.getMethodList().stream().forEach(e -> body.add(e.toString()));
        return body.toString();
    }

    /**
     * テーブル情報リストを取得
     *
     * @param rowList
     *     行情報リスト
     * @return テーブル情報リスト
     */
    public static List<Table> getTableList(List<Row> rowList) {

        // header行を除外
        List<Row> list = CollectionUtil.copyList(rowList);
        list.remove(0);
        List<Table> tableList = new ArrayList<>();
        List<String> existTableList = new ArrayList<>();
        for (Row row : list) {
            String logicalName = row.getCell(CellPositionType.LOGICAL_NAME).getValue();
            String physicalName = row.getCell(CellPositionType.PHYSICAL_NAME).getValue();
            if (!containsTable(existTableList, physicalName)
                    && !StringUtil.isEmpty(physicalName)) {
                existTableList.add(physicalName);
                Table table = new Table();
                table.setLogicalName(logicalName);
                table.setPhysicalName(physicalName);
                tableList.add(table);
            }
        }
        return tableList;
    }

    /**
     * テーブル情報リストにテーブル名が含まれるか判定する
     *
     * @param tableList
     *     テーブル情報リスト
     * @param tableName
     *     テーブル名
     * @return テーブル名が含まれる場合true、それ以外の場合false
     */
    public static boolean containsTable(List<String> tableList, String tableName) {
        return tableList.contains(tableName);
    }

    /**
     * 指定された行情報リストから<code>jp.co.nok.tool.db.Table</code>を返す
     *
     * @param rowList
     *     行情報リスト
     * @param tableName
     *     テーブル名
     * @return Tableクラス
     */
    public static Table getTable(List<Row> rowList, String tableName) {

        Table table = new Table();
        table.setPhysicalName(tableName);
        String logicalName = rowList.stream()
                .filter(e -> isTargetTable(e, tableName))
                .map(e -> e.getCell(CellPositionType.LOGICAL_NAME))
                .collect(Collectors.toList())
                .get(0)
                .getValue();
        table.setLogicalName(logicalName);
        table.setColumnList(
                rowList.stream().filter(e -> isTargetTable(e, tableName))
                        .map(e -> {
                            Column column = new Column();
                            column.setName(getColumnName(e));
                            column.setComment(getColumnComment(e));
                            column.setType(getColumnType(e));
                            column.setPrimary(isPrimaryKey(e));
                            column.setSequence(isSequence(e));
                            column.setCrypt(isCrypt(e));
                            column.setNotNull(isNotNull(e));
                            return column;
                        }).collect(Collectors.toList()));

        return table;
    }
}
