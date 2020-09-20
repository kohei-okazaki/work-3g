package jp.co.ha.common.log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.StringJoiner;

import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.common.log.annotation.LogParam;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.BeanUtil.AccessorType;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.common.util.StringUtil;

/**
 * ログメッセージ作成クラス
 *
 * @version 1.0.0
 */
public class LogMessageFactory {

    /** LOG */
    private final static Logger LOG = LoggerFactory.getLogger(LogMessageFactory.class);

    /**
     * プライベートコンストラクタ
     */
    private LogMessageFactory() {
    }

    /**
     * ログメッセージを返す
     *
     * @param bean
     *     Bean
     * @return ログメッセージ
     */
    public static String toString(Object bean) {

        if (BeanUtil.isNull(bean)) {
            return "<NULL>";
        }

        StringJoiner body = new StringJoiner(StringUtil.COMMA);
        Class<?> clazz = bean.getClass();

        for (Field f : BeanUtil.getFieldList(clazz)) {
            if (isIgnore(f)) {
                // 出力非対象項目
                continue;
            }
            String name = getLogParamName(f);
            if (MaskExecutor.isMask(f)) {
                body.add(name + StringUtil.EQUAL + MaskExecutor.getMask(f));
            } else {
                body.add(name + StringUtil.EQUAL + editValue(getValue(bean, name)));
            }
        }
        return body.toString();
    }

    /**
     * Log出力時のパラメータを返す<br>
     * <code>@LogParam</code>が指定されてない場合、フィールド名をそのまま返す<br>
     * 指定されてる場合、<code>@LogParam</code>のパラメータ名を返す
     *
     * @param field
     *     フィールド名
     * @return パラメータ名
     */
    private static String getLogParamName(Field field) {
        LogParam annotation = field.getAnnotation(LogParam.class);
        return BeanUtil.isNull(annotation) ? field.getName() : annotation.name();
    }

    /**
     * 出力対象かどうか判定する<br>
     * 出力対象で無い場合true, それ以外の場合false
     *
     * @param field
     *     フィールド名
     * @return 判定結果
     */
    private static boolean isIgnore(Field field) {
        return field.isAnnotationPresent(Ignore.class);
    }

    /**
     * 値を取得
     *
     * @param bean
     *     Bean
     * @param fieldName
     *     フィールド名
     * @return value
     */
    private static Object getValue(Object bean, String fieldName) {
        Object value = null;
        try {
            Method getter = BeanUtil.getAccessor(fieldName, bean.getClass(),
                    AccessorType.GETTER);
            value = getter.invoke(bean);
        } catch (IllegalAccessException e) {
            LOG.error("フィールドに対して不正アクセスです フィールド：" + fieldName, e);
        } catch (IllegalArgumentException e) {
            LOG.error("不正な引数です" + fieldName, e);
        } catch (InvocationTargetException e) {
            LOG.error("項目が不正です" + fieldName, e);
        }
        return value;
    }

    /**
     * 値を出力用に編集する
     *
     * @param value
     *     値
     * @return 編集後の値
     */
    private static String editValue(Object value) {
        String strValue;
        if (BeanUtil.isNull(value)) {
            strValue = "<NULL>";
        } else if (value instanceof LocalDateTime) {
            strValue = DateTimeUtil.toString((LocalDateTime) value,
                    DateFormatType.YYYYMMDDHHMMSS);
        } else if (value instanceof LocalDate) {
            strValue = DateTimeUtil.toString((LocalDate) value,
                    DateFormatType.YYYYMMDD);
        } else {
            strValue = value.toString();
        }
        return strValue;
    }

}
