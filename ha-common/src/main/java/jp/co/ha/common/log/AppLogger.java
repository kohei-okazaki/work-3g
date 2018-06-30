package jp.co.ha.common.log;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.StringJoiner;

import org.slf4j.Logger;

import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateFormatDefine;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.StringUtil;

/**
 * アプリ内のロガークラス<br>
 *
 */
public class AppLogger {

	/** ロガー */
	private Logger logger;

	AppLogger(Logger logger) {
		this.logger = logger;
	}

	/**
	 * debugログ出力を行う<br>
	 *
	 * @param bean
	 *            対象Bean
	 */
	public void debug(Object bean) {
		logger.debug(getLogMessage(bean));
	}

	/**
	 * infoログ出力を行う<br>
	 *
	 * @param bean
	 *            対象Bean
	 */
	public void info(Object bean) {
		logger.info(getLogMessage(bean));
	}

	/**
	 * warnログ出力を行う<br>
	 *
	 * @param bean
	 *            対象Bean
	 */
	public void warn(Object bean) {
		logger.warn(getLogMessage(bean));
	}

	/**
	 * errorログ出力を行う<br>
	 *
	 * @param bean
	 *            対象Bean
	 */
	public void error(Object bean) {
		logger.error(getLogMessage(bean));
	}

	/**
	 * ログメッセージを返す<br>
	 *
	 * @param bean
	 *            対象Bean
	 * @return
	 */
	private String getLogMessage(Object bean) {

		StringJoiner sj = new StringJoiner(", ");
		Class<?> clazz = bean.getClass();

		for (Field f : BeanUtil.getFieldList(clazz)) {
			String name = f.getName();
			if (isIgnore(f)) {
				// 出力非対象項目
				continue;
			}
			if (isMask(f)) {
				// マスク対象項目
				sj.add(name + "=****");
			} else {
				// マスク非対象項目
				String strValue = editValue(getValue(bean, name));
				sj.add(name + "=" + strValue);
			}
		}
		return bean.getClass().getName() + " " + sj.toString();
	}

	/**
	 * 値を取得<br>
	 *
	 * @param bean
	 *            Bean
	 * @param fieldName
	 *            フィールド名
	 * @return
	 */
	private Object getValue(Object bean, String fieldName) {
		Class<?> clazz = bean.getClass();
		Object value = null;
		try {
			PropertyDescriptor pd = new PropertyDescriptor(fieldName, clazz);
			Method getter = pd.getReadMethod();
			value = getter.invoke(bean);
		} catch (IllegalAccessException e) {
			logger.error("不正アクセスです", e);
		} catch (IntrospectionException e) {
			logger.error("項目が不正です", e);
		} catch (IllegalArgumentException e) {
			logger.error("不正な引数です", e);
		} catch (InvocationTargetException e) {
			logger.error("項目が不正です", e);
		}

		return value;
	}

	/**
	 * 値を出力用に編集する<br>
	 *
	 * @param value
	 *            値
	 * @return
	 */
	private String editValue(Object value) {
		String strValue;
		if (BeanUtil.isNull(value)) {
			strValue = StringUtil.EMPTY;
		} else if (value instanceof Date) {
			strValue = DateUtil.toString((Date) value, DateFormatDefine.YYYYMMDD_HHMMSS);
		} else {
			strValue = value.toString();
		}
		return strValue;
	}

	/**
	 * マスク対象かどうか判定する<br>
	 * マスク対象の場合true, それ以外の場合false<br>
	 *
	 * @param field
	 *            フィールド名
	 * @return
	 */
	private boolean isMask(Field field) {
		return BeanUtil.notNull(field.getAnnotation(Mask.class));
	}

	/**
	 * 出力対象かどうか判定する<br>
	 * 出力対象の場合true, それ以外の場合false<br>
	 *
	 * @param field
	 *            フィールド名
	 * @return
	 */
	private boolean isIgnore(Field field) {
		return BeanUtil.notNull(field.getAnnotation(Ignore.class));
	}

}
