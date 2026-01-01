package jp.co.ha.root.contents.api.service;

import static jp.co.ha.common.db.SelectOption.SortType.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.read.ApiLogSearchService;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.PagingView;
import jp.co.ha.common.util.PagingViewFactory;
import jp.co.ha.root.contents.api.response.ApiLogListApiResponse;
import jp.co.ha.root.contents.api.response.ApiLogListApiResponse.ApiLog;

/**
 * API通信ログサービス実装クラス
 * 
 * @version 1.0.0
 */
@Service
public class ApiLogServiceImpl implements ApiLogService {

    /** API通信ログ検索サービス */
    @Autowired
    private ApiLogSearchService searchService;

    @Override
    public List<ApiLog> getApiLogList(Pageable pageable) {

        SelectOption selectOption = new SelectOptionBuilder()
                .orderBy("SEQ_API_LOG_ID", DESC)
                .pageable(pageable)
                .build();

        List<ApiLogListApiResponse.ApiLog> apiLogList = searchService
                .findAll(selectOption)
                .stream()
                .map(e -> {
                    ApiLogListApiResponse.ApiLog response = new ApiLogListApiResponse.ApiLog();
                    BeanUtil.copy(e, response);
                    response.setHttpStatus(HttpStatus.valueOf(e.getHttpStatus()));
                    return response;
                }).collect(Collectors.toList());

        return apiLogList;
    }

    @Override
    public PagingView getPagingView(Pageable pageable) {
        return PagingViewFactory.getPageView(pageable, "apidata?page",
                searchService.countBySeqApiLogId(null));
    }

}
