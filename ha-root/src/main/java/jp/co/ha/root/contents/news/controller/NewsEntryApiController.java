package jp.co.ha.root.contents.news.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.api.aws.AwsS3Key;
import jp.co.ha.business.dto.NewsListDto;
import jp.co.ha.business.dto.NewsListDto.NewsDto;
import jp.co.ha.business.dto.NewsListDto.Tag;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.json.reader.JsonReader;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.news.component.NewsComponent;
import jp.co.ha.root.contents.news.request.NewsEntryApiRequest;
import jp.co.ha.root.contents.news.response.NewsEntryApiResponse;
import jp.co.ha.root.contents.news.response.NewsListApiResponse;

/**
 * お知らせ情報登録APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class NewsEntryApiController
        extends BaseRootApiController<NewsEntryApiRequest, NewsEntryApiResponse> {

    /** S3コンポーネント */
    @Autowired
    private AwsS3Component awsS3Component;
    /** お知らせ情報Component */
    @Autowired
    private NewsComponent newsComponent;

    /**
     * お知らせ情報登録処理
     *
     * @param request
     *     お知らせ情報登録APIリクエスト
     * @return お知らせ情報登録APIレスポンス
     * @throws BaseException
     *     JSONの取得/アップロードまたはSlackの通知に失敗した場合
     */
    @PostMapping(value = "news", produces = { MediaType.APPLICATION_JSON_VALUE })
    public NewsEntryApiResponse entry(@RequestBody NewsEntryApiRequest request)
            throws BaseException {

        NewsListDto dto = new JsonReader().read(
                awsS3Component.getS3ObjectByKey(AwsS3Key.NEWS_JSON), NewsListDto.class);
        // indexの昇順ソート
        List<NewsDto> dtoList = dto.getNewsDtoList()
                .stream()
                .sorted(Comparator.comparing(NewsDto::getIndex))
                .collect(Collectors.toList());
        NewsDto entryData = new NewsDto();
        entryData.setIndex(CollectionUtil.getLast(dtoList).getIndex() + 1);
        BeanUtil.copy(request, entryData);
        Tag tag = new Tag();
        BeanUtil.copy(request.getTag(), tag);
        entryData.setTag(tag);
        dtoList.add(entryData);
        dto.setNewsDtoList(dtoList);

        newsComponent.uploadS3(dto);
        newsComponent.sendSlack(entryData, "追加したお知らせ情報.json",
                "お知らせ情報JSONを追加.");

        NewsEntryApiResponse response = getSuccessResponse();
        NewsListApiResponse.NewsDataResponse entryResponse = new NewsListApiResponse.NewsDataResponse();
        BeanUtil.copy(entryData, entryResponse);
        response.setNewsDataResponse(entryResponse);

        return response;
    }

    @Override
    protected NewsEntryApiResponse getResponse() {
        return new NewsEntryApiResponse();
    }

}
