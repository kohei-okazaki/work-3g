package jp.co.ha.root.contents.inquiry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.component.InquiryComponent;
import jp.co.ha.business.component.InquiryComponent.Status;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.inquiry.request.InquiryNoticeApiRequest;
import jp.co.ha.root.contents.inquiry.response.InquiryListNoticeResponse;

/**
 * 問い合わせ情報通知APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class InquiryNoticeApiController extends
        BaseRootApiController<InquiryNoticeApiRequest, InquiryListNoticeResponse> {

    /** 問い合わせ関連Component */
    @Autowired
    private InquiryComponent component;

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

        Status statusEnum = getStatus(status);

        InquiryListNoticeResponse response = getSuccessResponse();
        response.setCount(component.countByStatus(statusEnum));
        return ResponseEntity.ok(response);
    }

    @Override
    protected InquiryListNoticeResponse getResponse() {
        return new InquiryListNoticeResponse();
    }

    /**
     * 文字列形式をステータスの列挙型に変換
     *
     * @param status
     *     ステータス
     * @return ステータスの列挙型
     */
    private Status getStatus(String status) {
        Status statusEnum = Status.of(status);
        if (statusEnum == null) {
            // ステータスが不正な場合、00：未対応を設定
            statusEnum = Status.NOT_STARTED;
        }
        return statusEnum;
    }

}
