package jp.co.ha.dashboard.service;

import jp.co.ha.business.dto.HealthInfoFileSettingDto;
import jp.co.ha.common.exception.BaseException;

/**
 * 健康情報ファイル設定サービスインターフェース
 * 
 * @since 1.0
 */
public interface HealthInfoFileSettingService {

	/**
	 * 健康情報ファイル設定のメイン処理を行う
	 *
	 * @param dto
	 *     健康情報ファイル設定DTO
	 * @throws BaseException
	 *     基底例外
	 */
	void execute(HealthInfoFileSettingDto dto) throws BaseException;
}
