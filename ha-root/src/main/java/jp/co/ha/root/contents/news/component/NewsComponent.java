package jp.co.ha.root.contents.news.component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.api.aws.AwsS3Key;
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.business.dto.NewsListDto;
import jp.co.ha.business.dto.NewsListDto.NewsDto;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.type.Charset;

/**
 * お知らせ情報に関するComponent
 *
 * @version 1.0.0
 */
@Component
public class NewsComponent {

    /** S3コンポーネント */
    @Autowired
    private AwsS3Component awsS3Component;
    /** SlackComponent */
    @Autowired
    private SlackApiComponent slackApi;

    /**
     * S3へのファイルアップロード
     *
     * @param dto
     *     お知らせ情報
     * @throws BaseException
     *     S3へのファイルアップロードに失敗した場合
     */
    public void uploadS3(NewsListDto dto) throws BaseException {

        try {
            String json = new ObjectMapper().writeValueAsString(dto);
            byte[] jsonByte = json.getBytes(Charset.UTF_8.getValue());
            InputStream is = new ByteArrayInputStream(jsonByte);
            awsS3Component.putFileByInputStream(AwsS3Key.NEWS_JSON,
                    Long.valueOf(jsonByte.length), is);
        } catch (JsonProcessingException e) {
            // JSON文字列への変換に失敗した場合
            throw new BusinessException(e);
        } catch (UnsupportedEncodingException e) {
            // 文字コードが不正な場合
            throw new BusinessException(e);
        }
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

        try {
            byte[] jsonByte = new ObjectMapper()
                    .enable(SerializationFeature.INDENT_OUTPUT)
                    .writeValueAsString(dto)
                    .getBytes(Charset.UTF_8.getValue());
            slackApi.sendFile(ContentType.ROOT, jsonByte, "news.json", fileName, message);
        } catch (JsonProcessingException e) {
            // JSON文字列への変換に失敗した場合
            throw new BusinessException(e);
        } catch (UnsupportedEncodingException e) {
            // 文字コードが不正な場合
            throw new BusinessException(e);
        }
    }

}
