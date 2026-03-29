package jp.co.ha.common.web.form;

import jp.co.ha.common.exception.SystemRuntimeException;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

/**
 * JSON基底データクラス
 *
 * @version 1.0.0
 */
public abstract class JsonEntity {

    @Override
    public String toString() {

        try {
            return JsonMapper.builder()
                    .enable(SerializationFeature.INDENT_OUTPUT)
                    .build().writeValueAsString(this);
        } catch (Exception e) {
            throw new SystemRuntimeException(e);
        }
    }
}
