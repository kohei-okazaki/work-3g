package jp.co.ha.business.db.crud.read.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.common.crypt.AesCrypter;
import jp.co.ha.common.crypt.Crypter;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.annotation.Select;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.entity.AccountExample;
import jp.co.ha.db.entity.AccountExample.Criteria;
import jp.co.ha.db.entity.AccountKey;
import jp.co.ha.db.entity.composite.CompositeAccount;
import jp.co.ha.db.entity.composite.CompositeAccountKey;
import jp.co.ha.db.entity.composite.CompositeMonthlyRegData;
import jp.co.ha.db.mapper.AccountMapper;
import jp.co.ha.db.mapper.composite.CompositeAccountMapper;
import jp.co.ha.db.mapper.composite.CompositeMonthlyMapper;

/**
 * アカウント情報検索サービスインターフェース実装クラス
 *
 * @version 1.0.0
 */
@Service
public class AccountSearchServiceImpl implements AccountSearchService {

    /** {@linkplain AesCrypter} */
    @Autowired
    @Qualifier("aesCrypter")
    private Crypter crypter;
    /** {@linkplain AccountMapper} */
    @Autowired
    private AccountMapper mapper;
    /** {@linkplain CompositeAccountMapper} */
    @Autowired
    private CompositeAccountMapper compositeAccountMapper;
    /** {@linkplain CompositeMonthlyMapper} */
    @Autowired
    private CompositeMonthlyMapper compositeMonthlyMapper;

    @Select
    @Override
    @Transactional(readOnly = true)
    public Optional<Account> findById(Long seqUserId) {
        AccountKey key = new AccountKey();
        key.setSeqUserId(seqUserId);
        return Optional.ofNullable(mapper.selectByPrimaryKey(key));
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public Optional<CompositeAccount> findCompositAccountById(Long seqUserId) {
        CompositeAccountKey key = new CompositeAccountKey();
        key.setSeqUserId(seqUserId);
        return Optional.ofNullable(compositeAccountMapper.selectByPrimaryKey(key));
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public Optional<Account> findByMailAddress(String mailAddress) {

        AccountExample example = new AccountExample();
        Criteria criteria = example.createCriteria();

        // メールアドレス
        criteria.andMailAddressEqualTo(crypter.encrypt(mailAddress));

        List<Account> list = mapper.selectByExample(example);

        // アカウント情報.メールアドレスは複数レコード存在する想定はないため、先頭1件を使用
        return Optional.ofNullable(CollectionUtil.isEmpty(list) ? null : list.get(0));
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public boolean isExistByMailAddress(String mailAddress) {

        AccountExample example = new AccountExample();
        Criteria criteria = example.createCriteria();

        // メールアドレス
        criteria.andMailAddressEqualTo(crypter.encrypt(mailAddress));

        long count = mapper.countByExample(example);
        return count > 0;
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public List<CompositeAccount> findAll(SelectOption selectOption) {

        AccountExample example = new AccountExample();
        example.setOrderByClause(selectOption.getOrderBy());

        return compositeAccountMapper.selectAll(example, selectOption.toRowBounds());
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public List<CompositeMonthlyRegData> findMonthly(LocalDateTime from,
            LocalDateTime to) {
        return compositeMonthlyMapper.selectAccountByRegDate(from, to);
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public long countBySeqUserId(Long seqUserId) {

        AccountExample example = new AccountExample();
        Criteria criteria = example.createCriteria();
        if (seqUserId != null) {
            // ユーザID
            criteria.andSeqUserIdEqualTo(seqUserId);
        }
        return mapper.countByExample(example);
    }

}
