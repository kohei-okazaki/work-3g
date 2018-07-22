package jp.co.ha.common.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.ha.common.exception.AppIOException;
import jp.co.ha.common.exception.BaseAppException;
import jp.co.ha.common.exception.ErrorCode;

/**
 * Json操作のUtilクラス<br>
 *
 */
public class JsonUtil {

	/**
	 * プライベートコンストラクタ<br>
	 */
	private JsonUtil() {
	}

	/**
	 * Json文字列からjavaオブジェクトに変換する<br>
	 *
	 * @param target
	 *     対象文字列
	 * @param clazz
	 *     Beanクラス型
	 * @return
	 * @throws BaseAppException
	 *     アプリ例外
	 */
	public static <T> T toJavaObject(String target, Class<T> clazz) throws BaseAppException {

		ObjectMapper mapper = new ObjectMapper();
		T t = null;
		try {
			t = mapper.readValue(target, clazz);
		} catch (JsonParseException e) {
			throw new AppIOException(ErrorCode.JSON_FORMAT_ERROR, target + "をjavaクラスへの変換に失敗しました");
		} catch (JsonMappingException e) {
			throw new AppIOException(ErrorCode.JSON_MAPPING_ERROR, target + "をjavaクラスへの変換に失敗しました");
		} catch (IOException e) {
			throw new AppIOException(ErrorCode.RUNTIME_ERROR, target + "をjavaクラスへの変換に失敗しました");
		}
		return t;
	}

	/**
	 * javaオブジェクトからJson文字列に変換する<br>
	 *
	 * @param target
	 *     対象文字列
	 * @return
	 */
	public static String toJsonString(Object target) {

		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(target);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;

	}

}
