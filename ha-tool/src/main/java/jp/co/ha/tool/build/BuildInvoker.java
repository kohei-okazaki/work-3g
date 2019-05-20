package jp.co.ha.tool.build;

import java.lang.reflect.Method;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.tool.build.annotation.Build;

public class BuildInvoker {

	/** LOG */
	private static final Logger LOG = LoggerFactory.getLogger(BuildInvoker.class);

	/**
	 * 指定したビルドクラスのメイン処理を実行する
	 *
	 * @param names
	 *     ビルド名
	 */
	@SuppressWarnings("unchecked")
	public static void build(final String... names) {
		try {
			for (String name : names) {
				Class<? extends BaseBuilder> builderClass = (Class<BaseBuilder>) Class
						.forName("jp.co.ha.tool.build." + name);
				for (Method m : builderClass.getDeclaredMethods()) {
					if (m.isAnnotationPresent(Build.class)) {
						BaseBuilder builder = builderClass.getDeclaredConstructor().newInstance();
						m.invoke(builder);
					}
				}
			}
		} catch (Exception e) {
			LOG.error("SQLの生成処理が失敗しました", e);
		}
	}
}
