package jp.co.ha.business.db.crud.read.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.common.db.annotation.Select;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.entity.AccountKey;
import jp.co.ha.db.entity.CompositAccount;
import jp.co.ha.db.mapper.AccountMapper;
import jp.co.ha.db.mapper.CompositAccountMapper;

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
    private CompositAccountMapper compositAccountMapper;

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
    public Optional<CompositAccount> findCompositAccountById(String userId) {
        return Optional.ofNullable(compositAccountMapper.selectByPrimaryKey(userId));
    }

}
