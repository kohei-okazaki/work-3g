package jp.co.ha.common.io.file.json.reader;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

/**
 * JSONの読み取りクラス
 *
 * @version 1.0.0
 */
public class JsonReader {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(JsonReader.class);

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
     * @throws BaseException
     *     基底例外
     */
    public <T> T read(String path, String fileName, Class<T> clazz) throws BaseException {
        return read(new File(path, fileName), clazz);
    }

    /**
     * 指定されたJSONからJSONオブジェクトに変換する
     *
     * @param json
     *     JSONファイル
     * @param clazz
     *     Beanクラス型
     * @return Javaインスタンス
     * @throws BaseException
     *     基底例外
     */
    public <T> T read(File json, Class<T> clazz) throws BaseException {
        try {
            return new ObjectMapper().readValue(json, clazz);
        } catch (JsonParseException e) {
            LOG.error(json.getName() + "をjavaクラスへのParseに失敗しました", e);
        } catch (JsonMappingException e) {
            LOG.error(json.getName() + "をjavaクラスへのMappingに失敗しました", e);
        } catch (IOException e) {
            throw new SystemException(CommonErrorCode.RUNTIME_ERROR,
                    json.getName() + "をjavaクラスへの変換に失敗しました", e);
        }
        return null;
    }

    /**
     * 指定されたJavaBeanをJSON文字列に変換する
     *
     * @param bean
     *     Bean
     * @return JSON文字列
     * @throws BaseException
     *     基底例外
     */
    public String read(Object bean) throws BaseException {
        try {
            return new ObjectMapper().writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            throw new SystemException(CommonErrorCode.JSON_MAPPING_ERROR,
                    bean + "をJSON文字列への変換に失敗しました", e);
        }
    }
}
