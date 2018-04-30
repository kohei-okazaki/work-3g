package jp.co.ha.common.api;

/**
 * リクエストタイプ列挙
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
	 * @param requestId
	 * @param name
	 */
	private RequestType(String requestId, String name) {
		setRequestId(requestId);
		setName(name);
	}

	/**
	 * 指定されたリクエストIDに対応するリクエストタイプを返す<br>
	 * @param requestId
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
	 * @return requestId
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * requestIdを設定する<br>
	 * @param requestId
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	/**
	 * nameを返す<br>
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * nameを設定する<br>
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

}
