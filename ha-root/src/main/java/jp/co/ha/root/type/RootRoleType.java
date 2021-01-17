package jp.co.ha.root.type;

import jp.co.ha.common.type.BaseEnum;

/**
 * ユーザ権限の列挙体
 *
 * @version 1.0.0
 */
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

}
