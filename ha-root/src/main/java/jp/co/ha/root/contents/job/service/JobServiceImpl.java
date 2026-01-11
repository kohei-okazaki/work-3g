package jp.co.ha.root.contents.job.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.read.BatchJobSearchService;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.PagingView;
import jp.co.ha.common.util.PagingViewFactory;
import jp.co.ha.db.entity.custom.CustomJobData;
import jp.co.ha.root.contents.job.response.JobListApiResponse.Job;
import jp.co.ha.root.contents.job.response.JobListApiResponse.JobParameter;

/**
 * Jobサービス実装クラス
 * 
 * @version 1.0.0
 */
@Service
public class JobServiceImpl implements JobService {

    /** Batch起動履歴検索サービス */
    @Autowired
    private BatchJobSearchService searchService;

    @Override
    public List<Job> getJobList(Pageable pageable) {

        SelectOption selectOption = new SelectOptionBuilder()
                .pageable(pageable)
                .build();

        Map<Long, Job> jobMap = new LinkedHashMap<>();

        for (CustomJobData jobEntity : searchService.findAll(selectOption)) {

            if (jobMap.containsKey(Long.valueOf(jobEntity.getJobInstanceId()))) {
                // 既知のjobInstanceIdの場合

                if (hasParam(jobEntity)) {
                    // 引数が指定されている場合
                    JobParameter jobParam = new JobParameter();
                    jobParam.setName(jobEntity.getParameterName());
                    jobParam.setValue(jobEntity.getParameterValue());
                    jobMap.get(Long.valueOf(jobEntity.getJobInstanceId()))
                            .getParameterList()
                            .add(jobParam);
                }

                continue;
            }

            Job job = new Job();
            job.setJobInstanceId(jobEntity.getJobInstanceId());
            job.setStartTime(jobEntity.getStartTime());
            job.setEndTime(jobEntity.getEndTime());
            job.setStatus(jobEntity.getStatus());
            job.setJobName(jobEntity.getJobName());

            if (hasParam(jobEntity)) {
                // バッチ引数が指定されている場合
                List<JobParameter> jobParamList = new ArrayList<>();
                JobParameter jobParam = new JobParameter();
                jobParam.setName(jobEntity.getParameterName());
                jobParam.setValue(jobEntity.getParameterValue());
                jobParamList.add(jobParam);
                job.setParameterList(jobParamList);
            }

            jobMap.put(Long.valueOf(jobEntity.getJobInstanceId()), job);
        }
        return jobMap.values().stream().toList();
    }

    @Override
    public PagingView getPagingView(Pageable pageable) {
        return PagingViewFactory.getPageView(pageable, "job?page", searchService.count());
    }

    /**
     * バッチ起動時の引数存在チェック
     * 
     * @param jobEntity
     *     バッチ起動履歴Entity
     * @return 引数指定ありの場合True。それ以外の場合False
     */
    private boolean hasParam(CustomJobData jobEntity) {
        return BeanUtil.notNull(jobEntity.getParameterName())
                || BeanUtil.notNull(jobEntity.getParameterValue());
    }

}
