package jp.co.ha.common.system;

import org.springframework.stereotype.Component;

import jp.co.ha.common.type.BaseEnum;

/**
 * system.propertiesのBean
 *
 * @param paging
 *     ページング数
 * @param environment
 *     環境名
 * @version 1.0.0
 */
@Component
public record SystemProperties(
        String paging,
        Environment environment) {

    /**
     * 環境名の列挙
     *
     * @version 1.0.0
     */
    public static enum Environment implements BaseEnum {

        /** ローカル環境 */
        LOCAL("local"),
        /** dev1環境 */
        DEV1("dev1");

        /** 値 */
        private String value;

        /**
         * コンストラクタ
         *
         * @param value
         *     値
         */
        private Environment(String value) {
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
         * @return Environment
         */
        public static Environment of(String value) {
            return BaseEnum.of(Environment.class, value);
        }

    }

}
