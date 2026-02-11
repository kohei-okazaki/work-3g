package jp.co.ha.business.io.file.properties;

import org.springframework.stereotype.Component;

/**
 * 健康情報設定ファイルクラス<br>
 * 設定ファイル名:healthinfo.properties<br>
 *
 * @version 1.0.0
 * @param referenceFilePath
 *     照会ファイル格納パス
 * @param healthinfoNodeApiUrl
 *     NodeAPIの基底URL
 * @param healthinfoNodeApiMigrateFlg
 *     NodeAPI 移行フラグ
 * @param healthInfoDashboardUrl
 *     健康情報ダッシュボードの基底URL
 * @param healthInfoApiUrl
 *     健康情報APIの基底URL
 * @param rootApiUrl
 *     管理者用APIの基底URL
 * @param trackApiUrl
 *     健康情報蓄積APIの基底URL
 * @param trackApiDbMigrateFlg
 *     健康情報蓄積API DB移行フラグ
 */
@Component
public record HealthInfoProperties(
        String referenceFilePath,
        String healthinfoNodeApiUrl,
        boolean healthinfoNodeApiMigrateFlg,
        String healthInfoDashboardUrl,
        String healthInfoApiUrl,
        String rootApiUrl,
        String trackApiUrl,
        boolean trackApiDbMigrateFlg) {
}
