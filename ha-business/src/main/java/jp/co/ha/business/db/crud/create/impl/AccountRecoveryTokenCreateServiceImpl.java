package jp.co.ha.business.db.crud.create.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.create.AccountRecoveryTokenCreateService;
import jp.co.ha.common.db.annotation.Insert;
import jp.co.ha.db.entity.AccountRecoveryTokenData;
import jp.co.ha.db.mapper.AccountRecoveryTokenDataMapper;

/**
 * アカウント回復トークン作成サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class AccountRecoveryTokenCreateServiceImpl
        implements AccountRecoveryTokenCreateService {

    /** AccountRecoveryTokenDataMapper */
    @Autowired
    private AccountRecoveryTokenDataMapper mapper;

    @Insert
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(AccountRecoveryTokenData entity) {
        mapper.insert(entity);
    }

}
