package jp.co.ha.root.contents.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.db.crud.read.ApiCommunicationDataSearchService;
import jp.co.ha.business.db.crud.read.impl.ApiCommunicationDataSearchServiceImpl;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.db.SelectOption.SortType;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.PagingView;
import jp.co.ha.common.util.PagingViewFactory;
import jp.co.ha.common.validator.annotation.Decimal;
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

    /** {@linkplain ApiCommunicationDataSearchServiceImpl} */
    @Autowired
    private ApiCommunicationDataSearchService searchService;

    /**
     * 一覧取得
     *
     * @param request
     *     API通信情報一覧取得APIリクエスト
     * @param page
     *     取得対象ページ
     * @return API通信情報一覧取得APIレスポンス
     */
    @GetMapping(value = "apidata", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ApiDataListApiResponse> list(ApiDataListApiRequest request,
            @RequestParam(name = "page", required = true, defaultValue = "0") @Decimal(min = "0", message = "page is positive") Integer page) {

        // ページング情報を取得(1ページあたりの表示件数はapplication-${env}.ymlより取得)
        Pageable pageable = PagingViewFactory.getPageable(page,
                applicationProperties.getApiPage());

        SelectOption selectOption = new SelectOptionBuilder()
                .orderBy("SEQ_API_COMMUNICATION_DATA_ID", SortType.DESC)
                .pageable(pageable)
                .build();

        List<ApiDataListApiResponse.ApiData> apiDataList = searchService
                .findAll(selectOption).stream()
                .map(e -> {
                    ApiDataListApiResponse.ApiData response = new ApiDataListApiResponse.ApiData();
                    BeanUtil.copy(e, response);
                    return response;
                }).collect(Collectors.toList());

        PagingView paging = PagingViewFactory.getPageView(pageable, "apidata?page",
                searchService.countBySeqApiCommunicationDataId(null));

        ApiDataListApiResponse response = getSuccessResponse();
        response.setApiDataList(apiDataList);
        response.setPaging(paging);

        return ResponseEntity.ok(response);
    }

    @Override
    protected ApiDataListApiResponse getResponse() {
        return new ApiDataListApiResponse();
    }
}
