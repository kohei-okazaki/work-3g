package jp.co.ha.root.contents.healthinfo.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import jp.co.ha.common.util.PagingView;
import jp.co.ha.root.contents.healthinfo.response.HealthInfoListApiResponse;

/**
 * 健康情報サービスインターフェース
 * 
 * @version 1.0.0
 */
public interface HealthInfoService {

    /**
     * 健康情報リストを返す
     * 
     * @param pageable
     *     Pageable
     * @return 健康情報リスト
     */
    List<HealthInfoListApiResponse.HealthInfoResponse> getHealthInfoList(
            Pageable pageable);

    /**
     * PagingViewを返す
     * 
     * @param pageable
     *     Pageable
     * @return PagingView
     */
    PagingView getPagingView(Pageable pageable);

}
