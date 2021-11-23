package jp.co.ha.root.type;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import jp.co.ha.common.type.BaseEnum;

/**
 * RootAPI処理結果列挙体
 * <ul>
 * <li>0:正常終了</li>
 * <li>1:異常終了</li>
 * </ul>
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
     * RootAPI処理結果の列挙体を返す
     *
     * @param value
     *     値
     * @return RootAPI処理結果の列挙体
     */
    public static RootApiResult of(String value) {
        return BaseEnum.of(RootApiResult.class, value);
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
