package jp.co.ha.tool.gen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.ha.common.db.annotation.Entity;
import jp.co.ha.common.util.FileUtil.FileExtension;
import jp.co.ha.common.util.FileUtil.FileSeparator;
import jp.co.ha.tool.source.Field;
import jp.co.ha.tool.source.Getter;
import jp.co.ha.tool.source.Import;
import jp.co.ha.tool.source.JavaSource;
import jp.co.ha.tool.source.Setter;
import jp.co.ha.tool.source.type.AccessType;
import jp.co.ha.tool.source.type.ClassType;
import jp.co.ha.tool.util.ToolUtil;

/**
 * Entityのビルダー
 *
 * @deprecated MybatisのgeneratorでEntityを生成している為
 * @since 1.0
 */
@Deprecated
public class EntityGenerator extends BaseGenerator {

    @Override
    List<GenerateFile> generateImpl() throws Exception {

        // 自動生成ファイルリスト
        List<GenerateFile> list = new ArrayList<>();

        for (String table : prop.getTargetTableList()) {

            LOG.debug("テーブル名:" + table);

            JavaSource source = new JavaSource();
            setCommonInfo(source);

            excel.getRowList().stream()
                    .filter(row -> ToolUtil.isTargetTable(row, table))
                    .forEach(row -> {

                        source.setClassJavaDoc(ToolUtil.getLogicalName(row) + " Entity");
                        source.setClassName(
                                ToolUtil.toJavaFileName(ToolUtil.getPhysicalName(row)));

                        // fieldの設定
                        Field field = new Field(
                                ToolUtil.toCamelCase(ToolUtil.getFieldName(row)),
                                ToolUtil.getColumnComment(row),
                                ToolUtil.getClassType(row),
                                ToolUtil.getFieldAnnotationMap(row, source));
                        source.addField(field);

                        // fieldのimport文を設定
                        Import im = new Import(field);
                        source.addImport(im);

                        // setterの設定
                        Setter setter = new Setter(field);
                        source.addMethod(setter);

                        // getterの設定
                        Getter getter = new Getter(field);
                        source.addMethod(getter);
                    });

            GenerateFile generateFile = new GenerateFile();
            generateFile.setFileName(
                    ToolUtil.toJavaFileName(table) + FileExtension.JAVA.getValue());
            generateFile.setData(ToolUtil.toStrJavaSource(source, prop));
            generateFile.setOutputPath(prop.getBaseDir() + FileSeparator.SYSTEM.getValue()
                    + GenerateType.ENTITY.getPath());
            list.add(generateFile);
        }

        return list;
    }

    /**
     * 共通情報を設定する
     *
     * @param source
     *     JavaSource
     */
    private void setCommonInfo(JavaSource source) {

        // パッケージ
        source.setPackage(new jp.co.ha.tool.source.Package("jp.co.nok.db.entity"));
        // ソースのクラスタイプ
        source.setClassType(ClassType.CLASS);
        // ソースのアクセス修飾子
        source.setAccessType(AccessType.PUBLIC);
        // 実装するインターフェース
        source.addImplInterface(Serializable.class);
        // java.io.SerializableのImport
        source.addImport(new Import(Serializable.class));
        // クラスに付与するアノテーション
        source.addClassAnnotation(Entity.class, "");
        // jp.co.ha.common.db.annotation.EntityのImport
        source.addImport(new Import(Entity.class));

    }
}
