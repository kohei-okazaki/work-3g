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

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;

/**
 * AWS-S3のComponent
 *
 * @version 1.0.0
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

    /**
     * 指定されたバケットへファイルを配置する
     *
     * @see AwsS3Component#putFile(String, String, long, InputStream)
     * @param backetName
     *     バケット名
     * @param key
     *     バケット内のキー(ファイル名込)
     * @param multipartFile
     *     Springのアップロードファイル
     * @throws BaseException
     *     S3へファイルアップロードにした場合
     */
    public void putFile(String backetName, String key, MultipartFile multipartFile)
            throws BaseException {
        try (InputStream is = multipartFile.getInputStream()) {
            putFile(backetName, key, multipartFile.getSize(), is);
        } catch (IOException e) {
            throw new BusinessException(e);
        }
    }

    /**
     * 指定されたバケットへファイルを配置する
     *
     * @see AwsS3Component#putFile(String, String, long, InputStream)
     * @param backetName
     *     バケット名
     * @param key
     *     バケット内のキー(ファイル名込)
     * @param file
     *     ファイル
     * @throws BaseException
     */
    public void putFile(String backetName, String key, File file) throws BaseException {
        try (InputStream is = new FileInputStream(file)) {
            putFile(backetName, key, file.length(), is);
        } catch (FileNotFoundException e) {
            // ファイルが存在しない場合は無い
        } catch (IOException e) {
            throw new BusinessException(e);
        }
    }

    /**
     * S3の指定したバケットとキーにInputStramのデータをファイルとしてアップロードする<br>
     *
     * @param backetName
     *     バケット名
     * @param key
     *     バケット内のキー(ファイル名込)
     * @param length
     *     ファイルサイズ
     * @param is
     *     InputStream
     * @throws BaseException
     *     S3へのファイルアップロードに失敗した場合
     */
    private void putFile(String backetName, String key, long length, InputStream is)
            throws BaseException {
        try {
            ObjectMetadata om = new ObjectMetadata();
            om.setContentLength(length);
            PutObjectRequest putRequest = new PutObjectRequest(backetName,
                    key, is, om);
            // 権限の設定
            putRequest.setCannedAcl(CannedAccessControlList.PublicReadWrite);
            // アップロード
            getAmazonS3().putObject(putRequest);
        } catch (Exception e) {
            throw new BusinessException(CommonErrorCode.FILE_UPLOAD_ERROR,
                    "S3へのファイルアップロードに失敗しました。backet=" + backetName + ", key=" + key, e);
        }
    }

}
