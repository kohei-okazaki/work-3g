package jp.co.ha.common.system;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.BeanUtil;

/**
 * Bean読込クラス(batch用)<br>
 * TODO jp.co.ha.common.system.BeanLoader からbeanを取得するように修正する予定
 *
 */
public class BatchBeanLoader implements ApplicationContextAware {

	/** LOG */
	private static final Logger LOG = LoggerFactory.getLogger(BatchBeanLoader.class);

	/** ApplicationContext */
	private static ApplicationContext context;

	/**
	 * プライベートコンストラクタ
	 */
	private BatchBeanLoader() {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		BatchBeanLoader.context = applicationContext;
	}

	/**
	 * Beanを取得<br>
	 * (例)<br>
	 * AccountSearchService searchService = BatchBeanLoader.getBean(AccountSearchService.class);
	 *
	 * @param clazz
	 *     Beanの型
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

	private static ApplicationContext getContext() {

		if (BeanUtil.notNull(context)) {
			return context;
		}

		// XMLから取得
		String[] xmls = new String[] { "classpath:batch-context.xml" };
		ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext(xmls);
		cxt.refresh();
		context = cxt;

		return context;
	}

}
