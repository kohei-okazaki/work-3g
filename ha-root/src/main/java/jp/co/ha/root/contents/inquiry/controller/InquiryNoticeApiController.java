package jp.co.ha.root.contents.inquiry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.inquiry.request.InquiryNoticeApiRequest;
import jp.co.ha.root.contents.inquiry.response.InquiryListNoticeResponse;
import jp.co.ha.root.contents.inquiry.service.InquiryService;

/**
 * 問い合わせ情報通知APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class InquiryNoticeApiController extends
        BaseRootApiController<InquiryNoticeApiRequest, InquiryListNoticeResponse> {

    /** 問い合わせサービス */
    @Autowired
    private InquiryService service;

    /**
     * 問い合わせ情報通知
     *
     * @param request
     *     問い合わせ情報通知APIリクエスト
     * @param status
     *     ステータス（未指定の場合、00：未対応）
     * @return 問い合わせ情報通知APIレスポンス
     */
    @GetMapping(value = "inquiry/notice")
    public ResponseEntity<InquiryListNoticeResponse> notice(
            InquiryNoticeApiRequest request,
            @RequestParam(name = "status", required = true, defaultValue = "00") String status) {

        InquiryListNoticeResponse response = getSuccessResponse();
        response.setCount(service.countByStatus(status));
        return ResponseEntity.ok(response);
    }

    @Override
    protected InquiryListNoticeResponse getResponse() {
        return new InquiryListNoticeResponse();
    }

}
