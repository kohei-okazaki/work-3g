package jp.co.ha.common.log;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLogger {

	/** ロガー */
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public void info(Object object) {
		StringJoiner sb = new StringJoiner(", ");

		Class<?> clazz = object.getClass();
		try {
			for (Field f : clazz.getDeclaredFields()) {
				Mask mask = f.getAnnotation(Mask.class);
				String name = f.getName();
				PropertyDescriptor pd = new PropertyDescriptor(name, clazz);
				if (mask != null) {
					System.out.println("マスク対象 : " + name);
					sb.add(name + "=****");
				} else {
					System.out.println("マスク対象でない : " + name);
					sb.add(name + "=" + pd.getReadMethod().invoke(object).toString());
				}
			}
		} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		System.out.println(sb.toString());
		LOG.info(sb.toString());
	}

}
