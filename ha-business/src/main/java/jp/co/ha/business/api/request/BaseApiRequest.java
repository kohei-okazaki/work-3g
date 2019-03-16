package jp.co.ha.business.api.request;

import jp.co.ha.business.api.type.RequestType;
import jp.co.ha.common.api.request.BaseRequest;
import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.common.type.RegixType;
import jp.co.ha.common.validator.annotation.Length;
import jp.co.ha.common.validator.annotation.Max;
import jp.co.ha.common.validator.annotation.Min;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;

/**
 * API共通リクエスト情報保持クラス
 *
 */
public abstract class BaseApiRequest extends BaseRequest {

	/** リクエスト種別 */
	@Required(message = "requestTypeが未設定です")
	@Pattern(regixPattern = RegixType.HALF_NUMBER, message = "requestTypeが半角数字でありません")
	@Length(length = 64, message = "requestTypeが2byteではありません")
	private RequestType requestType;
	/** ユーザID */
	@Required(message = "userIdが未設定です")
	@Pattern(regixPattern = RegixType.HALF_CHAR, message = "userIdが半角英数でありません")
	@Min(size = 2, message = "userIdが2桁未満です")
	@Max(size = 16, message = "userIdが16桁以上です")
	private String userId;
	/** APIキー */
	@Mask
	@Required(message = "apiKeyが未設定です")
	@Length(length = 64, message = "apiKeyが64byteではありません")
	private String apiKey;

	/**
	 * requestTypeを返す
	 *
	 * @return requestType
	 */
	public RequestType getRequestType() {
		return requestType;
	}

	/**
	 * requestTypeを設定する
	 *
	 * @param requestType
	 *     リクエスト種別
	 */
	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	/**
	 * userIdを返す
	 *
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * userIdを設定する
	 *
	 * @param userId
	 *     ユーザID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * apiKeyを返す
	 *
	 * @return apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * apiKeyを設定する
	 *
	 * @param apiKey
	 *     APIキー
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

}
