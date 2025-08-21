package jp.co.ha.dashboard.inquiry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.api.aws.AwsS3Component.AwsS3Key;
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.business.component.InquiryComponent.Status;
import jp.co.ha.business.db.crud.create.InquiryManagementCreateService;
import jp.co.ha.business.db.crud.read.InquiryTypeMtSearchService;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.db.SelectOption.SortType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.dashboard.inquiry.form.InquiryForm;
import jp.co.ha.dashboard.inquiry.service.InquiryService;
import jp.co.ha.db.entity.InquiryManagement;
import jp.co.ha.db.entity.InquiryTypeMt;

/**
 * 問い合わせ情報サービス実装クラス
 * 
 * @version 1.0.0
 */
@Service
public class InquiryServiceImpl implements InquiryService {

    /** 問い合わせ検索用のSelectOption */
    private static final SelectOption SELECT_OPTION = new SelectOptionBuilder()
            .orderBy("DISP_ORDER", SortType.ASC)
            .build();

    /** 問い合わせ種別マスタ検索サービス */
    @Autowired
    private InquiryTypeMtSearchService inquiryTypeMtSearchService;
    /** 問い合わせ管理情報作成サービス */
    @Autowired
    private InquiryManagementCreateService inquiryManagementCreateService;
    /** AWS-S3 Component */
    @Autowired
    private AwsS3Component s3;
    /** Slack Component */
    @Autowired
    private SlackApiComponent slack;

    @Override
    public List<InquiryTypeMt> getInquiryTypeMtList() {
        return inquiryTypeMtSearchService.findAll(SELECT_OPTION);
    }

    @Override
    public void regist(Long seqUserId, InquiryForm inquiryForm) throws BaseException {

        // S3登録
        String sysdate = DateTimeUtil.toString(DateTimeUtil.getSysDate(),
                DateFormatType.YYYYMMDDHHMMSS_NOSEP);
        String s3Key = AwsS3Key.INQUIRY.getValue() + seqUserId + "/" + sysdate;
        s3.putFile(s3Key, inquiryForm.getBody());

        // DB登録
        InquiryManagement entity = new InquiryManagement();
        entity.setSeqUserId(seqUserId);
        entity.setS3Key(s3Key);
        entity.setInquiryStatus(Status.NOT_STARTED.getValue());
        entity.setInquiryType(inquiryForm.getType());
        entity.setTitle(inquiryForm.getTitle());
        entity.setDeleteFlag(false);
        inquiryManagementCreateService.create(entity);

        // Slack通知
        slack.send(ContentType.DASHBOARD,
                "問い合わせユーザID=" + seqUserId + "問い合わせ内容=" + inquiryForm.getBody());

    }

}
