package jp.co.ha.root.contents.inquiry.service;

import static jp.co.ha.common.db.SelectOption.SortType.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jp.co.ha.business.component.InquiryComponent;
import jp.co.ha.business.component.InquiryComponent.Status;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.PagingView;
import jp.co.ha.common.util.PagingViewFactory;
import jp.co.ha.db.entity.custom.CompositeInquiry;
import jp.co.ha.root.contents.inquiry.request.InquiryEditApiRequest;
import jp.co.ha.root.contents.inquiry.response.InquiryListApiResponse;
import jp.co.ha.root.contents.inquiry.response.InquiryListApiResponse.Inquiry;

/**
 * 問い合わせサービス実装クラス
 * 
 * @version 1.0.0
 */
@Service
public class InquiryServiceImpl implements InquiryService {

    /** 問い合わせ関連Component */
    @Autowired
    private InquiryComponent component;

    @Override
    public List<Inquiry> getInquiryList(Pageable pageable) throws BaseException {

        SelectOption selectOption = new SelectOptionBuilder()
                .orderBy("REG_DATE", ASC)
                .pageable(pageable)
                .build();

        List<InquiryListApiResponse.Inquiry> list = new ArrayList<>();

        for (CompositeInquiry entity : component.getInquiryList(selectOption)) {
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

            list.add(inquiryResponse);
        }

        return list;
    }

    @Override
    public PagingView getPagingView(Pageable pageable) {
        return PagingViewFactory.getPageView(pageable, "inquiry?page", component.count());
    }

    @Override
    public void updateStatusById(Long seqInquriyMngId, InquiryEditApiRequest request) {
        component.updateStatusById(seqInquriyMngId, request.getStatus(),
                request.getSeqRootLoginInfoId());
    }

    @Override
    public boolean existInquiryManagement(Long seqInquriyMngId) {
        return component.isExistBySeqInquiryMngId(seqInquriyMngId);
    }

    @Override
    public long countByStatus(String status) {

        Status statusEnum = Status.of(status);
        if (statusEnum == null) {
            // ステータスが不正な場合、00：未対応を設定
            statusEnum = Status.NOT_STARTED;
        }

        return component.countByStatus(statusEnum);
    }

}
