package jp.co.ha.business.api.request;

import jp.co.ha.business.api.type.RequestType;
import jp.co.ha.business.api.type.TestModeType;
import jp.co.ha.common.api.request.BaseRequest;
import jp.co.ha.common.log.annotation.Mask;

/**
 * API共通リクエスト情報保持クラス<br>
 *
 */
public abstract class BaseApiRequest extends BaseRequest {

	/** リクエスト種別 */
	private RequestType requestType;
	/** ユーザID */
	private String userId;
	/** APIキー */
	@Mask
	private String apiKey;
	/** testモード区分 */
	private TestModeType testModeType;

	/**
	 * requestTypeを返す<br>
	 *
	 * @return requestType リクエスト種別
	 */
	public RequestType getRequestType() {
		return requestType;
	}

	/**
	 * requestTypeを設定する<br>
	 *
	 * @param requestType
	 *     リクエスト種別
	 */
	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	/**
	 * userIdを返す<br>
	 *
	 * @return userId ユーザID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * userIdを設定する<br>
	 *
	 * @param userId
	 *     ユーザID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * apiKeyを返す<br>
	 *
	 * @return apiKey APIキー
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * apiKeyを設定する<br>
	 *
	 * @param apiKey
	 *     APIキー
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 * testModeTypeを返す
	 *
	 * @return testModeType testモード区分
	 */
	public TestModeType getTestModeType() {
		return testModeType;
	}

	/**
	 * testModeTypeを設定する
	 *
	 * @param testModeType
	 *     testモード区分
	 */
	public void setTestModeType(TestModeType testModeType) {
		this.testModeType = testModeType;
	}

}
