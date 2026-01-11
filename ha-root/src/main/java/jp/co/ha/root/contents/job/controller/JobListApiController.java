package jp.co.ha.root.contents.job.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.common.util.PagingViewFactory;
import jp.co.ha.common.validator.annotation.Decimal;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.job.request.JobListApiRequest;
import jp.co.ha.root.contents.job.response.JobListApiResponse;
import jp.co.ha.root.contents.job.service.JobService;

/**
 * Job履歴情報一覧取得APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class JobListApiController
        extends BaseRootApiController<JobListApiRequest, JobListApiResponse> {

    /** Jobサービス */
    @Autowired
    private JobService jobService;

    /**
     * 一覧取得
     * 
     * @param request
     *     Job履歴情報一覧取得APIリクエスト
     * @param page
     *     取得対象ページ
     * @return Job履歴情報一覧取得APIレスポンス
     */
    @GetMapping(value = "job")
    public ResponseEntity<JobListApiResponse> list(JobListApiRequest request,
            @RequestParam(name = "page", required = true, defaultValue = "0") @Decimal(min = "0", message = "page is positive") Integer page) {

        // ページング情報を取得(1ページあたりの表示件数はapplication-${env}.ymlより取得)
        Pageable pageable = PagingViewFactory.getPageable(page,
                applicationProperties.getJobPage());

        JobListApiResponse response = getSuccessResponse();
        response.setJobList(jobService.getJobList(pageable));
        response.setPaging(jobService.getPagingView(pageable));

        return ResponseEntity.ok(response);
    }

    @Override
    protected JobListApiResponse getResponse() {
        return new JobListApiResponse();
    }

}
