package jp.co.ha.business.component;

import org.springframework.stereotype.Component;

import jp.co.ha.common.type.BaseEnum;

/**
 * 問い合わせ関連のComponent
 * 
 * @version 1.0.0
 */
@Component
public class InquiryComponent {

    /**
     * 問い合わせステータスの列挙体
     * 
     * @version 1.0.0
     */
    public static enum Status implements BaseEnum {

        /** 00：未対応 */
        NOT_STARTED("00"),
        /** 01：対応中 */
        IN_PROGRESS("01"),
        /** 02：完了 */
        COMPLETED("02"),;

        /**
         * コンストラクタ
         * 
         * @param value
         *     値
         */
        private Status(String value) {
            this.value = value;
        }

        /** 値 */
        private String value;

        @Override
        public String getValue() {
            return this.value;
        }

        /**
         * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
         * @param value
         *     値
         * @return Status
         */
        public static Status of(String value) {
            return BaseEnum.of(Status.class, value);
        }
    }

    /**
     * 問い合わせ種別の列挙体
     * 
     * @version 1.0.0
     */
    public static enum Type implements BaseEnum {

        /** 00：未対応 */
        NOT_STARTED("00"),
        /** 01：対応中 */
        IN_PROGRESS("01"),
        /** 02：完了 */
        COMPLETED("02"),;

        /**
         * コンストラクタ
         * 
         * @param value
         *     値
         */
        private Type(String value) {
            this.value = value;
        }

        /** 値 */
        private String value;

        @Override
        public String getValue() {
            return this.value;
        }

        /**
         * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
         * @param value
         *     値
         * @return Type
         */
        public static Type of(String value) {
            return BaseEnum.of(Type.class, value);
        }

    }
}
