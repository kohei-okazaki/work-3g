package jp.co.ha.root.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import jp.co.ha.common.exception.SystemRuntimeException;

/**
 * JSON基底データクラス
 *
 * @version 1.0.0
 */
public abstract class JsonEntity {

    @Override
    public String toString() {

        try {
            ObjectMapper om = new ObjectMapper();
            om.enable(SerializationFeature.INDENT_OUTPUT);
            return om.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new SystemRuntimeException(e);
        }
    }
}
