package jp.co.ha.business.db.crud.read;

import java.util.List;
import java.util.Optional;

import jp.co.ha.db.entity.BmiRangeMt;

/**
 * BMI範囲マスタ検索サービスインターフェース
 *
 * @version 1.0.0
 */
public interface BmiRangeMtSearchService {

    /**
     * BMI範囲マスタIDからBMI範囲マスタを検索する
     *
     * @param seqBmiRangeMtId
     *     BMI範囲ID
     * @return BmiRangeMt
     */
    Optional<BmiRangeMt> findById(Long seqBmiRangeMtId);

    /**
     * BMI範囲マスタを全件検索する
     *
     * @return BMI範囲マスタリスト
     */
    List<BmiRangeMt> findAll();

}
