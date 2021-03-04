package jp.co.ha.root.contents.news.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.api.aws.AwsS3Key;
import jp.co.ha.business.dto.NewsListDto;
import jp.co.ha.business.dto.NewsListDto.NewsDto;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.json.reader.JsonReader;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.news.component.NewsComponent;
import jp.co.ha.root.contents.news.request.NewsEditApiRequest;
import jp.co.ha.root.contents.news.response.NewsEditApiResponse;

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
    /** お知らせ情報Component */
    @Autowired
    private NewsComponent newsComponent;

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
            @PathVariable(name = "id", required = false) Optional<String> id,
            @RequestBody NewsEditApiRequest request) throws BaseException {

        NewsListDto dto = new JsonReader().read(
                awsS3Component.getS3ObjectByKey(AwsS3Key.NEWS_JSON), NewsListDto.class);

        NewsDto editData = new NewsDto();
        BeanUtil.copy(request, editData);

        for (int i = 0; i < dto.getNewsDtoList().size(); i++) {
            NewsDto data = dto.getNewsDtoList().get(i);
            if (data.getIndex().equals(editData.getIndex())) {
                dto.getNewsDtoList().set(i, editData);
                break;
            }
        }

        newsComponent.uploadS3(dto);
        newsComponent.sendSlack(editData, "編集したお知らせ情報.json",
                "お知らせ情報JSONを編集.");

        NewsEditApiResponse response = getSuccessResponse();

        return response;
    }

    @Override
    protected NewsEditApiResponse getResponse() {
        return new NewsEditApiResponse();
    }

}
