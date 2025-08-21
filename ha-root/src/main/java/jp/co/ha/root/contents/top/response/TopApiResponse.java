package jp.co.ha.root.contents.top.response;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import jp.co.ha.common.web.form.BaseApiResponse;
import jp.co.ha.common.web.form.JsonEntity;
import jp.co.ha.root.base.BaseRootApiResponse;

/**
 * Top情報取得APIレスポンスクラス
 *
 * @version 1.0.0
 */
public class TopApiResponse extends BaseRootApiResponse implements BaseApiResponse {

    /** 健康情報登録グラフ情報リスト */
    @JsonProperty("health_info_reg_graph_list")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<RegGraph> healthInfoRegGraphList;
    /** ユーザ登録グラフ情報リスト */
    @JsonProperty("account_reg_graph_list")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<RegGraph> accountRegGraphList;

    /**
     * 健康情報登録グラフ情報リストを返す
     *
     * @return healthInfoRegGraphList
     */
    public List<RegGraph> getHealthInfoRegGraphList() {
        return healthInfoRegGraphList;
    }

    /**
     * 健康情報登録グラフ情報リストを設定する
     *
     * @param healthInfoRegGraphList
     *     健康情報登録グラフ情報リスト
     */
    public void setHealthInfoRegGraphList(List<RegGraph> healthInfoRegGraphList) {
        this.healthInfoRegGraphList = healthInfoRegGraphList;
    }

    /**
     * ユーザ登録グラフ情報リストを返す
     *
     * @return accountRegGraphList
     */
    public List<RegGraph> getAccountRegGraphList() {
        return accountRegGraphList;
    }

    /**
     * ユーザ登録グラフ情報リストを設定する
     *
     * @param accountRegGraphList
     *     ユーザ登録グラフ情報リスト
     */
    public void setAccountRegGraphList(List<RegGraph> accountRegGraphList) {
        this.accountRegGraphList = accountRegGraphList;
    }

    /**
     * 登録グラフ情報
     *
     * @version 1.0.0
     */
    public static class RegGraph extends JsonEntity {

        /** 登録件数 */
        @JsonProperty("count")
        private Integer count;
        /** 登録日 */
        @JsonProperty("date")
        @JsonSerialize(using = LocalDateSerializer.class)
        @JsonFormat(pattern = "yyyy/MM/dd", timezone = "Asia/Tokyo")
        private LocalDate date;

        /**
         * 登録件数を返す
         *
         * @return count
         */
        public Integer getCount() {
            return count;
        }

        /**
         * 登録件数を設定する
         *
         * @param count
         *     登録件数
         */
        /**
         * @param count
         */
        public void setCount(Integer count) {
            this.count = count;
        }

        /**
         * 登録日を返す
         *
         * @return date
         */
        public LocalDate getDate() {
            return date;
        }

        /**
         * 登録日を設定する
         *
         * @param date
         *     登録日
         */
        public void setDate(LocalDate date) {
            this.date = date;
        }

    }
}
