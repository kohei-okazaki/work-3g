package jp.co.ha.tool.source;

import java.util.StringJoiner;

import jp.co.ha.common.util.StringUtil;

/**
 * Import
 *
 * @since 1.0
 */
public class Import {

    /** クラス型 */
    private Class<?> clazz;

    /**
     * コンストラクタ
     *
     * @param clazz
     *     クラス型
     */
    public Import(Class<?> clazz) {
        this.clazz = clazz;
    }

    /**
     * コンストラクタ
     *
     * @param field
     *     フィールド情報
     */
    public Import(Field field) {
        this.clazz = field.getClassType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        String prefix = "import";
        String suffix = ";";
        StringJoiner body = new StringJoiner(StringUtil.SPACE);
        body.add(prefix);
        body.add(this.clazz.getName() + suffix);
        return body.toString();
    }

}
