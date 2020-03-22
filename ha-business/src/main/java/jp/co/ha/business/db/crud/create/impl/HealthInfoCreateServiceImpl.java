package jp.co.ha.business.db.crud.create.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.create.HealthInfoCreateService;
import jp.co.ha.common.db.annotation.Insert;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.mapper.HealthInfoMapper;

/**
 * 健康情報作成サービスインターフェース実装クラス
 *
 * @since 1.0
 */
@Service
public class HealthInfoCreateServiceImpl implements HealthInfoCreateService {

    /** HealthInfoMapper */
    @Autowired
    private HealthInfoMapper mapper;

    /**
     * {@inheritDoc}
     */
    @Insert
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(HealthInfo entity) {
        mapper.insert(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Insert
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<HealthInfo> entityList) {
        entityList.stream().forEach(e -> mapper.insert(e));
    }
}
