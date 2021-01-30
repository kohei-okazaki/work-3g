package jp.co.ha.root.contents.news.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.news.request.NewsDeleteApiRequest;
import jp.co.ha.root.contents.news.response.NewsDeleteApiResponse;
import jp.co.ha.root.type.RootApiResult;

/**
 * お知らせ情報削除APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class NewsDeleteApiController
        extends BaseRootApiController<NewsDeleteApiRequest, NewsDeleteApiResponse> {

    /** S3コンポーネント */
    @Autowired
    private AwsS3Component awsS3Component;
    /** SlackComponent */
    @Autowired
    private SlackApiComponent slackApi;

    /**
     * お知らせ情報削除
     *
     * @param id
     *     お知らせ情報ID
     * @param request
     *     おしらせ情報削除APIリクエスト
     * @return おしらせ情報削除APIレスポンス
     * @throws BaseException
     *     URLが不正な場合
     */
    @DeleteMapping(value = "news/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public NewsDeleteApiResponse delete(
            @PathVariable(name = "id", required = false) Optional<String> id,
            NewsDeleteApiRequest request) throws BaseException {

        if (id == null || !id.isPresent()) {
            // URLが不正場合
            throw new BusinessException(CommonErrorCode.VALIDATE_ERROR, "id=" + id);
        }

        int deleteId = Integer.parseInt(id.get());
        InputStream is = awsS3Component.getS3ObjectByKey(AwsS3Key.NEWS_JSON);
        NewsListDto dto = new JsonReader().read(is, NewsListDto.class);
        List<NewsDto> dtoList = dto.getNewsDtoList()
                .stream().filter(e -> e.getIndex().intValue() != deleteId)
                .sorted(Comparator.comparing(NewsDto::getIndex))
                .collect(Collectors.toList());
        dto.setNewsDtoList(dtoList);

        uploadS3(dto);
        slackApi.send(ContentType.ROOT,
                "お知らせ情報ID=" + String.valueOf(deleteId) + "を削除しました");

        NewsDeleteApiResponse response = new NewsDeleteApiResponse();
        response.setRootApiResult(RootApiResult.SUCCESS);
        response.setDeleteNewsId(deleteId);

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
}
