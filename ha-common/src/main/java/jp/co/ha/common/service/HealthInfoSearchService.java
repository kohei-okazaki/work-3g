package jp.co.ha.common.service;

import java.util.List;

import jp.co.ha.common.entity.HealthInfo;

/**
 * 健康情報検索サービス<br>
 *
 */
public interface HealthInfoSearchService {

	/**
	 * 指定されたユーザIDと一致する健康情報のリストを返却する<br>
	 * @param userId
	 * @return List<HealthInfoDto>
	 */
	List<HealthInfo> findHealthInfoByUserId(String userId);

	/**
	 * 指定されたデータIDからと一致する健康情報を返却する<br>
	 * @param dataId
	 * @return HealthInfoDto
	 */
	HealthInfo findHealthInfoByDataId(String dataId);

	/**
	 * 指定したユーザIDで最後に登録した健康情報を返す<br>
	 * @param userId
	 * @return HealthInfo
	 */
	HealthInfo findLastHealthInfoByUserId(String userId);
}
