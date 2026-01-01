package jp.co.ha.root.contents.api.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import jp.co.ha.common.util.PagingView;
import jp.co.ha.root.contents.api.response.ApiLogListApiResponse;

/**
 * API通信ログサービスインターフェース
 * 
 * @version 1.0.0
 */
public interface ApiLogService {

    /**
     * API通信ログリストを返す
     * 
     * @param pageable
     *     Pageable
     * @return API通信ログリスト
     */
    List<ApiLogListApiResponse.ApiLog> getApiLogList(Pageable pageable);

    /**
     * PagingViewを返す
     * 
     * @param pageable
     *     Pageable
     * @return PagingView
     */
    PagingView getPagingView(Pageable pageable);
}
