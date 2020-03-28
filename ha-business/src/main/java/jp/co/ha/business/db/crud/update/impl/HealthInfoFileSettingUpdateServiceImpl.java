package jp.co.ha.business.db.crud.update.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.update.HealthInfoFileSettingUpdateService;
import jp.co.ha.common.db.annotation.Update;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.db.mapper.HealthInfoFileSettingMapper;

/**
 * 健康情報ファイル設定更新サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class HealthInfoFileSettingUpdateServiceImpl
        implements HealthInfoFileSettingUpdateService {

    /** HealthInfoFileSettingMapper */
    @Autowired
    private HealthInfoFileSettingMapper mapper;

    /**
     * {@inheritDoc}
     */
    @Update
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(HealthInfoFileSetting entity) {
        mapper.updateByPrimaryKey(entity);
    }

}
