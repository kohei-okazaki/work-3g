package jp.co.ha.root.contents.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.db.crud.read.ApiCommunicationDataSearchService;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.api.request.ApiDataListApiRequest;
import jp.co.ha.root.contents.api.response.ApiDataListApiResponse;

/**
 * API通信情報一覧取得APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class ApiDataListApiController
        extends BaseRootApiController<ApiDataListApiRequest, ApiDataListApiResponse> {

    /** API通信情報検索サービス */
    @Autowired
    private ApiCommunicationDataSearchService searchService;

    /**
     * 一覧取得
     *
     * @param request
     *     API通信情報一覧取得APIリクエスト
     * @return API通信情報一覧取得APIレスポンス
     */
    @GetMapping(value = "apidata", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ApiDataListApiResponse list(ApiDataListApiRequest request) {

        List<ApiDataListApiResponse.ApiData> apiDataList = searchService.findAll()
                .stream().map(e -> {
                    ApiDataListApiResponse.ApiData response = new ApiDataListApiResponse.ApiData();
                    BeanUtil.copy(e, response);
                    return response;
                }).collect(Collectors.toList());

        ApiDataListApiResponse response = getSuccessResponse();
        response.setApiDataList(apiDataList);

        return response;
    }

    @Override
    protected ApiDataListApiResponse getResponse() {
        return new ApiDataListApiResponse();
    }
}
