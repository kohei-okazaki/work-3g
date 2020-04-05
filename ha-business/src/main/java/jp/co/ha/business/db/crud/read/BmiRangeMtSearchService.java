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
     * BMI範囲IDからBMI範囲マスタを取得する
     *
     * @param seqBmiRangeId
     *     BMI範囲ID
     * @return BmiRangeMt
     */
    Optional<BmiRangeMt> findById(Integer seqBmiRangeId);

    /**
     * BMI範囲マスタを全件取得する
     *
     * @return BMI範囲マスタリスト
     */
    List<BmiRangeMt> findAll();

}
