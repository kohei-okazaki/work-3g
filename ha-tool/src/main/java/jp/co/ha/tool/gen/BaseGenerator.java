package jp.co.ha.tool.gen;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.io.file.property.reader.PropertyReader;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.BaseEnum;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.tool.excel.DmlExcelReader;
import jp.co.ha.tool.excel.Excel;
import jp.co.ha.tool.excel.ExcelReader;
import jp.co.ha.tool.util.ToolUtil;

/**
 * 自動生成基底クラス
 *
 * @version 1.0.0
 */
public abstract class BaseGenerator {

    /** LOG */
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());
    /** 自動生成ツール設定ファイル情報 */
    protected ToolProperty prop;
    /** 自動生成ツールのExcel情報 */
    protected Excel excel;
    /** ファイル名：tool.properties */
    private static final String FILE_NAME_PROP = "tool.properties";

    /**
     * 自動生成を行う
     *
     * @throws Exception
     *     自動生成処理に失敗した場合
     */
    final void generate() throws Exception {

        LOG.debug("自動生成開始");

        try {
            // 自動生成ツール設定ファイルを取得
            this.prop = getProp();

            // Excelファイルを取得
            this.excel = getExcelReader().read(prop);

            // 自動生成個別処理
            List<GenerateFile> genFileList = generateImpl();

            // 自動生成ファイルを作成
            ToolUtil.createGenFileList(genFileList);

        } finally {
            LOG.debug("自動生成終了");
        }

    }

    /**
     * ExcelファイルのReaderを返す<br>
     * デフォルトで「TABLE_LIST」シートを読み込むReaderを返している<br>
     * DML作成時は本メソッドをOverrideして個別で{@linkplain DmlExcelReader}を返す
     *
     * @return ExcelReader
     */
    protected ExcelReader getExcelReader() {
        return new ExcelReader();
    }

    /**
     * 個別自動生成処理を行う<br>
     * 本クラスを継承して実処理を実装すること
     *
     * @return 自動生成ファイルリスト
     * @throws Exception
     *     自動生成個別処理が失敗した場合
     */
    abstract List<GenerateFile> generateImpl() throws Exception;

    /**
     * 設定ファイルを取得する
     *
     * @return ToolProperty
     * @throws BaseException
     *     設定ファイルの読み取りに失敗しました
     */
    private ToolProperty getProp() throws BaseException {

        Path path = null;
        try {
            path = Paths.get(this.getClass().getClassLoader().getResource("").toURI());
        } catch (URISyntaxException e) {
            throw new SystemException(CommonErrorCode.FILE_READING_ERROR,
                    FILE_NAME_PROP + "が見つかりません", e);
        }

        // 設定ファイルを取得
        ToolProperty prop = new PropertyReader().read(path.toString(), FILE_NAME_PROP,
                ToolProperty.class);
        Stream.of(prop.getTargetTables().split(StringUtil.COMMA))
                .forEach(e -> prop.addTargetTable(e));
        Stream.of(prop.getDmlTables().split(StringUtil.COMMA))
                .forEach(e -> prop.addDmlTable(e));
        return prop;

    }

    /**
     * 自動生成区分の列挙
     *
     * @version 1.0.0
     */
    public static enum GenerateType implements BaseEnum {

        /** Entity作成 */
        @SuppressWarnings("deprecation")
        ENTITY("ENTITY", "ha-db\\src\\main\\java\\jp\\co\\ha\\db\\entity",
                EntityGenerator.class),
        /** DDL作成 */
        DDL("DDL", "ha-asset\\02_db\\ddl", CreateTableGenerator.class),
        /** DROP作成 */
        DROP("DROP", "ha-asset\\02_db\\others", DropSqlGenerator.class),
        /** テーブル定義作成 */
        TABLE_DEFINE("TABLE_DEFINE", "ha-asset\\02_db\\others",
                TableDefineGenerator.class),
        /** DML作成 */
        DML("DML", "ha-asset\\02_db\\dml", DmlGenerator.class);

        /** 値 */
        private String value;
        /** 出力先パス */
        private String path;
        /** 自動生成クラス型 */
        private Class<? extends BaseGenerator> genClass;

        /**
         * コンストラクタ
         *
         * @param value
         *     値
         * @param path
         *     出力先パス
         * @param genClass
         *     自動生成クラス型
         */
        private GenerateType(String value, String path,
                Class<? extends BaseGenerator> genClass) {
            this.value = value;
            this.path = path;
            this.genClass = genClass;
        }

        @Override
        public String getValue() {
            return this.value;
        }

        /**
         * pathを返す
         *
         * @return path
         */
        public String getPath() {
            return path;
        }

        /**
         * genClassを返す
         *
         * @return genClass
         */
        public Class<? extends BaseGenerator> getGenClass() {
            return genClass;
        }

        /**
         * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
         * @param value
         *     値
         * @return GenerateType
         */
        public static GenerateType of(String value) {
            return BaseEnum.of(GenerateType.class, value);
        }
    }

}
