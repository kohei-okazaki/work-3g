package jp.co.ha.business.healthInfo.type;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import jp.co.ha.common.type.BaseEnum;

/**
 * 性別の列挙
 *
 * @version 1.0.0
 */
public enum GenderType implements BaseEnum, JsonSerializable {

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
     * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
     * @param value
     *     値
     * @return GenderType
     */
    public static GenderType of(String value) {
        return BaseEnum.of(GenderType.class, value);
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeString(getValue());
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers,
            TypeSerializer typeSer) throws IOException {
        gen.writeString(getValue());
    }

}
