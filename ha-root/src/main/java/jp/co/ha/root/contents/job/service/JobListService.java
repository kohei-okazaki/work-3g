package jp.co.ha.root.contents.job.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import jp.co.ha.common.util.PagingView;
import jp.co.ha.root.contents.job.response.JobListApiResponse.Job;

/**
 * Job履歴情報一覧取得APIサービスインターフェース
 * 
 * @version 1.0.0
 */
public interface JobListService {

    /**
     * Job履歴情報リストを返す
     * 
     * @param pageable
     *     Pageable
     * @return Job履歴情報リスト
     */
    List<Job> getJobList(Pageable pageable);

    /**
     * PagingViewを返す
     * 
     * @param pageable
     *     Pageable
     * @return PagingView
     */
    PagingView getPagingView(Pageable pageable);

}
