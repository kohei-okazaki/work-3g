package jp.co.ha.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.create.HealthInfoFileSettingCreateService;
import jp.co.ha.business.db.crud.read.HealthInfoFileSettingSearchService;
import jp.co.ha.business.db.crud.update.HealthInfoFileSettingUpdateService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.web.form.HealthInfoFileSettingForm;
import jp.co.ha.web.service.HealthInfoFileSettingService;

/**
 * 健康情報ファイル設定サービス実装クラス
 *
 */
@Service
public class HealthInfoFileSettingServiceImpl implements HealthInfoFileSettingService {

	/** 健康情報ファイル設定登録サービス */
	@Autowired
	private HealthInfoFileSettingCreateService createService;
	/** 健康情報ファイル設定更新サービス */
	@Autowired
	private HealthInfoFileSettingUpdateService updateService;
	/** 健康情報ファイル設定検索サービス */
	@Autowired
	private HealthInfoFileSettingSearchService searchService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(HealthInfoFileSettingForm form) throws BaseException {
		HealthInfoFileSetting befEntity = searchService.findByUserId(form.getUserId());
		boolean isFirstReg = BeanUtil.isNull(befEntity);
		HealthInfoFileSetting entity = toEntity(form);
		if (isFirstReg) {
			createService.create(entity);
		} else {
			updateService.update(entity);
		}
	}

	/**
	 * フォーム情報を健康情報ファイル設定情報Entityに変換する
	 *
	 * @param form
	 *     健康情報ファイル設定フォーム
	 * @return HealthInfoFileSetting
	 */
	private HealthInfoFileSetting toEntity(HealthInfoFileSettingForm form) {
		HealthInfoFileSetting entity = new HealthInfoFileSetting();
		BeanUtil.copy(form, entity);
		return entity;
	}

}
