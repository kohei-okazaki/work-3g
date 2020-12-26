package jp.co.ha.business.api.type;

import jp.co.ha.common.type.BaseEnum;

/**
 * API名の列挙体
 *
 * @version 1.0.0
 */
public enum ApiNameType implements BaseEnum {

    /** 健康情報登録API */
    HEALTH_INFO_REGIST("健康情報登録API"),
    /** 健康情報照会API */
    HEALTH_INFO_REFERENCE("健康情報照会API"),
    /** ヘルスチェックAPI */
    HEALTH_CHECK("ヘルスチェックAPI"),
    /** トークン発行API */
    TOKEN("トークン発行API"),
    /** 基礎健康情報計算API */
    BASIC("基礎健康情報計算API"),
    /** カロリー計算API */
    CALORIE("カロリー計算API"),
    /** 肺活量計算API */
    BREATHING_CAPACITY("肺活量計算API");

    /** 値 */
    private String value;

    /**
     * コンストラクタ
     *
     * @param value
     *     値
     */
    private ApiNameType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    /**
     * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
     * @param value
     *     値
     * @return ApiNameType
     */
    public static ApiNameType of(String value) {
        return BaseEnum.of(ApiNameType.class, value);
    }

}
