package jp.co.ha.business.db.crud.read.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.read.RootLoginInfoSearchService;
import jp.co.ha.common.db.annotation.Select;
import jp.co.ha.db.entity.RootLoginInfo;
import jp.co.ha.db.entity.RootLoginInfoKey;
import jp.co.ha.db.entity.composite.CompositeRootUserInfo;
import jp.co.ha.db.mapper.RootLoginInfoMapper;
import jp.co.ha.db.mapper.composite.CompositeRootUserInfoMapper;

/**
 * 管理者サイトユーザログイン情報検索サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class RootLoginInfoSearchServiceImpl implements RootLoginInfoSearchService {

    /** 管理者サイトログイン情報Mapper */
    @Autowired
    private RootLoginInfoMapper mapper;
    /** 管理者サイト複合ユーザ情報Mapper */
    @Autowired
    private CompositeRootUserInfoMapper compositeMapper;

    @Select
    @Override
    @Transactional(readOnly = true)
    public Optional<RootLoginInfo> findById(Integer seqLoginId) {

        RootLoginInfoKey key = new RootLoginInfoKey();
        key.setSeqRootLoginInfoId(seqLoginId);
        RootLoginInfo entity = mapper.selectByPrimaryKey(key);

        return Optional.ofNullable(entity);
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public List<CompositeRootUserInfo> findCompositeUserById(Integer seqLoginId) {
        return compositeMapper.selectBySeqRootLoginInfoId(seqLoginId);
    }

}
