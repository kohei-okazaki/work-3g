package jp.co.ha.common.io.file.json.reader;

import static jp.co.ha.common.exception.CommonErrorCode.*;

import java.io.File;
import java.io.InputStream;

import jp.co.ha.common.exception.SystemRuntimeException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import tools.jackson.core.JacksonException;
import tools.jackson.core.exc.StreamReadException;
import tools.jackson.databind.DatabindException;
import tools.jackson.databind.json.JsonMapper;

/**
 * JSONの読み取りクラス
 *
 * @version 1.0.0
 */
public class JsonReader {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(JsonReader.class);

    /** JSON Mapper */
    private static final JsonMapper JSON_MAPPER = JsonMapper.builder().build();

    /**
     * 指定されたファイルパスからJavaオブジェクトに変換する
     *
     * @param path
     *     ファイルパス
     * @param fileName
     *     ファイル名
     * @param clazz
     *     Beanクラス型
     * @return Javaインスタンス
     */
    public <T> T read(String path, String fileName, Class<T> clazz) {
        return read(new File(path, fileName), clazz);
    }

    /**
     * 指定されたJSONからJSONオブジェクトに変換する
     *
     * @param <T>
     *     Beanクラスタイプ
     * @param json
     *     JSONファイル
     * @param clazz
     *     Beanクラス型
     * @return Javaインスタンス
     */
    public <T> T read(File json, Class<T> clazz) {
        try {
            return JSON_MAPPER.readValue(json, clazz);
        } catch (StreamReadException e) {
            LOG.error(json.getName() + "をjavaクラスへのParseに失敗しました", e);
        } catch (DatabindException e) {
            LOG.error(json.getName() + "をjavaクラスへのMappingに失敗しました", e);
        }
        return null;
    }

    /**
     * 指定された入力StreamからJSONオブジェクトに変換する
     *
     * @param <T>
     *     Beanクラスタイプ
     * @param is
     *     入力Stream
     * @param clazz
     *     Beanクラス型
     * @return Javaインスタンス
     */
    public <T> T read(InputStream is, Class<T> clazz) {
        try {
            return JSON_MAPPER.readValue(is, clazz);
        } catch (StreamReadException e) {
            LOG.error("javaクラスへのParseに失敗しました", e);
        } catch (DatabindException e) {
            LOG.error("javaクラスへのMappingに失敗しました", e);
        }
        return null;
    }

    /**
     * 指定されたJavaBeanをJSON文字列に変換する
     *
     * @param bean
     *     Bean
     * @return JSON文字列
     */
    public String read(Object bean) {
        try {
            return JSON_MAPPER.writeValueAsString(bean);
        } catch (JacksonException e) {
            throw new SystemRuntimeException(
                    JSON_MAPPING_ERROR,
                    bean + "をJSON文字列への変換に失敗しました",
                    e);
        }
    }
}