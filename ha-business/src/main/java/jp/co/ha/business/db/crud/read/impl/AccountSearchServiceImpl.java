package jp.co.ha.business.db.crud.read.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.common.db.annotation.Select;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.entity.AccountKey;
import jp.co.ha.db.entity.composite.CompositeAccount;
import jp.co.ha.db.entity.composite.CompositeAccountKey;
import jp.co.ha.db.mapper.AccountMapper;
import jp.co.ha.db.mapper.composite.CompositAccounteMapper;

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
    /** アカウント複合Mapper */
    @Autowired
    private CompositAccounteMapper compositAccountMapper;

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

    /**
     * {@inheritDoc}
     */
    @Select
    @Override
    @Transactional(readOnly = true)
    public Optional<CompositeAccount> findCompositAccountById(String userId) {
        CompositeAccountKey key = new CompositeAccountKey();
        key.setUserId(userId);
        return Optional.ofNullable(compositAccountMapper.selectByPrimaryKey(key));
    }

}
