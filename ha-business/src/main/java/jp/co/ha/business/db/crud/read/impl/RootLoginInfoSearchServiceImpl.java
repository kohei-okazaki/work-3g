package jp.co.ha.business.db.crud.read.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.read.RootLoginInfoSearchService;
import jp.co.ha.db.entity.RootLoginInfo;
import jp.co.ha.db.entity.RootLoginInfoKey;
import jp.co.ha.db.mapper.RootLoginInfoMapper;

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

    @Override
    public Optional<RootLoginInfo> findById(Integer seqLoginId) {

        RootLoginInfoKey key = new RootLoginInfoKey();
        key.setSeqRootLoginInfoId(seqLoginId);
        RootLoginInfo entity = mapper.selectByPrimaryKey(key);

        return Optional.ofNullable(entity);
    }

}
