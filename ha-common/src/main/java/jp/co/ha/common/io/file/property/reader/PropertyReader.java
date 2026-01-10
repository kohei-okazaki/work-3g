package jp.co.ha.common.io.file.property.reader;

import static jp.co.ha.common.exception.CommonErrorCode.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.io.file.property.annotation.Property;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.BeanUtil.AccessorType;
import jp.co.ha.common.util.FileUtil.FileSeparator;

/**
 * Propertyの読み取りクラス
 *
 * @version 1.0.0
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
        try (InputStream is = new FileInputStream(
                path + FileSeparator.SYSTEM.getValue() + fileName)) {
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

            for (Field f : BeanUtil.getFieldList(clazz)) {
                if (!f.isAnnotationPresent(Property.class)) {
                    // @Propertyが付与されていないフィールドの場合、次のフィールドへ
                    continue;
                }

                // プロパティファイル.プロパティ名から値を取得
                Object value = prop.get(f.getAnnotation(Property.class).name());
                BeanUtil.getAccessor(f.getName(), clazz, AccessorType.SETTER)
                        .invoke(t, value);
            }
            return t;
        } catch (Exception e) {
            throw new SystemException(UNEXPECTED_ERROR, "", e);
        }
    }

}
