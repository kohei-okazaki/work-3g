package jp.co.ha.root.contents.news.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
import jp.co.ha.common.io.file.json.reader.JsonReader;
import jp.co.ha.common.type.Charset;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.news.request.NewsEditApiRequest;
import jp.co.ha.root.contents.news.response.NewsEditApiResponse;
import jp.co.ha.root.type.RootApiResult;

/**
 * お知らせ情報編集APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class NewsEditApiController
        extends BaseRootApiController<NewsEditApiRequest, NewsEditApiResponse> {

    /** S3コンポーネント */
    @Autowired
    private AwsS3Component awsS3Component;
    /** SlackComponent */
    @Autowired
    private SlackApiComponent slackApi;

    /**
     * 編集
     *
     * @param id
     *     お知らせ情報ID
     * @param request
     *     おしらせ情報編集APIリクエスト
     * @return おしらせ情報編集APIレスポンス
     * @throws BaseException
     */
    @PutMapping(value = "news/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public NewsEditApiResponse edit(
            @RequestBody NewsEditApiRequest request,
            @PathVariable(name = "id", required = false) Optional<String> id)
            throws BaseException {

        InputStream is = awsS3Component.getS3ObjectByKey(AwsS3Key.NEWS_JSON);
        NewsListDto dto = new JsonReader().read(is, NewsListDto.class);

        NewsDto editData = new NewsDto();
        BeanUtil.copy(request, editData);

        for (int i = 0; i < dto.getNewsDtoList().size(); i++) {
            NewsDto data = dto.getNewsDtoList().get(i);
            if (data.getIndex().equals(editData.getIndex())) {
                dto.getNewsDtoList().set(i, editData);
                break;
            }
        }

        uploadS3(dto);
        sendSlack(editData);

        NewsEditApiResponse response = new NewsEditApiResponse();
        response.setRootApiResult(RootApiResult.SUCCESS);

        return response;
    }

    /**
     * S3へのファイルアップロード
     *
     * @param dto
     *     お知らせ情報
     * @throws BaseException
     *     S3へのファイルアップロードに失敗した場合
     */
    private void uploadS3(NewsListDto dto) throws BaseException {

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
     * 追加したお知らせ情報JSONをSlackに送信する
     *
     * @param dto
     *     追加したお知らせ情報JSON
     * @throws BaseException
     *     Slackへのメッセージの送信に失敗した場合
     */
    private void sendSlack(NewsDto dto) throws BaseException {

        try {
            byte[] jsonByte = new ObjectMapper()
                    .enable(SerializationFeature.INDENT_OUTPUT)
                    .writeValueAsString(dto)
                    .getBytes(Charset.UTF_8.getValue());
            slackApi.sendFile(ContentType.ROOT, jsonByte, "news.json", "編集したお知らせ情報.json",
                    "お知らせ情報JSONを編集.");
        } catch (JsonProcessingException e) {
            // JSON文字列への変換に失敗した場合
            throw new BusinessException(e);
        } catch (UnsupportedEncodingException e) {
            // 文字コードが不正な場合
            throw new BusinessException(e);
        }
    }

}
