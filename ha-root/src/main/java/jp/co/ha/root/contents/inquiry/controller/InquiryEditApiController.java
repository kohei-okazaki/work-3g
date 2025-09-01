package jp.co.ha.root.contents.inquiry.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.component.InquiryComponent;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.inquiry.request.InquiryEditApiRequest;
import jp.co.ha.root.contents.inquiry.response.InquiryEditApiResponse;

/**
 * 問い合わせ情報編集APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class InquiryEditApiController
        extends BaseRootApiController<InquiryEditApiRequest, InquiryEditApiResponse> {

    /** 問い合わせ関連Component */
    @Autowired
    private InquiryComponent inquiryComponent;

    /**
     * 問い合わせ情報編集
     *
     * @param seqInquriyMngId
     *     問い合わせ情報管理ID
     * @param request
     *     問い合わせ情報編集APIリクエスト
     * @return 問い合わせ情報編集APIレスポンス
     * @throws BaseException
     *     API呼び出しに失敗した場合
     */
    @PutMapping(value = "inquiry/{seq_inquriy_mng_id}", produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<InquiryEditApiResponse> edit(
            @PathVariable(name = "seq_inquriy_mng_id", required = true) Long seqInquriyMngId,
            @Valid @RequestBody InquiryEditApiRequest request) throws BaseException {

        if (!inquiryComponent.isExistBySeqInquiryMngId(seqInquriyMngId)) {
            // 問い合わせ情報管理IDがDBに存在しない場合
            return ResponseEntity.badRequest()
                    .body(getErrorResponse(
                            "inquriy_management is not found. seq_inquriy_mng_id="
                                    + seqInquriyMngId));
        }

        // 問い合わせ管理情報更新
        inquiryComponent.updateStatusById(seqInquriyMngId,
                request.getStatus(), request.getSeqRootLoginInfoId());

        return ResponseEntity.ok(getSuccessResponse());
    }

    @Override
    protected InquiryEditApiResponse getResponse() {
        return new InquiryEditApiResponse();
    }

}
