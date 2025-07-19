package jp.co.ha.business.db.crud.create.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.create.UserRecoveryTokenCreateService;
import jp.co.ha.common.db.annotation.Insert;
import jp.co.ha.db.entity.UserRecoveryToken;
import jp.co.ha.db.mapper.UserRecoveryTokenMapper;

/**
 * ユーザ回復トークン作成サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class UserRecoveryTokenCreateServiceImpl
        implements UserRecoveryTokenCreateService {

    /** {@linkplain UserRecoveryTokenMapper} */
    @Autowired
    private UserRecoveryTokenMapper mapper;

    @Insert
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(UserRecoveryToken entity) {
        mapper.insert(entity);
    }

}
