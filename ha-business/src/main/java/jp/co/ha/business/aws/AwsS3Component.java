package jp.co.ha.business.aws;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectSummary;

/**
 * AWS-S3のComponent
 *
 * @version 1.0
 */
@Component
public class AwsS3Component {

    /** AWS認証情報 */
    @Autowired
    private AwsAuthComponent awsAuthComponent;

    /**
     * AmazonS3を返す
     *
     * @return AmazonS3
     */
    public AmazonS3 getAmazonS3() {
        return AmazonS3ClientBuilder
                .standard()
                // 認証情報を設定
                .withCredentials(new AWSStaticCredentialsProvider(
                        awsAuthComponent.getCredentials()))
                // リージョンを AP_NORTHEAST_1(東京) に設定
                .withRegion(Regions.AP_NORTHEAST_1)
                .build();
    }

    /**
     * バケット内に保存されているファイル一覧を返す
     * 
     * @param backetName
     *     バケット名
     * @return ファイルリスト
     */
    public List<S3ObjectSummary> getS3ObjectSummaryListByBacketName(String backetName) {
        return getAmazonS3().listObjects(backetName).getObjectSummaries();
    }

}
