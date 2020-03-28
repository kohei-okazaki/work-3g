package jp.co.ha.business.aws;

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
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.exception.DashboardErrorCode;
import jp.co.ha.common.exception.BaseException;

/**
 * AWS-S3のComponent
 *
 * @version 1.0.0
 */
@Component
public class AwsS3Component {

    /** AWS個別設定情報 */
    @Autowired
    private AwsConfig awsConfig;
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
                .withRegion(awsConfig.getRegion())
                .build();
    }

    /**
     * バケット内に保存されているファイル一覧を返す
     *
     * @param backet
     *     バケット名
     * @return ファイルリスト
     */
    public List<S3ObjectSummary> getS3ObjectSummaryListByBacket(String backet) {
        ObjectListing objectListing = getAmazonS3().listObjects(backet);
        return objectListing.getObjectSummaries();
    }

    /**
     * 指定されたバケット名とキーからファイルの入力Streamを返す
     *
     * @param backet
     *     バケット名
     * @param key
     *     キー
     * @return 入力Stream
     * @throws BusinessException
     *     S3へのファイルダウンロードに失敗した場合
     */
    public InputStream getS3ObjectByBacketAndKey(String backet, String key)
            throws BusinessException {

        try {
            GetObjectRequest request = new GetObjectRequest(backet, key);
            S3Object s3Object = getAmazonS3().getObject(request);
            return s3Object.getObjectContent();

        } catch (AmazonServiceException e) {
            throw new BusinessException(DashboardErrorCode.AWS_S3_DOWNLOAD_ERROR,
                    "S3のファイルダウンロードに失敗しました。backet=" + awsConfig.getBacket() + ", key="
                            + key,
                    e);
        }

    }

    /**
     * 指定されたバケットへファイルを配置する
     *
     * @param key
     *     バケット内のキー(ファイル名込)
     * @param multipartFile
     *     Springのアップロードファイル
     * @throws BaseException
     *     S3へファイルアップロードに失敗した場合
     * @see AwsS3Component#putFile(String, long, InputStream)
     */
    public void putFile(String key, MultipartFile multipartFile)
            throws BaseException {

        try (InputStream is = multipartFile.getInputStream()) {
            putFile(key, multipartFile.getSize(), is);
        } catch (IOException e) {
            throw new BusinessException(e);
        }
    }

    /**
     * 指定されたバケットへファイルを配置する
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
            // ファイルが存在しない場合は無い
        } catch (IOException e) {
            throw new BusinessException(e);
        }
    }

    /**
     * S3の指定したキーにInputStramのデータをファイルとしてアップロードする<br>
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
    private void putFile(String key, long length, InputStream is)
            throws BaseException {

        try {
            ObjectMetadata om = new ObjectMetadata();
            om.setContentLength(length);
            PutObjectRequest putRequest = new PutObjectRequest(awsConfig.getBacket(),
                    key, is, om);
            // 権限の設定
            putRequest.setCannedAcl(CannedAccessControlList.PublicReadWrite);
            // アップロード
            getAmazonS3().putObject(putRequest);
        } catch (AmazonServiceException e) {
            throw new BusinessException(DashboardErrorCode.AWS_S3_UPLOAD_ERROR,
                    "S3へのファイルアップロードに失敗しました。backet=" + awsConfig.getBacket() + ", key="
                            + key,
                    e);
        }
    }

}
