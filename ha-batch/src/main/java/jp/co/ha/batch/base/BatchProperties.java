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

    /** 健康情報連携バッチプロパティファイルクラス */
    private HealthInfoMigrate healthInfoMigrate;

    /**
     * 健康情報連携バッチプロパティファイルクラスを返す
     * 
     * @return healthInfoMigrate
     */
    public HealthInfoMigrate getHealthInfoMigrate() {
        return healthInfoMigrate;
    }

    /**
     * 健康情報連携バッチプロパティファイルクラスを設定する
     * 
     * @param healthInfoMigrate
     *     健康情報連携バッチプロパティファイルクラス
     */
    public void setHealthInfoMigrate(HealthInfoMigrate healthInfoMigrate) {
        this.healthInfoMigrate = healthInfoMigrate;
    }

    /**
     * 健康情報連携バッチプロパティファイルクラス
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
}
