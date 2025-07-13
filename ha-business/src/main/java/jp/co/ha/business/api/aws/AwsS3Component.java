package jp.co.ha.business.api.aws;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.Charset;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Delete;
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

/**
 * AWS-Simple Storage ServiceのComponent
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
     * S3の指定したキーにInputStreamのデータをファイルとしてアップロードする
     *
     * @param key
     *     バケット内のキー(ファイル名込)
     * @param is
     *     InputStream
     * @param length
     *     ファイルサイズ
     */
    public void putFile(String key, long length, InputStream is) {

        S3Client s3 = getS3Client();

        s3.putObject(
                PutObjectRequest.builder()
                        .bucket(awsConfig.getBacket())
                        .key(key)
                        .acl(ObjectCannedACL.PUBLIC_READ)
                        .build(),
                RequestBody.fromInputStream(is, length));
    }

    /**
     * 指定されたキーからファイルのInputStreamを返す<br>
     * 必要に応じて呼び出し側で#close()
     *
     * @param key
     *     キー
     * @return InputStream
     */
    public InputStream getS3ObjectByKey(String key) {

        S3Client s3 = getS3Client();

        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(awsConfig.getBacket())
                .key(key)
                .build();

        ResponseInputStream<GetObjectResponse> s3Object = s3.getObject(request);

        return s3Object;
    }

    /**
     * {@linkplain S3Object}のリストを返す
     *
     * @return ファイルリスト
     */
    public List<S3Object> getS3ObjectList() {

        S3Client s3 = getS3Client();

        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(awsConfig.getBacket())
                .build();

        ListObjectsV2Response result = s3.listObjectsV2(request);

        return result.contents();
    }

    /**
     * 指定されたS3キーのファイルを削除する
     *
     * @param keys
     *     S3キー
     */
    public void removeS3ObjectByKeys(String... keys) {

        S3Client s3 = getS3Client();

        DeleteObjectsRequest deleteRequest = DeleteObjectsRequest.builder()
                .bucket(awsConfig.getBacket())
                .delete(Delete.builder()
                        .objects(
                                Arrays.stream(keys)
                                        .map(key -> ObjectIdentifier.builder().key(key)
                                                .build())
                                        .collect(Collectors.toList()))
                        .build())
                .build();

        s3.deleteObjects(deleteRequest);
    }

    /**
     * {@linkplain S3Client}を返す
     * 
     * @return S3Client
     */
    private S3Client getS3Client() {

        // HttpClient にタイムアウトを設定する
        SdkHttpClient httpClient = ApacheHttpClient.builder()
                .connectionTimeout(Duration.ofMillis(awsConfig.getS3Timeout()))
                .socketTimeout(Duration.ofMillis(awsConfig.getS3Timeout()))
                .build();

        return S3Client.builder()
                .region(awsConfig.getRegion())
                .credentialsProvider(awsAuthComponent.getAWSCredentialsProvider())
                .httpClient(httpClient)
                .build();
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
        return getS3ObjectByKey(key.getValue());
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
        putFile(key.getValue(), file);
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
        putFile(key.getValue(), length, is);
    }

}
