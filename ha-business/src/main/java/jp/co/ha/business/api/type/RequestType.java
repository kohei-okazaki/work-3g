package jp.co.ha.business.api.type;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * APIのリクエスト種別の列挙
 *
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum RequestType {

	/** 健康情報登録 */
	HEALTH_INFO_REGIST("001", "健康情報登録"),
	/** 健康情報照会 */
	HEALTH_INFO_REFERENCE("002", "健康情報照会");

	/** リクエストID */
	private String requestId;
	/** 名前 */
	private String name;

	/**
	 * コンストラクタ
	 *
	 * @param requestId
	 *     リクエストID
	 * @param name
	 *     名前
	 */
	private RequestType(String requestId, String name) {
		this.requestId = requestId;
		this.name = name;
	}

	/**
	 * 指定されたリクエストIDに対応するリクエストタイプを返す
	 *
	 * @param requestId
	 *     リクエストID
	 * @return RequestType
	 */
	@JsonCreator
	public static RequestType of(String requestId) {
		return Stream.of(RequestType.class.getEnumConstants())
				.filter(e -> e.getRequestId().equals(requestId))
				.findFirst()
				.orElse(null);
	}

	/**
	 * 指定したリクエストタイプが自身と一致するかどうか判定する
	 *
	 * @param requestType
	 *     リクエストタイプ
	 * @return 一致する場合true, それ以外の場合false
	 */
	public boolean is(RequestType requestType) {
		return this == requestType;
	}

	/**
	 * requestIdを返す
	 *
	 * @return requestId
	 */
	public String getRequestId() {
		return this.requestId;
	}

	/**
	 * nameを返す
	 *
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

}
