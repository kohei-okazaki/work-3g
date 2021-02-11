package jp.co.ha.business.db.crud.create.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.create.RootUserRoleMngMtCreateService;
import jp.co.ha.common.db.annotation.Insert;
import jp.co.ha.db.entity.RootUserRoleMngMt;
import jp.co.ha.db.mapper.RootUserRoleMngMtMapper;

/**
 * 管理者サイトユーザ権限管理マスタ作成サービス実装クラス
 *
 * @version 1.0.0
 */
public class RootUserRoleMngMtCreateServiceImpl
        implements RootUserRoleMngMtCreateService {

    /** 管理者サイトユーザ権限管理マスタMapper */
    @Autowired
    private RootUserRoleMngMtMapper mapper;

    @Insert
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(RootUserRoleMngMt entity) {
        mapper.insert(entity);
    }

}
