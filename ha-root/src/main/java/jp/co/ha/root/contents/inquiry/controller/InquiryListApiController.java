package jp.co.ha.root.contents.inquiry.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.component.InquiryComponent;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.db.SelectOption.SortType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.PagingView;
import jp.co.ha.common.util.PagingViewFactory;
import jp.co.ha.common.validator.annotation.Decimal;
import jp.co.ha.db.entity.composite.CompositeInquiry;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.inquiry.request.InquiryListApiRequest;
import jp.co.ha.root.contents.inquiry.response.InquiryListApiResponse;

/**
 * 問い合わせ情報一覧取得APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class InquiryListApiController
        extends BaseRootApiController<InquiryListApiRequest, InquiryListApiResponse> {

    /** 問い合わせ関連Component */
    @Autowired
    private InquiryComponent component;

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

        SelectOption selectOption = new SelectOptionBuilder()
                .orderBy("REG_DATE", SortType.ASC)
                .pageable(pageable)
                .build();
        List<CompositeInquiry> list = component.getInquiryList(selectOption);

        InquiryListApiResponse response = getSuccessResponse();

        List<InquiryListApiResponse.Inquiry> responseList = new ArrayList<>();

        for (CompositeInquiry entity : list) {
            InquiryListApiResponse.Inquiry inquiryResponse = new InquiryListApiResponse.Inquiry();

            // inquiryオブジェクト設定
            inquiryResponse.setSeqInquiryMngId(entity.getSeqInquiryMngId());
            inquiryResponse.setSeqUserId(entity.getSeqUserId());
            inquiryResponse.setSeqLoginId(entity.getResponderLoginId());
            inquiryResponse.setTitle(entity.getTitle());
            inquiryResponse.setBody(component.getInquiryBody(entity));
            inquiryResponse.setRegDate(entity.getRegDate());
            inquiryResponse.setUpdateDate(entity.getUpdateDate());

            // statusオブジェクト設定
            InquiryListApiResponse.Status status = new InquiryListApiResponse.Status();
            status.setLabel(entity.getInquiryStatusName());
            status.setValue(entity.getInquiryStatus());
            inquiryResponse.setStatus(status);

            // typeオブジェクト設定
            InquiryListApiResponse.Type type = new InquiryListApiResponse.Type();
            type.setLabel(entity.getInquiryTypeName());
            type.setValue(entity.getInquiryType());
            inquiryResponse.setType(type);

            responseList.add(inquiryResponse);
        }
        response.setInquiryList(responseList);

        PagingView paging = PagingViewFactory.getPageView(pageable, "inquiry?page",
                component.count());
        response.setPaging(paging);

        return ResponseEntity.ok(response);
    }

    @Override
    protected InquiryListApiResponse getResponse() {
        return new InquiryListApiResponse();
    }

}
