package jp.co.ha.business.db.crud.create.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.create.AccountCreateService;
import jp.co.ha.common.db.annotation.Insert;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.mapper.AccountMapper;

/**
 * アカウント情報作成サービスインターフェース実装クラス
 *
 * @version 1.0.0
 */
@Service
public class AccountCreateServiceImpl implements AccountCreateService {

    /** {@linkplain AccountMapper} */
    @Autowired
    private AccountMapper mapper;

    @Insert
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Account entity) {
        mapper.insert(entity);
    }

}
