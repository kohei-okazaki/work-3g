package jp.co.ha.root.contents.news.component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.business.dto.NewsDto;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.io.file.json.reader.JsonReader;
import jp.co.ha.common.type.Charset;

/**
 * お知らせ情報に関するComponent
 *
 * @version 1.0.0
 */
@Component
public class NewsComponent {

    /** AWS S3Component */
    @Autowired
    private AwsS3Component awsS3Component;
    /** SlackApiComponent */
    @Autowired
    private SlackApiComponent slackApi;

    /**
     * 指定されたS3キーよりお知らせ情報JSONを取得
     *
     * @param s3Key
     *     S3キー
     * @return お知らせ情報
     * @throws BaseException
     */
    public NewsDto getNewsDto(String s3Key) throws BaseException {

        // S3からお知らせJSONを取得
        try (InputStream is = awsS3Component.getS3ObjectByKey(s3Key)) {
            return new JsonReader().read(is, NewsDto.class);
        } catch (IOException e) {
            throw new SystemException(e);
        }

    }

    /**
     * お知らせ情報JSONをS3へファイルアップロード
     *
     * @param s3Key
     *     S3キー
     * @param dto
     *     お知らせ情報
     * @throws BaseException
     *     S3へのファイルアップロードに失敗した場合
     */
    public void upload(String s3Key, NewsDto dto) throws BaseException {

        try {
            String json = new ObjectMapper().writeValueAsString(dto);
            byte[] jsonByte = json.getBytes(Charset.UTF_8.getValue());
            InputStream is = new ByteArrayInputStream(jsonByte);
            awsS3Component.putFile(s3Key, Long.valueOf(jsonByte.length), is);
        } catch (JsonProcessingException e) {
            // JSON文字列への変換に失敗した場合
            throw new BusinessException(e);
        } catch (UnsupportedEncodingException e) {
            // 文字コードが不正な場合
            throw new BusinessException(e);
        }
    }

    /**
     * 指定したS3キーを削除
     *
     * @param s3Key
     *     S3キー
     * @throws BusinessException
     *     S3ファイル削除失敗エラー
     */
    public void remove(String s3Key) throws BusinessException {
        awsS3Component.removeS3ObjectByKeys(s3Key);
    }

    /**
     * Slackへお知らせ情報登録/編集のメッセージを投稿する
     *
     * @param dto
     *     お知らせ情報
     * @param fileName
     *     ファイル名
     * @param message
     *     Slackメッセージ
     * @throws BaseException
     *     Slackへの投稿に失敗した場合
     */
    public void sendSlack(NewsDto dto, String fileName, String message)
            throws BaseException {
        slackApi.send(ContentType.ROOT, message);
    }

    /**
     * Slackへお知らせ情報削除のメッセージを投稿する
     *
     * @param seqNewsInfoId
     *     お知らせ情報ID
     * @throws BaseException
     *     Slackへの投稿に失敗した場合
     */
    public void sendSlack(Long seqNewsInfoId) throws BaseException {
        slackApi.send(ContentType.ROOT, "お知らせ情報ID=" + seqNewsInfoId + "を削除.");
    }

}
