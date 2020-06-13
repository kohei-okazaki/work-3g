package jp.co.ha.tool.source;

import java.util.Map;
import java.util.StringJoiner;

import jp.co.ha.common.util.StringUtil;
import jp.co.ha.tool.source.type.AccessType;

/**
 * Field
 *
 * @version 1.0.0
 */
public class Field {

    /** フィールド名 */
    private String name;
    /** コメント */
    private String comment;
    /** 型 */
    private Class<?> classType;
    /** アクセスタイプ */
    private AccessType accessType;
    /** アノテーションMap */
    private Map<Class<?>, String> annotationMap;

    /**
     * コンストラクタ
     *
     * @param name
     *     フィールド名
     * @param comment
     *     コメント
     * @param classType
     *     型
     * @param annotationMap
     *     AnnotationMap
     */
    public Field(String name, String comment, Class<?> classType,
            Map<Class<?>, String> annotationMap) {
        this(name, comment, classType, AccessType.PRIVATE, annotationMap);
    }

    /**
     * コンストラクタ
     *
     * @param name
     *     フィールド名
     * @param comment
     *     コメント
     * @param classType
     *     型
     * @param accessType
     *     アクセスタイプ
     * @param annotationMap
     *     AnnotationMap
     */
    public Field(String name, String comment, Class<?> classType,
            AccessType accessType, Map<Class<?>, String> annotationMap) {
        this.name = name;
        this.comment = comment;
        this.classType = classType;
        this.accessType = accessType;
        this.annotationMap = annotationMap;
    }

    /**
     * nameを返す
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * nameを設定する
     *
     * @param name
     *     フィールド名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * commentを返す
     *
     * @return comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * commentを設定する
     *
     * @param comment
     *     コメント
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * classTypeを返す
     *
     * @return classType
     */
    public Class<?> getClassType() {
        return classType;
    }

    /**
     * classTypeを設定する
     *
     * @param classType
     *     型
     */
    public void setClassType(Class<?> classType) {
        this.classType = classType;
    }

    /**
     * accessTypeを返す
     *
     * @return accessType
     */
    public AccessType getAccessType() {
        return accessType;
    }

    /**
     * accessTypeを設定する
     *
     * @param accessType
     *     アクセスタイプ
     */
    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    /**
     * annotationMapを返す
     *
     * @return annotationMap
     */
    public Map<Class<?>, String> getAnnotationMap() {
        return annotationMap;
    }

    /**
     * annotationMapを設定する
     *
     * @param annotationMap
     *     アノテーションMap
     */
    public void setAnnotationMap(Map<Class<?>, String> annotationMap) {
        this.annotationMap = annotationMap;
    }

    @Override
    public String toString() {

        final String TAB = StringUtil.TAB;

        /* JavaDoc作成 */
        String javadocPrefix = "/**";
        String javadocSuffix = "*/";
        StringJoiner javadocBody = new StringJoiner(StringUtil.SPACE);
        javadocBody.add(javadocPrefix);
        javadocBody.add(comment);
        javadocBody.add(javadocSuffix);
        String javadoc = TAB + javadocBody.toString();

        /* annotation作成 */
        StringJoiner annotationBody = new StringJoiner(StringUtil.NEW_LINE);
        annotationMap.entrySet().stream().forEach(entry -> {
            if (StringUtil.isEmpty(entry.getValue())) {
                annotationBody.add(TAB + "@" + entry.getKey().getSimpleName());
            } else {
                annotationBody.add(TAB + "@" + entry.getKey().getSimpleName()
                        + "(" + entry.getValue() + ")");
            }
        });

        /* field作成 */
        StringJoiner fieldBody = new StringJoiner(StringUtil.SPACE);
        fieldBody.add(accessType.getValue());
        fieldBody.add(classType.getSimpleName());
        fieldBody.add(name);
        String field = TAB + fieldBody.toString() + ";";

        if (StringUtil.isEmpty(annotationBody.toString())) {
            return javadoc + StringUtil.NEW_LINE + field;
        }
        return javadoc + StringUtil.NEW_LINE + annotationBody
                + StringUtil.NEW_LINE + field;
    }
}
