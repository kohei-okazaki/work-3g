package jp.co.ha.batch.base;

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

}
