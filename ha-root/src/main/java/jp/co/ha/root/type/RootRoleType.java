package jp.co.ha.root.type;

import jp.co.ha.common.type.BaseEnum;
import jp.co.ha.root.type.RootRoleType.RootRoleTypeDeserializer;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.annotation.JsonDeserialize;

/**
 * 管理者サイトユーザ権限の列挙体
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
     * 管理者サイトユーザ権限の列挙体を返す
     *
     * @param value
     *     値
     * @return 管理者サイトユーザ権限の列挙体
     */
    public static RootRoleType of(String value) {
        return BaseEnum.of(RootRoleType.class, value);
    }

    /**
     * JSONの管理者サイトユーザ権限のデシリアライズクラス<br>
     * 文字列型のJSONをJavaのクラスに変換する
     *
     * @version 1.0.0
     */
    public static class RootRoleTypeDeserializer extends ValueDeserializer<RootRoleType> {

        @Override
        public RootRoleType deserialize(JsonParser parser, DeserializationContext ctxt)
                throws JacksonException {
            return RootRoleType.of(parser.getValueAsString());
        }

    }

}
