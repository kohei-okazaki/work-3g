package jp.co.ha.tool.gen;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.property.reader.PropertyReader;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.BaseEnum;
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
            this.prop = readProp();

            // Excelファイルを取得
            this.excel = new ExcelReader().read(prop);

            // 自動生成個別処理
            List<GenerateFile> genFileList = generateImpl();

            // 自動生成ファイルを作成
            ToolUtil.createGenFileList(genFileList);

        } finally {
            LOG.debug("自動生成終了");
        }

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
     * 設定ファイルを読込を行う
     *
     * @return 設定ファイル
     * @throws URISyntaxException
     *     パスの指定が不正な場合
     */
    private ToolProperty readProp() throws URISyntaxException {

        Path path = Paths.get(this.getClass().getClassLoader().getResource("").toURI());
        String classDir = path.toString();

        try {
            // 設定ファイルを取得
            ToolProperty prop = new PropertyReader().read(classDir, "tool.properties",
                    ToolProperty.class);
            Stream.of(prop.getTargetTables().split(","))
                    .forEach(e -> prop.addTargetTable(e));
            return prop;
        } catch (BaseException e) {
            return null;
        }
    }

    /**
     * 自動生成区分の列挙
     *
     * @since 1.0
     */
    public static enum GenerateType implements BaseEnum {

        /** Entity作成 */
        @SuppressWarnings("deprecation")
        ENTITY("ENTITY", "ha-db\\src\\main\\java\\jp\\co\\ha\\db\\entity",
                EntityGenerator.class),
        /** DDL作成 */
        DDL("DDL", "ha-resource\\02_db\\ddl", CreateTableGenerator.class),
        /** DROP作成 */
        DROP("DROP", "ha-resource\\02_db\\drop", DropSqlGenerator.class),
        /** テーブル定義作成 */
        TABLE_DEFINE("TABLE_DEFINE", "ha-resource\\02_db\\others",
                TableDefineGenerator.class);

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
