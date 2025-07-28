package jp.co.ha.business.db.crud.update.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.update.RootLoginInfoUpdateService;
import jp.co.ha.common.db.annotation.Update;
import jp.co.ha.db.entity.RootLoginInfo;
import jp.co.ha.db.entity.RootLoginInfoExample;
import jp.co.ha.db.entity.RootLoginInfoExample.Criteria;
import jp.co.ha.db.mapper.RootLoginInfoMapper;

/**
 * 管理者サイトユーザログイン情報更新サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class RootLoginInfoUpdateServiceImpl implements RootLoginInfoUpdateService {

    /** 管理者サイトユーザログイン情報Mapper */
    @Autowired
    private RootLoginInfoMapper mapper;

    @Update
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(RootLoginInfo entity) {
        RootLoginInfoExample example = new RootLoginInfoExample();
        Criteria criteria = example.createCriteria();

        // 管理者サイトログインID
        criteria.andSeqRootLoginInfoIdEqualTo(entity.getSeqRootLoginInfoId());

        mapper.updateByExampleSelective(entity, example);
    }

}
