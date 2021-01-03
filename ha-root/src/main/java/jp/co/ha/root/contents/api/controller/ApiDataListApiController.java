package jp.co.ha.root.contents.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.db.crud.read.ApiCommunicationDataSearchService;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.db.entity.ApiCommunicationData;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.api.request.ApiDataListApiRequest;
import jp.co.ha.root.contents.api.response.ApiDataListApiResponse;
import jp.co.ha.root.contents.api.response.ApiDataListApiResponse.ApiData;
import jp.co.ha.root.type.RootApiResult;

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
    private ApiCommunicationDataSearchService apiCommunicationDataSearchService;

    /**
     * 一覧取得
     *
     * @param request
     *     API通信情報一覧取得APIリクエスト
     * @return API通信情報一覧取得APIレスポンス
     */
    @GetMapping(value = "apidata", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ApiDataListApiResponse list(ApiDataListApiRequest request) {

        List<ApiData> apiDataList = new ArrayList<>();
        for (ApiCommunicationData entity : apiCommunicationDataSearchService.findAll()) {
            ApiData apiData = new ApiData();
            BeanUtil.copy(entity, apiData);
            apiDataList.add(apiData);
        }

        ApiDataListApiResponse response = new ApiDataListApiResponse();
        response.setRootApiResult(RootApiResult.SUCCESS);
        response.setApiDataList(apiDataList);

        return response;
    }
}
