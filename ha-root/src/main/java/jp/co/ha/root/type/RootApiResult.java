package jp.co.ha.root.type;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import jp.co.ha.common.type.BaseEnum;

/**
 * RootAPI処理結果列挙
 *
 * @version 1.0.0
 */
public enum RootApiResult implements BaseEnum {

    /** 正常終了 */
    SUCCESS("0"),
    /** 異常終了 */
    FAILURE("1");

    /** 値 */
    private String value;

    /**
     * コンストラクタ
     *
     * @param value
     *     値
     */
    private RootApiResult(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    /**
     * RootAPI処理結果のシリアライズクラス
     *
     * @version 1.0.0
     */
    public static class RootApiResultSerializer extends JsonSerializer<RootApiResult> {

        @Override
        public void serialize(RootApiResult rootApiResult, JsonGenerator gen,
                SerializerProvider serializers) throws IOException {
            gen.writeString(rootApiResult.getValue());
        }

    }

}
