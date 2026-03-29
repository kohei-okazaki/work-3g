package jp.co.ha.business.healthInfo.type;

import jp.co.ha.common.type.BaseEnum;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

/**
 * 性別の列挙
 * <ul>
 * <li>0:男性</li>
 * <li>1:女性</li>
 * </ul>
 *
 * @version 1.0.0
 */
public enum GenderType implements BaseEnum {

    /** 男性 */
    MALE("0"),
    /** 女性 */
    FEMALE("1");

    /** 値 */
    private String value;

    /**
     * コンストラクタ
     *
     * @param value
     *     値
     */
    private GenderType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    /**
     * 数値型の性別タイプを返す
     * 
     * @return 数値型の性別タイプ
     */
    public Integer getIntValue() {
        return Integer.valueOf(this.value);
    }

    /**
     * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
     * @param value
     *     値
     * @return GenderType
     */
    public static GenderType of(String value) {
        return BaseEnum.of(GenderType.class, value);
    }

    /**
     * @see #of(String)
     * @param value
     *     値
     * @return GenderType
     */
    public static GenderType of(int value) {
        return of(String.valueOf(value));
    }

    /**
     * 性別の列挙のシリアライズクラス<br>
     * Javaのクラスを文字列型のJSONに変換する
     * 
     * @version 1.0.0
     */
    public static class GenderTypeSerializer extends ValueSerializer<GenderType> {

        @Override
        public void serialize(GenderType genderType, JsonGenerator gen,
                SerializationContext ctxt) throws JacksonException {
            gen.writeString(genderType.getValue());
        }
    }

}
