package jp.co.ha.root.contents.tools.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.db.crud.read.InquiryStatusMtSearchService;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.db.SelectOption.SortType;
import jp.co.ha.db.entity.InquiryStatusMt;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.tools.request.InquiryStatusMtListApiRequest;
import jp.co.ha.root.contents.tools.response.InquiryStatusMtListApiResponse;

/**
 * 問い合わせステータスマスタリスト取得APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class InquiryStatusMtListApiController extends
        BaseRootApiController<InquiryStatusMtListApiRequest, InquiryStatusMtListApiResponse> {

    /** 問い合わせステータスマスタ検索サービス */
    @Autowired
    private InquiryStatusMtSearchService service;

    /**
     * 問い合わせステータスマスタリスト取得
     *
     * @param request
     *     問い合わせステータスマスタリスト取得APIリクエスト
     * @return 問い合わせステータスマスタリスト取得APIレスポンス
     */
    @GetMapping(value = "inquiry/status")
    public ResponseEntity<InquiryStatusMtListApiResponse> get(
            InquiryStatusMtListApiRequest request) {

        SelectOption selectOption = new SelectOptionBuilder()
                .orderBy("INQUIRY_STATUS", SortType.ASC)
                .build();

        List<InquiryStatusMt> list = service.findAll(selectOption);
        InquiryStatusMtListApiResponse response = getSuccessResponse();

        response.setStatuses(list.stream().map(e -> {
            InquiryStatusMtListApiResponse.Status status = new InquiryStatusMtListApiResponse.Status();
            status.setLabel(e.getName());
            status.setValue(e.getInquiryStatus());
            return status;
        }).collect(Collectors.toList()));

        return ResponseEntity.ok(response);

    }

    @Override
    protected InquiryStatusMtListApiResponse getResponse() {
        return new InquiryStatusMtListApiResponse();
    }

}
