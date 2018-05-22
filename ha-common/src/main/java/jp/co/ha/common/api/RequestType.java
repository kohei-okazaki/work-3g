package jp.co.ha.common.api;

/**
 * リクエストタイプ<br>
 * APIのリクエストの列挙<br>
 *
 */
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
	 * コンストラクタ<br>
	 *
	 * @param requestId
	 *            リクエストID
	 * @param name
	 *            名前
	 */
	private RequestType(String requestId, String name) {
		this.requestId = requestId;
		this.name = name;
	}

	/**
	 * 指定されたリクエストIDに対応するリクエストタイプを返す<br>
	 *
	 * @param requestId
	 *            リクエストID
	 * @return
	 */
	public static RequestType of(String requestId) {
		for (RequestType type : RequestType.class.getEnumConstants()) {
			if (type.getRequestId().equals(requestId)) {
				return type;
			}
		}
		return null;
	}

	/**
	 * requestIdを返す<br>
	 *
	 * @return requestId リクエストID
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * nameを返す<br>
	 *
	 * @return name 名前
	 */
	public String getName() {
		return name;
	}

}
