package jp.co.ha.business.healthInfo.type;

import jp.co.ha.common.type.BaseEnum;

/**
 * 健康情報ステータスの列挙<br>
 * <ul>
 * <li>10:減少</li>
 * <li>20:変化なし</li>
 * <li>30:増加</li>
 * </ul>
 *
 * @version 1.0.0
 */
public enum HealthInfoStatus implements BaseEnum {

    /** 10:減少 */
    DOWN("10"),
    /** 20:変化なし */
    EVEN("20"),
    /** 30:増加 */
    INCREASE("30");

    /** 値 */
    private String value;

    /**
     * コンストラクタ
     *
     * @param value
     *     値
     */
    private HealthInfoStatus(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    /**
     * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
     * @param value
     *     値
     * @return HealthInfoStatus
     */
    public static HealthInfoStatus of(String value) {
        return BaseEnum.of(HealthInfoStatus.class, value);
    }

}
