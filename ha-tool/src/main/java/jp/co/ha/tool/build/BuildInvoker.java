package jp.co.ha.tool.build;

import java.lang.reflect.Method;
import java.util.List;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.tool.build.annotation.Build;
import jp.co.ha.tool.config.FileConfig;
import jp.co.ha.tool.factory.FileFactory;

/**
 * 自動生成ツールのinvokeクラス
 *
 * @since 1.0
 */
public class BuildInvoker {

	/** LOG */
	private static final Logger LOG = LoggerFactory.getLogger(BuildInvoker.class);

	/** builerクラスパッケージの接頭語 */
	private static final String PACKEGE = "jp.co.ha.tool.build.";

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
				Class<? extends BaseBuilder> builderClass = (Class<BaseBuilder>) Class.forName(PACKEGE + name);
				for (Method m : builderClass.getDeclaredMethods()) {

					if (!m.isAnnotationPresent(Build.class)) {
						continue;
					}

					BaseBuilder builder = builderClass.getDeclaredConstructor().newInstance();
					Object o = m.invoke(builder);
					if (o instanceof FileConfig) {
						FileConfig fileConfig = (FileConfig) m.invoke(builder);
						// 自動生成ファイルを生成
						FileFactory.create(fileConfig);
					} else if (o instanceof List) {
						List<FileConfig> confList = (List<FileConfig>) m.invoke(builder);
						// 自動生成ファイルを生成
						confList.stream().forEach(FileFactory::create);
					} else {
						LOG.error("レスポンスが実装されていない");
					}

				}
			}
		} catch (Exception e) {
			LOG.error("SQLの生成処理が失敗しました", e);
		}
	}
}
