package jp.co.ha.business.db.crud.create;

import java.util.List;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 健康情報作成サービスインターフェース
 *
 */
public interface HealthInfoCreateService {

	/**
	 * 指定した健康情報を登録する
	 *
	 * @param entity
	 *     健康情報
	 * @throws BaseException
	 *     基底例外
	 */
	void create(HealthInfo entity) throws BaseException;

	/**
	 * 指定した健康情報リストを登録する
	 *
	 * @param entityList
	 *     健康情報リスト
	 * @throws BaseException
	 *     基底例外
	 */
	void create(List<HealthInfo> entityList) throws BaseException;
}
