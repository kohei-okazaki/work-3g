package jp.co.ha.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.create.HealthInfoFileSettingCreateService;
import jp.co.ha.business.find.HealthInfoFileSettingSearchService;
import jp.co.ha.business.update.HealthInfoFileSettingUpdateService;
import jp.co.ha.common.entity.HealthInfoFileSetting;
import jp.co.ha.common.exception.BaseAppException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.web.form.HealthInfoFileSettingForm;
import jp.co.ha.web.service.HealthInfoFileSettingService;

/**
 * 健康情報ファイル設定サービス実装クラス<br>
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
	public void execute(HealthInfoFileSettingForm form) throws BaseAppException {
		HealthInfoFileSetting befEntity = searchService.findByUserId(form.getUserId());
		HealthInfoFileSetting entity = toEntity(form);
		if (BeanUtil.isNull(befEntity)) {
			createService.create(entity);
		} else {
			updateService.update(entity);
		}
	}

	/**
	 * フォーム情報をEntityクラスに変換する<br>
	 *
	 * @param form
	 *     健康情報ファイル設定フォーム
	 * @return
	 */
	private HealthInfoFileSetting toEntity(HealthInfoFileSettingForm form) {
		HealthInfoFileSetting entity = new HealthInfoFileSetting();
		BeanUtil.copy(form, entity);
		return entity;
	}

}
