package jp.co.ha.business.db.crud.delete.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.delete.RootUserRoleDetailMtDeleteService;
import jp.co.ha.common.db.annotation.Delete;
import jp.co.ha.db.entity.RootUserRoleDetailMtExample;
import jp.co.ha.db.entity.RootUserRoleDetailMtExample.Criteria;
import jp.co.ha.db.mapper.RootUserRoleDetailMtMapper;

/**
 * 管理者サイトユーザ権限詳細マスタ削除サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class RootUserRoleDetailMtDeleteServiceImpl
        implements RootUserRoleDetailMtDeleteService {

    /** 管理者サイトユーザ権限詳細マスタMapper */
    @Autowired
    private RootUserRoleDetailMtMapper mapper;

    @Delete
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByUser(Integer seqRootUserRoleMngMtId) {

        RootUserRoleDetailMtExample example = new RootUserRoleDetailMtExample();
        Criteria criteria = example.createCriteria();

        // 管理者サイトユーザ権限管理マスタID
        criteria.andSeqRootUserRoleMngMtIdEqualTo(seqRootUserRoleMngMtId);

        mapper.deleteByExample(example);
    }

}
