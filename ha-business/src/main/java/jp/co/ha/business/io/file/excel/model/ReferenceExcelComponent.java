package jp.co.ha.business.io.file.excel.model;

import java.util.List;

import jp.co.ha.business.dto.HealthInfoReferenceDto;
import jp.co.ha.common.io.file.excel.model.BaseExcelComponent;

/**
 * 健康情報照会ExcelComponent
 *
 * @version 1.0.0
 */
public class ReferenceExcelComponent implements BaseExcelComponent {

    /** ユーザID */
    private Long seqUserId;
    /** 結果リスト */
    private List<HealthInfoReferenceDto> resultList;

    /**
     * seqUserIdを返す
     *
     * @return seqUserId
     */
    public Long getSeqUserId() {
        return seqUserId;
    }

    /**
     * seqUserIdを設定する
     *
     * @param seqUserId
     *     ユーザID
     */
    public void setSeqUserId(Long seqUserId) {
        this.seqUserId = seqUserId;
    }

    /**
     * resultListを返す
     *
     * @return resultList
     */
    public List<HealthInfoReferenceDto> getResultList() {
        return resultList;
    }

    /**
     * resultListを設定する
     *
     * @param resultList
     *     結果リスト
     */
    public void setResultList(List<HealthInfoReferenceDto> resultList) {
        this.resultList = resultList;
    }

}
