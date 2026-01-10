package jp.co.ha.root.contents.inquiry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.PagingViewFactory;
import jp.co.ha.common.validator.annotation.Decimal;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.inquiry.request.InquiryListApiRequest;
import jp.co.ha.root.contents.inquiry.response.InquiryListApiResponse;
import jp.co.ha.root.contents.inquiry.service.InquiryService;

/**
 * 問い合わせ情報一覧取得APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class InquiryListApiController
        extends BaseRootApiController<InquiryListApiRequest, InquiryListApiResponse> {

    /** 問い合わせサービス */
    @Autowired
    private InquiryService service;

    /**
     * 問い合わせ情報一覧取得
     *
     * @param request
     *     問い合わせ情報一覧取得APIリクエスト
     * @param page
     *     ページ
     * @return 問い合わせ情報一覧取得APIレスポンス
     * @throws BaseException
     *     レスポンスの生成に失敗した場合
     */
    @GetMapping(value = "inquiry")
    public ResponseEntity<InquiryListApiResponse> list(InquiryListApiRequest request,
            @RequestParam(name = "page", required = true, defaultValue = "0") @Decimal(min = "0", message = "page is positive") Integer page)
            throws BaseException {

        // ページング情報を取得(1ページあたりの表示件数はapplication-${env}.ymlより取得)
        Pageable pageable = PagingViewFactory.getPageable(page,
                applicationProperties.getInquiryPage());

        InquiryListApiResponse response = getSuccessResponse();
        response.setInquiryList(service.getInquiryList(pageable));
        response.setPaging(service.getPagingView(pageable));

        return ResponseEntity.ok(response);
    }

    @Override
    protected InquiryListApiResponse getResponse() {
        return new InquiryListApiResponse();
    }

}
