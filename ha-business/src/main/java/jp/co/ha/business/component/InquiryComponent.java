package jp.co.ha.business.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.api.aws.AwsSesComponent;
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.db.crud.create.InquiryManagementCreateService;
import jp.co.ha.business.db.crud.read.InquiryManagementSelectService;
import jp.co.ha.business.db.crud.read.InquiryTypeMtSearchService;
import jp.co.ha.business.db.crud.update.InquiryManagementUpdateService;
import jp.co.ha.business.dto.InquiryDto;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.db.SelectOption.SortType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.system.SystemProperties;
import jp.co.ha.common.type.BaseEnum;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.db.entity.InquiryManagement;
import jp.co.ha.db.entity.InquiryTypeMt;
import jp.co.ha.db.entity.composite.CompositeInquiry;

/**
 * 問い合わせ関連のComponent
 * 
 * @version 1.0.0
 */
@Component
public class InquiryComponent {

    /** 問い合わせ検索用のSelectOption */
    private static final SelectOption SELECT_OPTION = new SelectOptionBuilder()
            .orderBy("DISP_ORDER", SortType.ASC)
            .build();
    /** Slack件名： */
    private static final String SLACK_TITLE_REGIST = "問い合わせ登録";
    /** Slack件名： */
    private static final String SLACK_TITLE_OCCURED = "inquiry/";

    /** 問い合わせ種別マスタ検索サービス */
    @Autowired
    private InquiryTypeMtSearchService inquiryTypeMtSearchService;
    /** 問い合わせ管理情報作成サービス */
    @Autowired
    private InquiryManagementCreateService createService;
    /** 問い合わせ管理情報検索サービス */
    @Autowired
    private InquiryManagementSelectService selectService;
    /** 問い合わせ管理情報更新サービス */
    @Autowired
    private InquiryManagementUpdateService updateService;
    /** AWS-S3 Component */
    @Autowired
    private AwsS3Component s3;
    /** AWS-SES Component */
    @Autowired
    private AwsSesComponent ses;
    /** Slack Component */
    @Autowired
    private SlackApiComponent slack;
    /** システムプロパティ */
    @Autowired
    private SystemProperties systemProps;

    /**
     * 問い合わせ種別リストを返す
     * 
     * @return 問い合わせ種別リスト
     */
    public List<InquiryTypeMt> getInquiryTypeMtList() {
        return inquiryTypeMtSearchService.findAll(SELECT_OPTION);
    }

    /**
     * 問い合わせ管理情報を登録する
     * 
     * @param dto
     *     問い合わせDto
     * @throws BaseException
     *     問い合わせ情報の登録に失敗した場合
     */
    public void regist(InquiryDto dto) throws BaseException {

        // S3登録
        String s3Key = new StringJoiner(StringUtil.THRASH)
                .add(AwsS3Component.AwsS3Key.INQUIRY.getValue())
                .add(String.valueOf(dto.getSeqUserId()))
                .add(DateTimeUtil.toString(DateTimeUtil.getSysDate(),
                        DateFormatType.YYYYMMDDHHMMSS_NOSEP))
                .toString();
        s3.putFile(s3Key, dto.getBody());

        // DB登録
        InquiryManagement entity = new InquiryManagement();
        entity.setSeqUserId(dto.getSeqUserId());
        entity.setS3Key(s3Key);
        entity.setInquiryStatus(Status.NOT_STARTED.getValue());
        entity.setInquiryType(dto.getType());
        entity.setTitle(dto.getTitle());
        entity.setDeleteFlag(false);
        createService.create(entity);

        // Slack通知
        slack.sendFile(SlackApiComponent.ContentType.DASHBOARD, dto.getBody().getBytes(),
                s3Key, SLACK_TITLE_REGIST,
                "問い合わせユーザID=" + dto.getSeqUserId() + ", S3キー=" + s3Key + "を登録.");
    }

    /**
     * 問い合わせ完了メールを送信する
     * 
     * @throws BaseException
     *     メール送信に失敗した場合
     */
    public void sendMail() throws BaseException {
        ses.sendMail(systemProps.getSystemMailAddress(), SLACK_TITLE_OCCURED,
                AwsSesComponent.MailTemplateKey.INQUIRY_REGIST_TEMPLATE);
    }

    /**
     * 問い合わせ情報一覧を取得する
     * 
     * @param selectOption
     *     検索オプション
     * @return 問い合わせ情報一覧
     */
    public List<CompositeInquiry> getInquiryList(SelectOption selectOption) {
        return selectService.findAll(selectOption);
    }

    /**
     * 問い合わせ情報の総件数を取得する
     * 
     * @return 総件数
     */
    public long count() {
        return selectService.count();
    }

    /**
     * 指定した問い合わせ管理IDが存在するか判定する
     * 
     * @param seqInquiryMngId
     *     問い合わせ管理ID
     * @return 判定結果
     */
    public boolean isExistBySeqInquiryMngId(Long seqInquiryMngId) {
        return selectService.isExistBySeqInquiryMngId(seqInquiryMngId);
    }

    /**
     * 問い合わせ管理情報のステータスを更新する
     * 
     * @param seqInquriyMngId
     *     問い合わせ情報管理ID
     * @param status
     *     問い合わせステータス
     * @param seqRootLoginInfoId
     *     管理者サイトログイン情報ID
     */
    public void updateStatusById(Long seqInquriyMngId, Status status,
            Long seqRootLoginInfoId) {
        InquiryManagement entity = new InquiryManagement();
        entity.setSeqInquiryMngId(seqInquriyMngId);
        entity.setInquiryStatus(status.getValue());
        entity.setResponderLoginId(seqRootLoginInfoId);
        updateService.updateStatusById(entity);
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
    public String getInquiryBody(CompositeInquiry entity) throws BaseException {

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

    /**
     * 指定した問い合わせステータスの件数を取得
     * 
     * @param statuses
     *     問い合わせステータス
     * @return 件数
     */
    public long countByStatus(Status... statuses) {
        return selectService.countByStatusList(statuses);
    }

    /**
     * 問い合わせステータスの列挙体
     * 
     * @version 1.0.0
     */
    @JsonDeserialize(using = InquiryStatusDeserializer.class)
    public static enum Status implements BaseEnum {

        /** 00：未対応 */
        NOT_STARTED("00"),
        /** 01：対応中 */
        IN_PROGRESS("01"),
        /** 02：完了 */
        COMPLETED("02"),;

        /**
         * コンストラクタ
         * 
         * @param value
         *     値
         */
        private Status(String value) {
            this.value = value;
        }

        /** 値 */
        private String value;

        @Override
        public String getValue() {
            return this.value;
        }

        /**
         * @param value
         *     値
         * @return Status
         * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
         */
        public static Status of(String value) {
            return BaseEnum.of(Status.class, value);
        }
    }

    /**
     * JSONの問い合わせステータスのデシリアライズクラス<br>
     * 文字列型のJSONをJavaのクラスに変換する
     *
     * @version 1.0.0
     */
    public static class InquiryStatusDeserializer extends JsonDeserializer<Status> {

        @Override
        public Status deserialize(JsonParser parser, DeserializationContext ctxt)
                throws IOException, JacksonException {
            return Status.of(parser.getValueAsString());
        }

    }

    /**
     * 問い合わせ種別の列挙体
     * 
     * @version 1.0.0
     */
    public static enum Type implements BaseEnum {

        /** 00：未対応 */
        NOT_STARTED("00"),
        /** 01：対応中 */
        IN_PROGRESS("01"),
        /** 02：完了 */
        COMPLETED("02"),;

        /**
         * コンストラクタ
         * 
         * @param value
         *     値
         */
        private Type(String value) {
            this.value = value;
        }

        /** 値 */
        private String value;

        @Override
        public String getValue() {
            return this.value;
        }

        /**
         * @param value
         *     値
         * @return Type
         * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
         */
        public static Type of(String value) {
            return BaseEnum.of(Type.class, value);
        }

    }
}
