package jp.co.ha.business.db.crud.read;

import java.util.List;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.BmiRangeMt;

/**
 * BMI範囲マスタ検索サービスインターフェース
 *
 */
public interface BmiRangeMtSearchService {

	/**
	 * BMI範囲IDからBMI範囲マスタを取得する
	 *
	 * @param bmiRangeId
	 *     BMI範囲ID
	 * @return
	 * @throws BaseException
	 *     基底例外
	 */
	BmiRangeMt findByBmiRangeId(Integer bmiRangeId) throws BaseException;

	/**
	 * BMI範囲マスタを全件取得する
	 *
	 * @return BMI範囲マスタリスト
	 * @throws BaseException
	 *     基底例外
	 */
	List<BmiRangeMt> findAll() throws BaseException;
}
