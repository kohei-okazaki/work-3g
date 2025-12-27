package jp.co.ha.business.api.aws;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
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

import jp.co.ha.business.exception.BusinessErrorCode;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.SystemRuntimeException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.BaseEnum;
import jp.co.ha.common.type.Charset;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Delete;
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
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

    /** AWS設定ファイル情報 */
    @Autowired
    private AwsProperties awsProps;
    /** AWS認証情報Component */
    @Autowired
    private AwsAuthComponent auth;

    /**
     * S3の指定したキーにInputStreamのデータをファイルとしてアップロードする
     *
     * @param key
     *     バケット内のキー(ファイル名込)
     * @param is
     *     InputStream
     * @param length
     *     ファイルサイズ
     * @throws BaseException
     *     S3からファイルダウンロードエラー
     */
    public void putFile(String key, long length, InputStream is)
            throws BaseException {

        try {
            S3Client s3 = getS3Client();
            s3.putObject(
                    PutObjectRequest.builder()
                            .bucket(awsProps.getBacket())
                            .key(key)
                            .acl(ObjectCannedACL.PUBLIC_READ)
                            .build(),
                    RequestBody.fromInputStream(is, length));
        } catch (Exception e) {
            throw new BusinessException(BusinessErrorCode.AWS_S3_UPLOAD_ERROR, e);
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
     *     S3からファイルダウンロードエラー
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
     * 指定されたキーへ文字列データをファイルとして配置する
     *
     * @param key
     *     キー
     * @param strData
     *     文字列データ
     * @throws BaseException
     *     S3からファイルダウンロードエラー
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
     *     S3からファイルダウンロードエラー
     * @see AwsS3Component#putFile(String, long, InputStream)
     */
    public void putFile(String key, File file) throws BaseException {

        try (InputStream is = new FileInputStream(file)) {
            putFile(key, file.length(), is);
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
     *     S3からファイルダウンロードエラー
     * @see AwsS3Component#putFile(String, File)
     */
    public void putFile(AwsS3Key key, File file) throws BaseException {
        putFile(key.getValue(), file);
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
     *     S3からファイルダウンロードエラー
     * @see AwsS3Component#putFile(String, long, InputStream)
     */
    public void putFile(AwsS3Key key, long length, InputStream is)
            throws BaseException {
        putFile(key.getValue(), length, is);
    }

    /**
     * 指定されたキーからファイルのInputStreamを返す<br>
     * 必要に応じて呼び出し側で#close()
     *
     * @param key
     *     キー
     * @return InputStream
     * @throws BaseException
     *     S3からファイルダウンロードエラー
     */
    public InputStream getS3ObjectByKey(String key) throws BaseException {

        S3Client s3 = getS3Client();
        try {
            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(awsProps.getBacket())
                    .key(key)
                    .build();

            return s3.getObject(request);

        } catch (Exception e) {
            throw new BusinessException(BusinessErrorCode.AWS_S3_DOWNLOAD_ERROR, e);
        }
    }

    /**
     * 指定されたキーからファイルのInputStreamを返す
     *
     * @param key
     *     キー
     * @return InputStream
     * @throws BaseException
     *     S3からファイルダウンロードエラー
     * @see #getS3ObjectByKey(String)
     */
    public InputStream getS3ObjectByKey(AwsS3Key key) throws BaseException {
        return getS3ObjectByKey(key.getValue());
    }

    /**
     * {@linkplain S3Object}のリストを返す
     *
     * @return ファイルリスト
     * @throws BaseException
     *     S3からファイルダウンロードエラー
     */
    public List<S3Object> getS3ObjectList() throws BaseException {

        S3Client s3 = getS3Client();
        try {
            ListObjectsV2Request request = ListObjectsV2Request.builder()
                    .bucket(awsProps.getBacket())
                    .build();

            return s3.listObjectsV2(request).contents();

        } catch (Exception e) {
            throw new BusinessException(BusinessErrorCode.AWS_S3_DOWNLOAD_ERROR, e);
        }
    }

    /**
     * 指定されたS3キーのファイルを削除する
     *
     * @param keys
     *     S3キー
     * @throws BaseException
     *     S3ファイル削除失敗エラー
     */
    public void removeS3ObjectByKeys(String... keys) throws BaseException {

        S3Client s3 = getS3Client();

        try {
            DeleteObjectsRequest deleteRequest = DeleteObjectsRequest.builder()
                    .bucket(awsProps.getBacket())
                    .delete(Delete.builder()
                            .objects(
                                    Arrays.stream(keys)
                                            .map(key -> ObjectIdentifier.builder()
                                                    .key(key)
                                                    .build())
                                            .collect(Collectors.toList()))
                            .build())
                    .build();

            s3.deleteObjects(deleteRequest);
        } catch (Exception e) {
            throw new SystemRuntimeException(BusinessErrorCode.AWS_S3_DELETE_ERROR,
                    e);
        }
    }

    /**
     * {@linkplain S3Client}を返す
     * 
     * @return S3Client
     * @throws BaseException
     *     AWSクライアント接続エラー
     */
    private S3Client getS3Client() throws BaseException {

        try {

            // HttpClient にタイムアウトを設定する
            SdkHttpClient httpClient = ApacheHttpClient.builder()
                    .connectionTimeout(
                            Duration.ofMillis(awsProps.getS3ConnnectionTimeout()))
                    .socketTimeout(Duration.ofMillis(awsProps.getS3SocketTimeout()))
                    .build();

            return S3Client.builder()
                    .region(awsProps.getRegion())
                    .credentialsProvider(auth.getAWSCredentialsProvider())
                    .httpClient(httpClient)
                    .build();

        } catch (Exception e) {
            throw new BusinessException(BusinessErrorCode.AWS_CLIENT_CONNECT_ERROR, e);
        }
    }

    /**
     * AWSのS3のキー列挙
     *
     * @version 1.0.0
     */
    public static enum AwsS3Key implements BaseEnum {

        /** 健康情報一括登録CSVファイル配置キー */
        HEALTHINFO_FILE_REGIST("healthinfo-file-regist"),
        /** 健康情報照会CSVファイルの配置キー */
        HEALTHINFO_FILE_REFERENCE("healthinfo-file-reference"),
        /** お知らせ一覧JSONファイルの配置キー */
        NEWS_JSON("news"),
        /** 月次健康情報集計CSV配置キー */
        MONTHLY_HEALTHINFO_SUMMARY("monthly/healthinfo"),
        /** 日次データ分析連携CSV配置キー */
        DAILY_ANALYSIS("analysis"),
        /** メモ情報配置キー */
        NOTE_FILE("note"),
        /** 問い合わせキー */
        INQUIRY("inquiry");

        /**
         * コンストラクタ
         *
         * @param value
         *     値
         */
        private AwsS3Key(String value) {
            this.value = value;
        }

        /** 値 */
        private String value;

        @Override
        public String getValue() {
            return this.value;
        }

    }

}
