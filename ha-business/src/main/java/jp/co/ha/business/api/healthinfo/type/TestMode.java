package jp.co.ha.business.api.healthinfo.type;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import jp.co.ha.common.type.BaseEnum;

/**
 * APIのテストモード種別の列挙
 *
 * @version 1.0.0
 */
public enum TestMode implements BaseEnum {

    /** テストモード(DB登録を実施しない */
    TEST("1"),
    /** DB登録を行うモード */
    DB_REGIST("0");

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

    /**
     * {@inheritDoc}
     */
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
    public static class TestModeDeserializer extends JsonDeserializer<TestMode> {

        /**
         * {@inheritDoc}
         */
        @Override
        public TestMode deserialize(JsonParser parser, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            return TestMode.of(parser.getValueAsString("testMode"));
        }

    }
}
