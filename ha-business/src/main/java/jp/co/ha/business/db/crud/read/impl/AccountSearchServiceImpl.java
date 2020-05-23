package jp.co.ha.business.db.crud.read.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.common.db.annotation.Select;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.entity.AccountKey;
import jp.co.ha.db.mapper.AccountMapper;

/**
 * アカウント情報検索サービスインターフェース実装クラス
 *
 * @version 1.0.0
 */
@Service
public class AccountSearchServiceImpl implements AccountSearchService {

    /** AccountMapper */
    @Autowired
    private AccountMapper mapper;

    /**
     * {@inheritDoc}
     */
    @Select
    @Override
    @Transactional(readOnly = true)
    public Optional<Account> findById(String userId) {
        AccountKey key = new AccountKey();
        key.setUserId(userId);
        return Optional.ofNullable(mapper.selectByPrimaryKey(key));
    }

}
