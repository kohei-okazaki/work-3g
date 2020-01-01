package jp.co.ha.common.system;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.BeanUtil;

/**
 * Bean読込クラス
 *
 * @since 1.0
 */
public class BeanLoader {

	/** LOG */
	private static final Logger LOG = LoggerFactory.getLogger(BeanLoader.class);

	/** {@linkplain ApplicationContext} */
	private static ApplicationContext context;

	/**
	 * プライベートコンストラクタ
	 */
	private BeanLoader() {
	}

	/**
	 * Beanを取得
	 *
	 * @param clazz
	 *     Beanの実装クラス
	 * @return Bean
	 */
	public static <T> T getBean(Class<T> clazz) {
		try {
			return getContext().getBean(clazz);
		} catch (NoUniqueBeanDefinitionException e) {
			LOG.warn("同一のBeanが登録されています" + clazz.getName(), e);
			return null;
		} catch (NoSuchBeanDefinitionException e) {
			LOG.warn(clazz.getName() + "のBeanの取得に失敗しました", e);
			return null;
		}
	}

	/**
	 * ApplicationContextを返す
	 *
	 * @return ApplicationContext
	 */
	private static ApplicationContext getContext() {

		if (BeanUtil.notNull(context)) {
			return context;
		}
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		context = RequestContextUtils.findWebApplicationContext(sra.getRequest());
		return context;
	}

	/**
	 * ApplicationContextを設定する
	 *
	 * @param context
	 *     ApplicationContext
	 */
	public static void setContext(ApplicationContext context) {
		BeanLoader.context = context;
	}
}
