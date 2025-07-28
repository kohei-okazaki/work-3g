package jp.co.ha.business.db.crud.update.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.update.HealthInfoUpdateService;
import jp.co.ha.common.db.annotation.Update;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.entity.HealthInfoExample;
import jp.co.ha.db.entity.HealthInfoExample.Criteria;
import jp.co.ha.db.mapper.HealthInfoMapper;

/**
 * 健康情報更新サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class HealthInfoUpdateServiceImpl implements HealthInfoUpdateService {

    /** 健康情報Mapper */
    @Autowired
    private HealthInfoMapper mapper;

    @Update
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(HealthInfo entity) {
        HealthInfoExample example = new HealthInfoExample();
        Criteria criteria = example.createCriteria();

        // 健康情報ID
        criteria.andSeqHealthInfoIdEqualTo(entity.getSeqHealthInfoId());

        mapper.updateByExampleSelective(entity, example);
    }

}
