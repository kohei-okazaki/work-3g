package jp.co.ha.business.api.aws;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.ClientConfigurationFactory;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import jp.co.ha.business.exception.BusinessErrorCode;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

/**
 * AWS-S3のComponent
 *
 * @version 1.0.0
 */
@Component
public class AwsS3Component {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(AwsS3Component.class);

    /** AWS個別設定情報 */
    @Autowired
    private AwsConfig awsConfig;
    /** AWS認証情報 */
    @Autowired
    private AwsAuthComponent awsAuthComponent;

    /**
     * {@linkplain AmazonS3}を返す
     *
     * @return AmazonS3
     */
    public AmazonS3 getAmazonS3() {
        return AmazonS3ClientBuilder
                .standard()
                // IAMユーザ認証情報を設定
                .withCredentials(awsAuthComponent.getAWSCredentialsProvider())
                .withClientConfiguration(getClientConfiguration())
                .withRegion(awsConfig.getRegion())
                .build();
    }

    /**
     * {@linkplain S3ObjectSummary}のリストを返す
     *
     * @return ファイルリスト
     * @throws BaseException
     *     AWS、SDK接続エラー
     */
    public List<S3ObjectSummary> getS3ObjectList() throws BaseException {
        try {
            LOG.debug("Amazon S3 region=" + awsConfig.getRegion().getName() + ",backet="
                    + awsConfig.getBacket());
            ObjectListing objectListing = getAmazonS3()
                    .listObjects(awsConfig.getBacket());
            return objectListing.getObjectSummaries();
        } catch (AmazonServiceException e) {
            throw new BusinessException(BusinessErrorCode.AWS_CLIENT_CONNECT_ERROR,
                    "リクエストの処理中にAmazon S3でエラーが発生。backet=" + awsConfig.getBacket(), e);
        } catch (SdkClientException e) {
            throw new BusinessException(BusinessErrorCode.SDK_CLIENT_CONNECT_ERROR,
                    "リクエストの作成中またはレスポンスの処理中にクライアントでエラーが発生。backet=" + awsConfig.getBacket(),
                    e);
        }
    }

    /**
     * 指定されたキーからファイルの入力Streamを返す
     *
     * @param key
     *     キー
     * @return 入力Stream
     * @throws BusinessException
     *     S3へのファイルダウンロードに失敗した場合
     */
    public InputStream getS3ObjectByKey(String key) throws BusinessException {

        try {

            LOG.debug("Amazon S3 region=" + awsConfig.getRegion().getName() + ",backet="
                    + awsConfig.getBacket() + ",key=" + key);
            GetObjectRequest request = new GetObjectRequest(awsConfig.getBacket(), key);
            S3Object s3Object = getAmazonS3().getObject(request);
            return s3Object.getObjectContent();

        } catch (AmazonServiceException e) {
            throw new BusinessException(BusinessErrorCode.AWS_S3_DOWNLOAD_ERROR,
                    "リクエストの処理中にAmazon S3でエラーが発生。backet=" + awsConfig.getBacket()
                            + ", key=" + key,
                    e);
        } catch (SdkClientException e) {
            throw new BusinessException(BusinessErrorCode.SDK_CLIENT_CONNECT_ERROR,
                    "リクエストの作成中またはレスポンスの処理中にクライアントでエラーが発生。" + awsConfig.getBacket()
                            + ", key=" + key,
                    e);
        }

    }

    /**
     * 指定されたキーへ{@linkplain MultipartFile}を配置する
     *
     * @param key
     *     バケット内のキー(ファイル名込)
     * @param multipartFile
     *     Springのアップロードファイル
     * @throws BaseException
     *     S3へファイルアップロードに失敗した場合
     * @see AwsS3Component#putFile(String, long, InputStream)
     */
    public void putFile(String key, MultipartFile multipartFile) throws BaseException {

        try (InputStream is = multipartFile.getInputStream()) {
            putFile(key, multipartFile.getSize(), is);
        } catch (IOException e) {
            throw new BusinessException(e);
        }
    }

    /**
     * 指定されたキーへファイルを配置する
     *
     * @param key
     *     バケット内のキー(ファイル名込)
     * @param file
     *     ファイル
     * @throws BaseException
     *     S3へファイルアップロードに失敗した場合
     * @see AwsS3Component#putFile(String, long, InputStream)
     */
    public void putFile(String key, File file) throws BaseException {

        try (InputStream is = new FileInputStream(file)) {
            putFile(key, file.length(), is);
        } catch (FileNotFoundException e) {
            throw new BusinessException(e);
        } catch (IOException e) {
            throw new BusinessException(e);
        }
    }

    /**
     * S3の指定したキーにInputStreamのデータをファイルとしてアップロードする
     *
     * @param key
     *     バケット内のキー(ファイル名込)
     * @param length
     *     ファイルサイズ
     * @param is
     *     InputStream
     * @throws BaseException
     *     S3へのファイルアップロードに失敗した場合
     */
    private void putFile(String key, long length, InputStream is) throws BaseException {

        try {
            ObjectMetadata om = new ObjectMetadata();
            om.setContentLength(length);
            PutObjectRequest putRequest = new PutObjectRequest(awsConfig.getBacket(), key,
                    is, om);
            // 権限の設定
            putRequest.setCannedAcl(CannedAccessControlList.PublicReadWrite);
            // アップロード
            getAmazonS3().putObject(putRequest);
        } catch (AmazonServiceException e) {
            throw new BusinessException(BusinessErrorCode.AWS_S3_UPLOAD_ERROR,
                    "リクエストの処理中にAmazon S3でエラーが発生し、S3へのファイルアップロードに失敗。backet="
                            + awsConfig.getBacket() + ", key=" + key,
                    e);
        } catch (SdkClientException e) {
            throw new BusinessException(BusinessErrorCode.SDK_CLIENT_CONNECT_ERROR,
                    "リクエストの作成中またはレスポンスの処理中にクライアントでエラーが発生。backet=" + awsConfig.getBacket()
                            + ", key=" + key,
                    e);
        }
    }

    /**
     * {@linkplain ClientConfiguration}を返す
     *
     * @return ClientConfiguration
     */
    private ClientConfiguration getClientConfiguration() {
        ClientConfiguration config = new ClientConfigurationFactory().getConfig();
        config.setConnectionTimeout(awsConfig.getS3Timeout());
        return config;
    }

}
