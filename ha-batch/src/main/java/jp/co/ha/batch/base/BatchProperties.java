package jp.co.ha.batch.base;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * バッチのプロパティファイルクラス<br>
 * <ul>
 * <li>application.yml</li>
 * <li>application-${env}.yml</li>
 * </ul>
 * 
 * @version 1.0.0
 */
@ConfigurationProperties(prefix = "ha-batch")
public class BatchProperties {

    /** 健康情報連携バッチプロパティクラス */
    private HealthInfoMigrate healthInfoMigrate;

    /** 月次健康情報集計バッチプロパティクラス */
    private MonthlyHealthInfoSummary monthlyHealthInfoSummary;

    /** データパージプロパティクラスリスト */
    private List<DataPurge> dataPurgeList;

    /** 日次健康情報データ分析連携バッチプロパティクラス */
    private DailyHealthInfoAnalysis dailyHealthInfoAnalysis;

    /** 日次ユーザ情報データ分析連携バッチプロパティクラス */
    private DailyUserAnalysis dailyUserAnalysis;

    /**
     * 健康情報連携バッチプロパティクラスを返す
     * 
     * @return healthInfoMigrate
     */
    public HealthInfoMigrate getHealthInfoMigrate() {
        return healthInfoMigrate;
    }

    /**
     * 健康情報連携バッチプロパティクラスを設定する
     * 
     * @param healthInfoMigrate
     *     健康情報連携バッチプロパティクラス
     */
    public void setHealthInfoMigrate(HealthInfoMigrate healthInfoMigrate) {
        this.healthInfoMigrate = healthInfoMigrate;
    }

    /**
     * 月次健康情報集計バッチプロパティクラスを返す
     * 
     * @return monthlyHealthInfoSummary
     */
    public MonthlyHealthInfoSummary getMonthlyHealthInfoSummary() {
        return monthlyHealthInfoSummary;
    }

    /**
     * 月次健康情報集計バッチプロパティクラスを設定する
     * 
     * @param monthlyHealthInfoSummary
     *     月次健康情報集計バッチプロパティクラス
     */
    public void setMonthlyHealthInfoSummary(
            MonthlyHealthInfoSummary monthlyHealthInfoSummary) {
        this.monthlyHealthInfoSummary = monthlyHealthInfoSummary;
    }

    /**
     * データパージプロパティクラスリストを返す
     * 
     * @return dataPurgeList
     */
    public List<DataPurge> getDataPurgeList() {
        return dataPurgeList;
    }

    /**
     * データパージプロパティクラスリストを設定する
     * 
     * @param dataPurgeList
     *     データパージプロパティクラスリスト
     */
    public void setDataPurgeList(List<DataPurge> dataPurgeList) {
        this.dataPurgeList = dataPurgeList;
    }

    /**
     * 日次健康情報データ分析連携バッチプロパティクラスを返す
     * 
     * @return dailyHealthInfoAnalysis
     */
    public DailyHealthInfoAnalysis getDailyHealthInfoAnalysis() {
        return dailyHealthInfoAnalysis;
    }

    /**
     * 日次健康情報データ分析連携バッチプロパティクラスを設定する
     * 
     * @param dailyHealthInfoAnalysis
     *     日次健康情報データ分析連携バッチプロパティクラス
     */
    public void setDailyHealthInfoAnalysis(
            DailyHealthInfoAnalysis dailyHealthInfoAnalysis) {
        this.dailyHealthInfoAnalysis = dailyHealthInfoAnalysis;
    }

    /**
     * 日次ユーザ情報データ分析連携バッチプロパティクラスを返す
     * 
     * @return dailyUserAnalysis
     */
    public DailyUserAnalysis getDailyUserAnalysis() {
        return dailyUserAnalysis;
    }

    /**
     * 日次ユーザ情報データ分析連携バッチプロパティクラスを設定する
     * 
     * @param dailyUserAnalysis
     *     日次ユーザ情報データ分析連携バッチプロパティクラス
     */
    public void setDailyUserAnalysis(DailyUserAnalysis dailyUserAnalysis) {
        this.dailyUserAnalysis = dailyUserAnalysis;
    }

    /**
     * 健康情報連携バッチプロパティクラス
     * 
     * @version 1.0.0
     */
    public static class HealthInfoMigrate {

        /** 実行カウント */
        private int execPerpageCount;

        /**
         * 実行カウントを返す
         * 
         * @return execPerpageCount
         */
        public int getExecPerpageCount() {
            return execPerpageCount;
        }

        /**
         * 実行カウントを設定する
         * 
         * @param execPerpageCount
         *     実行カウント
         */
        public void setExecPerpageCount(int execPerpageCount) {
            this.execPerpageCount = execPerpageCount;
        }
    }

    /**
     * 月次健康情報集計バッチプロパティクラス
     * 
     * @version 1.0.0
     */
    public static class MonthlyHealthInfoSummary {

        /** 実行カウント */
        private int execPerpageCount;

        /**
         * 実行カウントを返す
         * 
         * @return execPerpageCount
         */
        public int getExecPerpageCount() {
            return execPerpageCount;
        }

        /**
         * 実行カウントを設定する
         * 
         * @param execPerpageCount
         *     実行カウント
         */
        public void setExecPerpageCount(int execPerpageCount) {
            this.execPerpageCount = execPerpageCount;
        }
    }

    /**
     * 日次健康情報データ分析連携バッチプロパティクラス
     * 
     * @version 1.0.0
     */
    public static class DailyHealthInfoAnalysis {

        /** 実行カウント */
        private int execPerpageCount;

        /**
         * 実行カウントを返す
         * 
         * @return execPerpageCount
         */
        public int getExecPerpageCount() {
            return execPerpageCount;
        }

        /**
         * 実行カウントを設定する
         * 
         * @param execPerpageCount
         *     実行カウント
         */
        public void setExecPerpageCount(int execPerpageCount) {
            this.execPerpageCount = execPerpageCount;
        }
    }

    /**
     * 日次ユーザ情報データ分析連携バッチプロパティクラス
     * 
     * @version 1.0.0
     */
    public static class DailyUserAnalysis {

        /** 実行カウント */
        private int execPerpageCount;

        /**
         * 実行カウントを返す
         * 
         * @return execPerpageCount
         */
        public int getExecPerpageCount() {
            return execPerpageCount;
        }

        /**
         * 実行カウントを設定する
         * 
         * @param execPerpageCount
         *     実行カウント
         */
        public void setExecPerpageCount(int execPerpageCount) {
            this.execPerpageCount = execPerpageCount;
        }
    }

    /**
     * データパージバッチプロパティクラス
     * 
     * @version 1.0.0
     */
    public static class DataPurge {

        /** テーブル名 */
        private String tableName;
        /** 有効期限(年) */
        private int expired;

        /**
         * テーブル名を返す
         * 
         * @return tableName
         */
        public String getTableName() {
            return tableName;
        }

        /**
         * テーブル名を設定する
         * 
         * @param tableName
         *     テーブル名
         */
        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        /**
         * 有効期限(年)を返す
         * 
         * @return userExpired
         */
        public int getExpired() {
            return expired;
        }

        /**
         * 有効期限(年)を設定する
         * 
         * @param expired
         *     有効期限(年)
         */
        public void setExpired(int expired) {
            this.expired = expired;
        }

    }

}
