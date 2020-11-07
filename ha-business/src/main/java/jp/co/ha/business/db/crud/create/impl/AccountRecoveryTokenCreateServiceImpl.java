package jp.co.ha.business.db.crud.create.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.create.AccountRecoveryTokenCreateService;
import jp.co.ha.common.db.annotation.Insert;
import jp.co.ha.db.entity.AccountRecoveryToken;
import jp.co.ha.db.mapper.AccountRecoveryTokenMapper;

/**
 * アカウント回復トークン作成サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class AccountRecoveryTokenCreateServiceImpl
        implements AccountRecoveryTokenCreateService {

    /** AccountRecoveryTokenMapper */
    @Autowired
    private AccountRecoveryTokenMapper mapper;

    @Insert
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(AccountRecoveryToken entity) {
        mapper.insert(entity);
    }

}
