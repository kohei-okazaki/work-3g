package jp.co.ha.common.log;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
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
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	/**
	 * infoログ出力を行う<br>
	 * @param object 対象Bean
	 */
	public void info(Object object) {
		StringJoiner sj = new StringJoiner(", ");
		Class<?> clazz = object.getClass();
		try {
			for (Field f : clazz.getDeclaredFields()) {
				String fieldName = f.getName();
				if (BeanUtil.notNull(f.getAnnotation(Ignore.class))) {
					// 出力非対象項目
					continue;
				}
				if (BeanUtil.isNull(f.getAnnotation(Mask.class))) {
					// マスク非対象項目
					PropertyDescriptor pd = new PropertyDescriptor(fieldName, clazz);
					Method getter = pd.getReadMethod();
					Object value = getter.invoke(object);
					if (value instanceof Date) {
						value = DateUtil.toString((Date) value, DateFormatDefine.YYYYMMDD_HHMMSS);
					}
					sj.add(fieldName + "=" + value.toString());
				} else {
					// マスク対象項目
					sj.add(fieldName + "=****");
				}
			}
		} catch (IllegalAccessException e) {
			LOG.error("不正アクセスです", e);
		} catch (IntrospectionException e) {
			LOG.error("項目が不正です", e);
		} catch (IllegalArgumentException e) {
			LOG.error("不正な引数です", e);
		} catch (InvocationTargetException e) {
			LOG.error("項目が不正です", e);
		}
		LOG.info(sj.toString());
	}

}
