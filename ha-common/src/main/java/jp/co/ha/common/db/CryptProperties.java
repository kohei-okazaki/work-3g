package jp.co.ha.common.db;

import org.springframework.stereotype.Component;

/**
 * crypt.propertiesのBean
 *
 * @version 1.0.0
 */
@Component
public class CryptProperties {

    /** Mode */
    private String mode;
    /** Key */
    private String key;
    /** Shift数 */
    private int shift;

    /**
     * modeを返す
     *
     * @return mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * modeを設定する
     *
     * @param mode
     *     Mode
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * keyを返す
     *
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * keyを設定する
     *
     * @param key
     *     Key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * shiftを返す
     *
     * @return shift
     */
    public int getShift() {
        return shift;
    }

    /**
     * shiftを設定する
     *
     * @param shift
     *     Shift数
     */
    public void setShift(String shift) {
        this.shift = Integer.parseInt(shift);
    }

}
