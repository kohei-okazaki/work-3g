package jp.co.ha.common.aws;

import org.springframework.stereotype.Component;

import software.amazon.awssdk.regions.Region;

/**
 * AWSの設定情報保持クラス<br>
 * 設定ファイル名:aws.properties
 * 
 * @param region
 *     リージョン：設定ファイルで東京を設定
 * @param backet
 *     バケット名：環境毎にバケットを設定
 * @param s3ConnnectionTimeout
 *     S3コネクションタイムアウト：設定ファイルで10秒を設定
 * @param s3SocketTimeout
 *     S3ソケットタイムアウト：設定ファイルで10秒を設定
 * @param sesConnnectionTimeout
 *     SESコネクションタイムアウト：設定ファイルで10秒を設定
 * @param sesSocketTimeout
 *     SESソケットタイムアウト：設定ファイルで10秒を設定
 * @param sesStubFlag
 *     SESスタブフラグ：true:メールを送信しない、false:送信する
 * @param apiLogQueueName
 *     キュー名：環境毎にキュー名を設定
 * @param sqsConnnectionTimeout
 *     SQSコネクションタイムアウト：設定ファイルで10秒を設定
 * @param sqsSocketTimeout
 *     SQSソケットタイムアウト：設定ファイルで10秒を設定
 * @param ssmConnnectionTimeout
 *     SSMコネクションタイムアウト：設定ファイルで10秒を設定
 * @param ssmSocketTimeout
 *     SSMソケットタイムアウト：設定ファイルで10秒を設定
 * @version 1.0.0
 */
@Component
public record AwsProperties(
        Region region,
        String backet,
        int s3ConnnectionTimeout,
        int s3SocketTimeout,
        int sesConnnectionTimeout,
        int sesSocketTimeout,
        boolean sesStubFlag,
        String apiLogQueueName,
        int sqsConnnectionTimeout,
        int sqsSocketTimeout,
        int ssmConnnectionTimeout,
        int ssmSocketTimeout) {
}
