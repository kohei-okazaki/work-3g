package jp.co.ha.business.db.crud.delete.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.delete.HealthInfoFileSettingDeleteService;
import jp.co.ha.common.db.annotation.Delete;
import jp.co.ha.db.mapper.HealthInfoFileSettingMapper;

/**
 * 健康情報ファイル設定削除サービスインターフェース実装クラス
 *
 * @version 1.0.0
 */
@Service
public class HealthInfoFileSettingDeleteServiceImpl
        implements HealthInfoFileSettingDeleteService {

    /** HealthInfoFileSettingMapper */
    @Autowired
    private HealthInfoFileSettingMapper mapper;

    /**
     * {@inheritDoc}
     */
    @Delete
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByUserId(String userId) {
        mapper.deleteByPrimaryKey(userId);
    }
}
