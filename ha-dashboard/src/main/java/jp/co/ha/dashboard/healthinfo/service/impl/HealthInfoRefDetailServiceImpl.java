package jp.co.ha.dashboard.healthinfo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.dto.HealthInfoRefDetailDto;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.dashboard.healthinfo.service.HealthInfoRefDetailService;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 健康情報詳細サービス実装クラス
 *
 * @since 1.0
 */
@Service
public class HealthInfoRefDetailServiceImpl implements HealthInfoRefDetailService {

	/** 健康情報検索サービス */
	@Autowired
	private HealthInfoSearchService healthInfoSearchService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfoRefDetailDto getHealthInfoRefDetailDto(HealthInfoRefDetailDto dto) {

		// 健康情報を検索
		List<HealthInfo> healthInfoList = healthInfoSearchService.findByHealthInfoIdAndUserId(dto.getHealthInfoId(),
				dto.getUserId());

		return healthInfoList.stream().map(e -> {

			HealthInfoRefDetailDto destDto = new HealthInfoRefDetailDto();
			BeanUtil.copy(dto, destDto);
			BeanUtil.copy(e, destDto);
			return destDto;
		}).collect(Collectors.toList()).get(0);
	}

}
