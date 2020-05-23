package jp.co.ha.common.db;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

import jp.co.ha.common.function.Builder;
import jp.co.ha.common.util.StringUtil;

/**
 * 検索オプション<br>
 * 検索における共通事項をまとめて保持するクラス
 *
 * @version 1.0.0
 */
public class SelectOption {

    /** ソートマップ */
    private final Map<String, SortType> ORDER_BY_MAP;
    /** 検索上限数 */
    private final int LIMIT;

    /**
     * 検索オプションのビルダー
     *
     * @version 1.0.0
     */
    public static class SelectOptionBuilder implements Builder<SelectOption> {

        /* 任意項目 */
        /** ソートマップ */
        private Map<String, SortType> orderByMap = new LinkedHashMap<>();
        /** 検索上限数 */
        private int limit = 10000;

        /**
         * {@inheritDoc}
         */
        @Override
        public SelectOption build() {
            return new SelectOption(this);
        }

        /**
         * 指定したカラムでのソートを設定
         *
         * @param column
         *     カラム
         * @param sortType
         *     昇順/降順の列挙
         * @return SelectOptionBuilder
         */
        public SelectOptionBuilder orderBy(String column, SortType sortType) {
            orderByMap.put(column, sortType);
            return this;
        }

        /**
         * limitを設定
         *
         * @param limit
         *     検索上限数
         * @return SelectOptionBuilder
         */
        public SelectOptionBuilder limit(int limit) {
            this.limit = limit;
            return this;
        }

    }

    /**
     * DB検索のソートの列挙体
     *
     * @version 1.0.0
     */
    public static enum SortType {

        /** 昇順 */
        ASC,
        /** 降順 */
        DESC;

    }

    /**
     * コンストラクタ<br>
     * ビルダーからのみインスタンスの生成を行うためprivateにする
     *
     * @param builder
     *     SelectOptionのビルダー
     */
    private SelectOption(SelectOptionBuilder builder) {
        this.ORDER_BY_MAP = builder.orderByMap;
        this.LIMIT = builder.limit;
    }

    /**
     * ORDER BY句を文字列表現で返す
     *
     * @return ORDER BY句の文字列表現
     */
    public String getOrderBy() {
        StringJoiner sj = new StringJoiner(StringUtil.COMMA);
        this.ORDER_BY_MAP.entrySet()
                .stream()
                .map(e -> e.getKey() + StringUtil.SPACE + e.getValue())
                .forEach(e -> sj.add(e));
        return sj.toString();
    }

    /**
     * limitを返す
     *
     * @return 検索上限数
     */
    public int getLimit() {
        return LIMIT;
    }

}
