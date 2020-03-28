package jp.co.ha.business.db.crud.update.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.update.AccountUpdateService;
import jp.co.ha.common.db.annotation.Update;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.mapper.AccountMapper;

/**
 * アカウント情報更新サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class AccountUpdateServiceImpl implements AccountUpdateService {

    /** AccountMapper */
    @Autowired
    private AccountMapper mapper;

    /**
     * {@inheritDoc}
     */
    @Update
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Account entity) {
        mapper.updateByPrimaryKey(entity);
    }

}
