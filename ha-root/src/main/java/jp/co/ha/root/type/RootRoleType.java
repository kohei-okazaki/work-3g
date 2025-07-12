package jp.co.ha.root.type;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jp.co.ha.common.type.BaseEnum;
import jp.co.ha.root.type.RootRoleType.RootRoleTypeDeserializer;

/**
 * ユーザ権限の列挙体
 * <ul>
 * <li>00:管理者権限</li>
 * <li>01:照会権限</li>
 * <li>02:作成権限</li>
 * </ul>
 *
 * @version 1.0.0
 */
@JsonDeserialize(using = RootRoleTypeDeserializer.class)
public enum RootRoleType implements BaseEnum {

    /** 管理者権限 */
    ADMIN("00"),
    /** 照会権限 */
    REF("01"),
    /** 作成権限 */
    ENTRY("02");

    /** 値 */
    private String value;

    /**
     * コンストラクタ
     *
     * @param value
     *     値
     */
    private RootRoleType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    /**
     * ユーザ権限の列挙体を返す
     *
     * @param value
     *     値
     * @return ユーザ権限の列挙体
     */
    public static RootRoleType of(String value) {
        return BaseEnum.of(RootRoleType.class, value);
    }

    /**
     * JSONのテストモード種別のデシリアライズクラス<br>
     * 文字列型のJSONをJavaのクラスに変換する
     *
     * @version 1.0.0
     */
    public static class RootRoleTypeDeserializer extends JsonDeserializer<RootRoleType> {

        @Override
        public RootRoleType deserialize(JsonParser parser, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            return RootRoleType.of(parser.getValueAsString());
        }

    }

}
