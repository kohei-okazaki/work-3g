package jp.co.ha.common.io.file.property.reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.io.file.property.annotation.Property;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.BeanUtil.AccessorType;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.common.util.FileUtil.FileSeparator;

/**
 * Propertyの読み取りクラス
 * 
 * @since 1.0
 */
public class PropertyReader {

	/** LOG */
	private static final Logger LOG = LoggerFactory.getLogger(PropertyReader.class);

	/**
	 * 指定したパスのプロパティファイルの読み込みを行う
	 *
	 * @param path
	 *     ファイルパス
	 * @param fileName
	 *     ファイル名
	 * @return Properties
	 */
	public Properties read(String path, String fileName) {

		LOG.debug(fileName + "の読込 開始");

		Properties prop = new Properties();
		try (InputStream is = new FileInputStream(path + FileSeparator.SYSTEM.getValue() + fileName)) {
			prop.load(is);
		} catch (FileNotFoundException e) {
			LOG.error("プロパティファイル読込エラー ファイル名=" + fileName, e);
		} catch (IOException e) {
			LOG.error("読込エラー", e);
		} finally {
			LOG.debug(fileName + "の読込 終了");
		}
		return prop;
	}

	/**
	 * 指定したパスのプロパティを読み取り、対応するBeanを返す
	 *
	 * @param path
	 *     ファイルパス
	 * @param fileName
	 *     ファイル名
	 * @param clazz
	 *     bean
	 * @return T
	 * @throws BaseException
	 *     基底例外
	 */
	public <T> T read(String path, String fileName, Class<T> clazz) throws BaseException {
		Properties prop = read(path, fileName);
		try {
			T t = clazz.getDeclaredConstructor().newInstance();

			for (String name : getPropNameList(clazz)) {
				Method setter = BeanUtil.getAccessor(name, clazz, AccessorType.SETTER);
				Object value = prop.get(name);
				setter.invoke(t, value);
			}
			return t;
		} catch (Exception e) {
			throw new SystemException(CommonErrorCode.UNEXPECTED_ERROR, "", e);
		}
	}

	/**
	 * 指定したクラス型のフィールドに付与されている<code>@Propery</code>のnameをリストにして返す
	 *
	 * @param clazz
	 *     対象クラス
	 * @return nameリスト
	 */
	private static List<String> getPropNameList(Class<?> clazz) {
		return CollectionUtil.toList(clazz.getDeclaredFields()).stream()
				.filter(e -> e.isAnnotationPresent(Property.class))
				.map(e -> e.getAnnotation(Property.class).name()).collect(Collectors.toList());
	}

}
