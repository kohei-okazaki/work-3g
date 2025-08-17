package jp.co.ha.business.db.crud.create.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.create.RootUserRoleDetailMtCreateService;
import jp.co.ha.common.db.annotation.Insert;
import jp.co.ha.db.entity.RootUserRoleDetailMt;
import jp.co.ha.db.mapper.RootUserRoleDetailMtMapper;

/**
 * 管理者サイトユーザ権限詳細マスタ作成サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class RootUserRoleDetailMtCreateServiceImpl
        implements RootUserRoleDetailMtCreateService {

    /** 管理者サイトユーザ権限詳細マスタMapper */
    @Autowired
    private RootUserRoleDetailMtMapper mapper;

    @Insert
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(RootUserRoleDetailMt entity) {
        mapper.insert(entity);
    }
}
