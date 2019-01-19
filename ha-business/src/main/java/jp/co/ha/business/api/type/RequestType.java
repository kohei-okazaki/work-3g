package jp.co.ha.business.api.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import jp.co.ha.common.type.BaseEnum;

/**
 * APIのリクエスト種別の列挙
 *
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum RequestType implements BaseEnum {

	/** 健康情報登録 */
	HEALTH_INFO_REGIST("001", "健康情報登録"),
	/** 健康情報照会 */
	HEALTH_INFO_REFERENCE("002", "健康情報照会");

	/** 値*/
	private String value;
	/** 名前 */
	private String name;

	/**
	 * コンストラクタ
	 *
	 * @param value
	 *     値
	 * @param name
	 *     名前
	 */
	private RequestType(String value, String name) {
		this.value = value;
		this.name = name;
	}

	/**
	 * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
	 * @param value
	 *     値
	 * @return RequestType
	 */
	@JsonCreator
	public static RequestType of(String value) {
		return BaseEnum.of(RequestType.class, value);
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
	 * {@inheritDoc}
	 */
	@Override
	public String getValue() {
		return this.value;
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
