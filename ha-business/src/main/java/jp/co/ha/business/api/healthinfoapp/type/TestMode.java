package jp.co.ha.business.api.healthinfoapp.type;

import jp.co.ha.common.type.BaseEnum;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.ValueSerializer;

/**
 * APIのテストモード種別の列挙
 *
 * @version 1.0.0
 */
public enum TestMode implements BaseEnum {

    /** 0:DB登録を行うモード */
    DB_REGIST("0"),
    /** 1:テストモード(DB登録を実施しない */
    TEST("1");

    /** 値 */
    private String value;

    /**
     * コンストラクタ
     *
     * @param value
     *     値
     */
    private TestMode(String value) {
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
     * @return TestMode
     */
    public static TestMode of(String value) {
        return BaseEnum.of(TestMode.class, value);
    }

    /**
     * JSONのテストモード種別のデシリアライズクラス<br>
     * 文字列型のJSONをJavaのクラスに変換する
     *
     * @version 1.0.0
     */
    public static class TestModeDeserializer extends ValueDeserializer<TestMode> {

        @Override
        public TestMode deserialize(JsonParser parser, DeserializationContext ctxt)
                throws JacksonException {
            return TestMode.of(parser.getValueAsString());
        }
    }

    /**
     * JSONのテストモード種別のシリアライズクラス<br>
     * Javaのクラスを文字列型のJSONに変換する
     * 
     * @version 1.0.0
     */
    public static class TestModeSerializer extends ValueSerializer<TestMode> {

        @Override
        public void serialize(TestMode testMode, JsonGenerator gen,
                SerializationContext ctxt) throws JacksonException {
            gen.writeString(testMode.getValue());
        }
    }
}
