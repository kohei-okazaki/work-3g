package jp.co.ha.common.system;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import jp.co.ha.common.util.BeanUtil;

public class BeanLoader {

	private static ApplicationContext context;

	private BeanLoader() {
	}

	private static ApplicationContext getContext() {

		if (BeanUtil.notNull(context)) {
			return context;
		}
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		context = RequestContextUtils.findWebApplicationContext(sra.getRequest());
		return context;
	}

	public static <T> T getBean(Class<T> clazz) {
		try {
			return getContext().getBean(clazz);
		} catch (NoSuchBeanDefinitionException e) {
			return null;
		}
	}

	public static void setContext(ApplicationContext context) {
		BeanLoader.context = context;
	}
}
