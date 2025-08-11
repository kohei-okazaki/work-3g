package jp.co.ha.root.contents.inquiry.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.db.crud.read.InquiryManagementSelectService;
import jp.co.ha.business.exception.BusinessException;
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

    /** 問い合わせ管理情報検索サービス */
    @Autowired
    private InquiryManagementSelectService inquiryManagementSelectService;
    /** AWS-S3 Component */
    @Autowired
    private AwsS3Component s3;

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
        List<CompositeInquiry> list = inquiryManagementSelectService
                .findAll(selectOption);

        InquiryListApiResponse response = getSuccessResponse();

        List<InquiryListApiResponse.Inquiry> responseList = new ArrayList<>();

        for (CompositeInquiry entity : list) {
            InquiryListApiResponse.Inquiry inquiryResponse = new InquiryListApiResponse.Inquiry();

            inquiryResponse.setSeqInquriyMngId(entity.getSeqInquiryMngId());
            inquiryResponse.setSeqUserId(entity.getSeqUserId());
            inquiryResponse.setSeqLoginId(entity.getResponderLoginId());

            InquiryListApiResponse.Status status = new InquiryListApiResponse.Status();
            status.setLabel(entity.getInquiryStatusName());
            status.setValue(entity.getInquiryStatus());
            inquiryResponse.setStatus(status);

            InquiryListApiResponse.Type type = new InquiryListApiResponse.Type();
            type.setLabel(entity.getInquiryTypeName());
            type.setValue(entity.getInquiryType());
            inquiryResponse.setType(type);

            inquiryResponse.setTitle(entity.getTitle());
            inquiryResponse.setBody(getInquiryBody(entity));
            inquiryResponse.setRegDate(entity.getRegDate());
            inquiryResponse.setUpdateDate(entity.getUpdateDate());

            responseList.add(inquiryResponse);

        }
        response.setInquriyList(responseList);

        PagingView paging = PagingViewFactory.getPageView(pageable, "inquiry?page",
                inquiryManagementSelectService.count());
        response.setPaging(paging);

        return ResponseEntity.ok(response);
    }

    /**
     * S3の問い合わせ内容を返す
     *
     * @param entity
     *     問い合わせ情報
     * @return 問い合わせ内容
     * @throws BaseException
     *     S3からデータの取得に失敗した場合
     */
    private String getInquiryBody(CompositeInquiry entity) throws BaseException {

        try (InputStream is = s3.getS3ObjectByKey(entity.getS3Key());
                Reader r = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(r)) {
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();
        } catch (IOException e) {
            throw new BusinessException(e);
        }

    }

    @Override
    protected InquiryListApiResponse getResponse() {
        return new InquiryListApiResponse();
    }

}
