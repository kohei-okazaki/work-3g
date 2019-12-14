package jp.co.ha.dashboard.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.update.HealthInfoFileSettingUpdateService;
import jp.co.ha.business.dto.HealthInfoFileSettingDto;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.dashboard.service.HealthInfoFileSettingService;
import jp.co.ha.db.entity.HealthInfoFileSetting;

/**
 * 健康情報ファイル設定サービス実装クラス
 *
 * @since 1.0
 */
@Service
public class HealthInfoFileSettingServiceImpl implements HealthInfoFileSettingService {

	/** 健康情報ファイル設定更新サービス */
	@Autowired
	private HealthInfoFileSettingUpdateService updateService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(HealthInfoFileSettingDto dto) throws BaseException {

		HealthInfoFileSetting entity = toEntity(dto);

		// 健康情報ファイル設定情報を更新
		updateService.update(entity);
	}

	/**
	 * 健康情報ファイル設定DTOを健康情報ファイル設定情報Entityに変換する
	 *
	 * @param dto
	 *     健康情報ファイル設定DTO
	 * @return HealthInfoFileSetting
	 */
	private HealthInfoFileSetting toEntity(HealthInfoFileSettingDto dto) {
		HealthInfoFileSetting entity = new HealthInfoFileSetting();
		BeanUtil.copy(dto, entity);
		return entity;
	}

}
