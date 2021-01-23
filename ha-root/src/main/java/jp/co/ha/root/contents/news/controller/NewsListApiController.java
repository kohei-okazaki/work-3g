package jp.co.ha.root.contents.news.controller;

import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.api.aws.AwsS3Key;
import jp.co.ha.business.dto.NewsListDto;
import jp.co.ha.business.dto.NewsListDto.NewsDto;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.json.reader.JsonReader;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.news.request.NewsListApiRequest;
import jp.co.ha.root.contents.news.response.NewsListApiResponse;
import jp.co.ha.root.type.RootApiResult;

/**
 * お知らせ情報一覧取得APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class NewsListApiController
        extends BaseRootApiController<NewsListApiRequest, NewsListApiResponse> {

    /** S3コンポーネント */
    @Autowired
    private AwsS3Component awsS3Component;

    /**
     * お知らせ情報一覧取得
     *
     * @param request
     *     お知らせ情報一覧取得APIリクエスト
     * @return お知らせ情報一覧取得APIレスポンス
     * @throws BaseException
     *     S3よりお知らせ情報JSONを取得できなかった場合
     */
    @GetMapping(value = "news", produces = { MediaType.APPLICATION_JSON_VALUE })
    public NewsListApiResponse list(NewsListApiRequest request) throws BaseException {

        // S3からお知らせJSONを取得
        InputStream is = awsS3Component.getS3ObjectByKey(AwsS3Key.NEWS_JSON);
        List<NewsDto> newsList = new JsonReader().read(is, NewsListDto.class)
                .getNewsDtoList();

        // レスポンス型に変換し、Indexの降順でソート
        List<NewsListApiResponse.NewsDataResponse> newsResponseList = newsList.stream()
                .map(e -> {
                    NewsListApiResponse.NewsDataResponse response = new NewsListApiResponse.NewsDataResponse();
                    BeanUtil.copy(e, response);
                    return response;
                })
                .sorted(Comparator
                        .comparing(NewsListApiResponse.NewsDataResponse::getIndex)
                        .reversed())
                .collect(Collectors.toList());

        NewsListApiResponse response = new NewsListApiResponse();
        response.setRootApiResult(RootApiResult.SUCCESS);
        response.setNewsDataResponseList(newsResponseList);

        return response;
    }

}
