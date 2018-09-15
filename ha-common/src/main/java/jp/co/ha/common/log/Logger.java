package jp.co.ha.common.log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.StringJoiner;

import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.common.type.AccessorType;
import jp.co.ha.common.type.DateFormatType;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.StringUtil;

/**
 * アプリ内のロガークラス<br>
 *
 */
public class Logger {

	/** ロガー */
	private org.slf4j.Logger logger;

	Logger(org.slf4j.Logger logger) {
		this.logger = logger;
	}

	public void debugRes(Object bean) {
		logger.debug(getLogMessage(bean));
	}

	public void debug(String msg) {
		logger.debug(msg);
	}

	public void infoRes(Object bean) {
		logger.info(getLogMessage(bean));
	}

	public void info(String msg) {
		logger.info(msg);
	}

	public void warnRes(Object bean) {
		logger.warn(getLogMessage(bean));
	}

	public void warn(String msg) {
		logger.warn(msg);
	}

	public void warn(String msg, Throwable t) {
		logger.warn(msg, t);
	}

	public void errorRes(Object bean) {
		logger.error(getLogMessage(bean));
	}

	public void error(String msg) {
		logger.error(msg);
	}

	public void error(String msg, Throwable t) {
		logger.error(msg, t);
	}

	/**
	 * ログメッセージを返す<br>
	 *
	 * @param bean
	 *     Bean
	 * @return
	 */
	private String getLogMessage(Object bean) {

		StringJoiner body = new StringJoiner(", ");
		Class<?> clazz = bean.getClass();

		for (Field f : BeanUtil.getFieldList(clazz)) {
			if (isIgnore(f)) {
				// 出力非対象項目
				continue;
			}
			String name = f.getName();
			if (MaskExecutor.isMask(f)) {
				body.add(name + StringUtil.EQUAL + MaskExecutor.MASK);
			} else {
				body.add(name + StringUtil.EQUAL + editValue(getValue(bean, name)));
			}
		}
		return clazz.getName() + StringUtil.SPACE + body.toString();
	}

	/**
	 * 値を取得<br>
	 *
	 * @param bean
	 *     Bean
	 * @param fieldName
	 *     フィールド名
	 * @return
	 */
	private Object getValue(Object bean, String fieldName) {
		Object value = null;
		try {
			Method getter = BeanUtil.getAccessor(fieldName, bean.getClass(), AccessorType.GETTER);
			value = getter.invoke(bean);
		} catch (IllegalAccessException e) {
			logger.error("不正アクセスです" + fieldName, e);
		} catch (IllegalArgumentException e) {
			logger.error("不正な引数です" + fieldName, e);
		} catch (InvocationTargetException e) {
			logger.error("項目が不正です" + fieldName, e);
		}

		return value;
	}

	/**
	 * 値を出力用に編集する<br>
	 *
	 * @param value
	 *     値
	 * @return
	 */
	private String editValue(Object value) {
		String strValue;
		if (BeanUtil.isNull(value)) {
			strValue = StringUtil.EMPTY;
		} else if (value instanceof Date) {
			strValue = DateUtil.toString((Date) value, DateFormatType.YYYYMMDD_HHMMSS);
		} else {
			strValue = value.toString();
		}
		return strValue;
	}

	/**
	 * 出力対象かどうか判定する<br>
	 * 出力対象の場合true, それ以外の場合false<br>
	 *
	 * @param field
	 *     フィールド名
	 * @return
	 */
	private boolean isIgnore(Field field) {
		return BeanUtil.notNull(field.getAnnotation(Ignore.class));
	}

}
