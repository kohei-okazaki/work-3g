package jp.co.ha.root.contents.news.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
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
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.io.file.json.reader.JsonReader;
import jp.co.ha.common.type.Charset;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.news.request.NewsEntiryApiRequest;
import jp.co.ha.root.contents.news.response.NewsEntiryApiResponse;
import jp.co.ha.root.contents.news.response.NewsListApiResponse;
import jp.co.ha.root.type.RootApiResult;

/**
 * お知らせ情報登録APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class NewsEntiryApiController
        extends BaseRootApiController<NewsEntiryApiRequest, NewsEntiryApiResponse> {

    /** S3コンポーネント */
    @Autowired
    private AwsS3Component awsS3Component;
    /** SlackComponent */
    @Autowired
    private SlackApiComponent slackApi;

    /**
     * お知らせ情報登録処理
     *
     * @param request
     *     お知らせ情報登録APIリクエスト
     * @return お知らせ情報登録APIレスポンス
     * @throws BaseException
     */
    @PostMapping(value = "news/entry", produces = { MediaType.APPLICATION_JSON_VALUE })
    public NewsEntiryApiResponse entry(
            @RequestBody MultiValueMap<String, Object> request) throws BaseException {

        for (Entry<String, List<Object>> entry : request.entrySet()) {
            if (CollectionUtil.isEmpty(entry.getValue())) {
                throw new BusinessException(CommonErrorCode.VALIDATE_ERROR,
                        "required error key=" + entry.getKey() + ", value="
                                + entry.getValue());
            }
        }

        InputStream is = awsS3Component.getS3ObjectByKey(AwsS3Key.NEWS_JSON);
        NewsListDto dto = new JsonReader().read(is, NewsListDto.class);
        // indexの昇順ソート
        List<NewsDto> dtoList = dto.getNewsDtoList()
                .stream()
                .sorted(Comparator.comparing(NewsDto::getIndex))
                .collect(Collectors.toList());
        NewsDto entryData = new NewsDto();
        entryData.setIndex(CollectionUtil.getLast(dtoList).getIndex() + 1);
        entryData.setTitle(request.get("title").get(0).toString());
        entryData.setDate(request.get("date").get(0).toString());
        entryData.setDetail(request.get("detail").get(0).toString());
        entryData.setTagColor(request.get("tag_color").get(0).toString());
        entryData.setTagName(request.get("tag_name").get(0).toString());
        dtoList.add(entryData);
        dto.setNewsDtoList(dtoList);

        uploadS3(dto);
        sendSlack(entryData);

        NewsEntiryApiResponse response = new NewsEntiryApiResponse();
        response.setRootApiResult(RootApiResult.SUCCESS);
        NewsListApiResponse.NewsDataResponse entryResponse = new NewsListApiResponse.NewsDataResponse();
        BeanUtil.copy(entryData, entryResponse);
        response.setNewsDataResponse(entryResponse);

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
            slackApi.sendFile(ContentType.ROOT, jsonByte, "news.json", "追加したお知らせ情報.json",
                    "お知らせ情報JSONを追加.");
        } catch (JsonProcessingException e) {
            // JSON文字列への変換に失敗した場合
            throw new BusinessException(e);
        } catch (UnsupportedEncodingException e) {
            // 文字コードが不正な場合
            throw new BusinessException(e);
        }
    }

}
