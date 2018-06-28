package jp.co.ha.common.log;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateFormatDefine;
import jp.co.ha.common.util.DateUtil;

/**
 * アプリ内のロガークラス<br>
 *
 */
public class AppLogger {

	/** ロガー */
	private Logger logger;

	public AppLogger(Class<?> clazz) {
		this.logger = LoggerFactory.getLogger(clazz);
	}

	/**
	 * debugログ出力を行う<br>
	 * @param bean 対象Bean
	 */
	public void debug(Object bean) {
		logger.debug(getLogMessge(bean));
	}

	/**
	 * infoログ出力を行う<br>
	 * @param bean 対象Bean
	 */
	public void info(Object bean) {
		logger.info(getLogMessge(bean));
	}

	/**
	 * warnログ出力を行う<br>
	 * @param bean 対象Bean
	 */
	public void warn(Object bean) {
		logger.warn(getLogMessge(bean));
	}

	/**
	 * errorログ出力を行う<br>
	 * @param bean 対象Bean
	 */
	public void error(Object bean) {
		logger.error(getLogMessge(bean));
	}

	/**
	 * ログメッセージを返す<br>
	 * @param object
	 * @return
	 */
	private String getLogMessge(Object bean) {

		StringJoiner sj = new StringJoiner(", ");
		Class<?> clazz = bean.getClass();

		for (Field f : getFieldList(clazz)) {
			String fieldName = f.getName();
			if (isIgnore(f)) {
				// 出力非対象項目
				continue;
			}
			if (isMask(f)) {
				// マスク対象項目
				sj.add(fieldName + "=****");
			} else {
				// マスク非対象項目
				Object value = getValue(bean, fieldName);
				if (value instanceof Date) {
					value = DateUtil.toString((Date) value, DateFormatDefine.YYYYMMDD_HHMMSS);
				}
				sj.add(fieldName + "=" + value.toString());
			}
		}
		return sj.toString();
	}

	/**
	 * 値を取得<br>
	 * @param bean Beanクラス
	 * @param fieldName フィールド名
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
	 * マスク対象かどうか判定する<br>
	 * マスク対象の場合true, それ以外の場合false<br>
	 * @param field フィールド名
	 * @return
	 */
	private boolean isMask(Field field) {
		return BeanUtil.notNull(field.getAnnotation(Mask.class));
	}

	/**
	 * 出力対象かどうか判定する<br>
	 * 出力対象の場合true, それ以外の場合false<br>
	 * @param field フィールド名
	 * @return
	 */
	private boolean isIgnore(Field field) {
		return BeanUtil.notNull(field.getAnnotation(Ignore.class));
	}

	/**
	 * 指定したクラスのフィールドをリストで返す<br>
	 * @param clazz
	 * @return
	 */
	private List<Field> getFieldList(Class<?> clazz) {
		List<Field> fieldList = new ArrayList<Field>();
		while (BeanUtil.notNull(clazz)) {
			fieldList.addAll(List.of(clazz.getDeclaredFields()));
			clazz = clazz.getSuperclass();
		}
		return fieldList;

	}

}
