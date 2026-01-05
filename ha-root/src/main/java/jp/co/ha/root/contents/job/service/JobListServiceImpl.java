package jp.co.ha.root.contents.job.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.read.BatchJobSearchService;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.PagingView;
import jp.co.ha.common.util.PagingViewFactory;
import jp.co.ha.root.contents.job.response.JobListApiResponse.Job;

/**
 * Job履歴情報一覧取得APIサービス実装クラス
 * 
 * @version 1.0.0
 */
@Service
public class JobListServiceImpl implements JobListService {

    /** API通信ログ検索サービス */
    @Autowired
    private BatchJobSearchService searchService;

    @Override
    public List<Job> getJobList(Pageable pageable) {

        SelectOption selectOption = new SelectOptionBuilder()
                .pageable(pageable)
                .build();

        // TODO 返却値は要修正（パラメータは更にリスト化）
        return searchService.findAll(selectOption).stream().map(e -> {
            Job job = new Job();
            BeanUtil.copy(e, job);
            return job;
        }).collect(Collectors.toList());
    }

    @Override
    public PagingView getPagingView(Pageable pageable) {
        return PagingViewFactory.getPageView(pageable, "job?page", 0);
    }

}
