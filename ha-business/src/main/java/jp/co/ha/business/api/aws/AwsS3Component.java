package jp.co.ha.business.api.aws;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.ClientConfigurationFactory;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
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
import jp.co.ha.common.type.Charset;

/**
 * AWS-S3のComponent
 *
 * @version 1.0.0
 */
@Component
public class AwsS3Component {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(AwsS3Component.class);
    /** 文字コード */
    private static final String UTF_8 = Charset.UTF_8.getValue();

    /** {@linkplain AwsConfig} */
    @Autowired
    private AwsConfig awsConfig;
    /** {@linkplain AwsAuthComponent} */
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
        } catch (AmazonClientException e) {
            throw new BusinessException(BusinessErrorCode.AWS_CLIENT_CONNECT_ERROR,
                    "リクエストの処理中にAmazon S3でエラーが発生。backet=" + awsConfig.getBacket(), e);
        }
    }

    /**
     * 指定されたキーからファイルのInputStreamを返す
     *
     * @param key
     *     キー
     * @return InputStream
     * @throws BusinessException
     *     S3からファイルダウンロードに失敗した場合
     * @see #getS3ObjectByKey(String)
     */
    public InputStream getS3ObjectByKey(AwsS3Key key) throws BusinessException {
        return this.getS3ObjectByKey(key.getValue());
    }

    /**
     * 指定されたキーからファイルのInputStreamを返す
     *
     * @param key
     *     キー
     * @return InputStream
     * @throws BusinessException
     *     S3からファイルダウンロードに失敗した場合
     */
    public InputStream getS3ObjectByKey(String key) throws BusinessException {

        try {

            LOG.debug("Amazon S3 region=" + awsConfig.getRegion().getName() + ",backet="
                    + awsConfig.getBacket() + ",key=" + key);
            GetObjectRequest request = new GetObjectRequest(awsConfig.getBacket(), key);
            S3Object s3Object = getAmazonS3().getObject(request);
            return s3Object.getObjectContent();
        } catch (AmazonClientException e) {
            throw new BusinessException(BusinessErrorCode.AWS_S3_DOWNLOAD_ERROR,
                    "リクエストの処理中にAmazon S3でエラーが発生。backet=" + awsConfig.getBacket()
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
     * @see AwsS3Component#putFile(String, File)
     */
    public void putFile(AwsS3Key key, File file) throws BaseException {
        this.putFile(key.getValue(), file);
    }

    /**
     * 指定されたキーへ文字列データをファイルとして配置する
     *
     * @param key
     * @param strData
     * @throws BaseException
     *     S3へファイルアップロードに失敗した場合
     * @see #putFile(String, long, InputStream)
     */
    public void putFile(String key, String strData) throws BaseException {
        try {
            byte[] b = strData.getBytes(UTF_8);
            InputStream is = new ByteArrayInputStream(b);
            putFile(key, b.length, is);
        } catch (UnsupportedEncodingException e) {
            // UTF-8指定のため、発生しない
            LOG.warn("文字コードの指定が不正です", e);
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
     * @see AwsS3Component#putFile(String, long, InputStream)
     */
    public void putFileByInputStream(AwsS3Key key, long length, InputStream is)
            throws BaseException {
        this.putFile(key.getValue(), length, is);
    }

    /**
     * 指定されたS3キーのファイルを削除する
     *
     * @param keys
     *     S3キー
     */
    public void removeS3ObjectByKeys(String... keys) {

        // List<KeyVersion> deleteList = Stream.of(keys).map(KeyVersion::new)
        // .collect(Collectors.toList());
        // ファイル削除
        @SuppressWarnings("unused")
        DeleteObjectsResult result = getAmazonS3()
                .deleteObjects(
                        new DeleteObjectsRequest(awsConfig.getBacket()).withKeys(keys));
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
    public void putFile(String key, long length, InputStream is) throws BaseException {

        try {
            ObjectMetadata om = new ObjectMetadata();
            om.setContentLength(length);
            PutObjectRequest putRequest = new PutObjectRequest(awsConfig.getBacket(), key,
                    is, om);
            // 権限の設定
            putRequest.setCannedAcl(CannedAccessControlList.PublicReadWrite);
            // アップロード
            getAmazonS3().putObject(putRequest);
        } catch (AmazonClientException e) {
            throw new BusinessException(BusinessErrorCode.AWS_S3_UPLOAD_ERROR,
                    "リクエストの処理中にAmazon S3でエラーが発生し、S3へのファイルアップロードに失敗。backet="
                            + awsConfig.getBacket() + ", key=" + key,
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
