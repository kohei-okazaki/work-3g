package jp.co.ha.business.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.db.crud.read.InquiryManagementSelectService;
import jp.co.ha.business.db.crud.update.InquiryManagementUpdateService;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.type.BaseEnum;
import jp.co.ha.db.entity.InquiryManagement;
import jp.co.ha.db.entity.composite.CompositeInquiry;

/**
 * 問い合わせ関連のComponent
 * 
 * @version 1.0.0
 */
@Component
public class InquiryComponent {

    /** 問い合わせ管理情報検索サービス */
    @Autowired
    private InquiryManagementSelectService selectService;
    /** 問い合わせ管理情報更新サービス */
    @Autowired
    private InquiryManagementUpdateService updateService;
    /** AWS-S3 Component */
    @Autowired
    private AwsS3Component s3;

    /**
     * 問い合わせ情報一覧を取得する
     * 
     * @param selectOption
     *     SelectOption
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
