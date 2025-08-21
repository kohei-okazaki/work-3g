package jp.co.ha.common.system;

import org.springframework.stereotype.Component;

import jp.co.ha.common.type.BaseEnum;

/**
 * system.propertiesのBean
 *
 * @version 1.0.0
 */
@Component
public class SystemProperties {

    /** ページング数 */
    private String paging;
    /** 環境名 */
    private Environment environment;
    /** システムメールアドレス */
    private String systemMailAddress;

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

    /**
     * ページング数を返す
     *
     * @return paging
     */
    public String getPaging() {
        return paging;
    }

    /**
     * ページング数を設定する
     *
     * @param paging
     *     ページング数
     */
    public void setPaging(String paging) {
        this.paging = paging;
    }

    /**
     * 環境を返す
     *
     * @return environment
     */
    public Environment getEnvironment() {
        return environment;
    }

    /**
     * 環境を設定する
     *
     * @param environment
     *     環境
     */
    public void setEnvironment(String environment) {
        this.environment = Environment.of(environment);
    }

    /**
     * システムメールアドレスを返す
     * 
     * @return systemMailAddress
     */
    public String getSystemMailAddress() {
        return systemMailAddress;
    }

    /**
     * システムメールアドレスを設定する
     * 
     * @param systemMailAddress
     *     システムメールアドレス
     */
    public void setSystemMailAddress(String systemMailAddress) {
        this.systemMailAddress = systemMailAddress;
    }

}
