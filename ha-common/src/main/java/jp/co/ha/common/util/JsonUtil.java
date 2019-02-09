package jp.co.ha.common.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.ha.common.exception.AppIOException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;

/**
 * Json操作のUtilクラス
 *
 */
public class JsonUtil {

	/**
	 * プライベートコンストラクタ
	 */
	private JsonUtil() {
	}

	/**
	 * Json文字列からjavaオブジェクトに変換する
	 *
	 * @param target
	 *     対象文字列
	 * @param clazz
	 *     Beanクラス型
	 * @return Javaインスタンス
	 * @throws BaseException
	 *     基底例外
	 */
	public static <T> T toJavaObject(String target, Class<T> clazz) throws BaseException {

		try {
			return new ObjectMapper().readValue(target, clazz);
		} catch (JsonParseException e) {
			throw new AppIOException(CommonErrorCode.JSON_FORMAT_ERROR, target + "をjavaクラスへの変換に失敗しました");
		} catch (JsonMappingException e) {
			throw new AppIOException(CommonErrorCode.JSON_MAPPING_ERROR, target + "をjavaクラスへの変換に失敗しました");
		} catch (IOException e) {
			throw new AppIOException(CommonErrorCode.RUNTIME_ERROR, target + "をjavaクラスへの変換に失敗しました");
		}
	}

	/**
	 * javaオブジェクトからJson文字列に変換する
	 *
	 * @param target
	 *     対象文字列
	 * @return Json文字列
	 * @throws BaseException
	 *     基底例外
	 */
	public static String toJsonString(Object target) throws BaseException {

		try {
			return new ObjectMapper().writeValueAsString(target);
		} catch (JsonProcessingException e) {
			throw new AppIOException(CommonErrorCode.JSON_MAPPING_ERROR, target + "をJSON文字列への変換に失敗しました");
		}

	}

}
