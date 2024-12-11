package jp.co.ha.business.api.track.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jp.co.ha.common.type.BaseEnum;

/**
 * Track APIの基底レスポンス情報クラス
 *
 * @version 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseTrackApiResponse {

    /** 処理結果 */
    private TrackApiResult result;

    /**
     * resultを返す
     * 
     * @return result
     */
    public TrackApiResult getResult() {
        return result;
    }

    /**
     * resultを設定する
     * 
     * @param result
     */
    public void setResult(TrackApiResult result) {
        this.result = result;
    }

    /**
     * 処理結果のEnum
     * 
     * @version 1.0.0
     */
    public static enum TrackApiResult implements BaseEnum {

        /** 成功 */
        SUCCESS("0"),
        /** 失敗 */
        FAILURE("1");

        /** 値 */
        private String value;

        /**
         * コンストラクタ
         *
         * @param value
         *     値
         */
        private TrackApiResult(String value) {
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
         * @return Result
         */
        public static TrackApiResult of(String value) {
            return BaseEnum.of(TrackApiResult.class, value);
        }

    }

}
