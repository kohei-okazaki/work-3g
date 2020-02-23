package jp.co.ha.business.db.crud.read;

import java.util.List;

import jp.co.ha.db.entity.BmiRangeMt;

/**
 * BMI範囲マスタ検索サービスインターフェース
 *
 * @since 1.0
 */
public interface BmiRangeMtSearchService {

	/**
	 * BMI範囲IDからBMI範囲マスタを取得する
	 *
	 * @param bmiRangeId
	 *     BMI範囲ID
	 * @return BmiRangeMt
	 */
	BmiRangeMt findByBmiRangeId(Integer bmiRangeId);

	/**
	 * BMI範囲マスタを全件取得する
	 *
	 * @return BMI範囲マスタリスト
	 */
	List<BmiRangeMt> findAll();
}
