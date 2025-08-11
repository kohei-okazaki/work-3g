package jp.co.ha.business.db.crud.read.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.read.UserSearchService;
import jp.co.ha.common.crypt.Crypter;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.annotation.Select;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.db.entity.User;
import jp.co.ha.db.entity.UserExample;
import jp.co.ha.db.entity.UserExample.Criteria;
import jp.co.ha.db.entity.UserKey;
import jp.co.ha.db.entity.composite.CompositeMonthlyRegData;
import jp.co.ha.db.entity.composite.CompositeUser;
import jp.co.ha.db.mapper.UserMapper;
import jp.co.ha.db.mapper.composite.CompositeMonthlyMapper;
import jp.co.ha.db.mapper.composite.CompositeUserMapper;

/**
 * ユーザ情報検索サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class UserSearchServiceImpl implements UserSearchService {

    /** AesCrypter */
    @Autowired
    @Qualifier("aesCrypter")
    private Crypter crypter;
    /** ユーザ情報Mapper */
    @Autowired
    private UserMapper mapper;
    /** ユーザ関連テーブル情報の複合Mapper */
    @Autowired
    private CompositeUserMapper compositeUserMapper;
    /** 月ごとの健康情報Mapper */
    @Autowired
    private CompositeMonthlyMapper compositeMonthlyMapper;

    @Select
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long seqUserId) {
        UserKey key = new UserKey();
        key.setSeqUserId(seqUserId);
        return Optional.ofNullable(mapper.selectByPrimaryKey(key));
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public Optional<CompositeUser> findCompositUserById(Long seqUserId) {
        UserKey key = new UserKey();
        key.setSeqUserId(seqUserId);
        return Optional.ofNullable(compositeUserMapper.selectByPrimaryKey(key));
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByMailAddress(String mailAddress) {

        UserExample example = new UserExample();
        Criteria criteria = example.createCriteria();

        // メールアドレス
        criteria.andMailAddressEqualTo(crypter.encrypt(mailAddress));
        // 削除フラグ
        criteria.andDeleteFlagEqualTo(false);

        List<User> list = mapper.selectByExample(example);

        // ユーザ情報.メールアドレスは複数レコード存在する想定はないため、先頭1件を使用
        return Optional.ofNullable(CollectionUtil.isEmpty(list) ? null : list.get(0));
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public boolean isExistByMailAddress(String mailAddress) {

        UserExample example = new UserExample();
        Criteria criteria = example.createCriteria();

        // メールアドレス
        criteria.andMailAddressEqualTo(crypter.encrypt(mailAddress));
        // 削除フラグ
        criteria.andDeleteFlagEqualTo(false);

        long count = mapper.countByExample(example);
        return count > 0;
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public List<CompositeUser> findAll(SelectOption selectOption) {

        UserExample example = new UserExample();
        example.setOrderByClause(selectOption.getOrderBy());

        return compositeUserMapper.selectAll(example, selectOption.toRowBounds());
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public List<CompositeMonthlyRegData> findMonthly(LocalDateTime from,
            LocalDateTime to) {
        return compositeMonthlyMapper.selectUserByRegDate(from, to);
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public long countBySeqUserId(Long seqUserId) {

        UserExample example = new UserExample();
        Criteria criteria = example.createCriteria();
        if (seqUserId != null) {
            // ユーザID
            criteria.andSeqUserIdEqualTo(seqUserId);
        }
        return mapper.countByExample(example);
    }

}
