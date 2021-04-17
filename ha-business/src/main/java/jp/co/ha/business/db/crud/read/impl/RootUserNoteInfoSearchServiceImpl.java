package jp.co.ha.business.db.crud.read.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.read.RootUserNoteInfoSearchService;
import jp.co.ha.common.db.annotation.Select;
import jp.co.ha.db.entity.RootUserNoteInfo;
import jp.co.ha.db.entity.RootUserNoteInfoExample;
import jp.co.ha.db.entity.RootUserNoteInfoExample.Criteria;
import jp.co.ha.db.entity.RootUserNoteInfoKey;
import jp.co.ha.db.mapper.RootUserNoteInfoMapper;

/**
 * 管理者サイトユーザメモ情報検索サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class RootUserNoteInfoSearchServiceImpl implements RootUserNoteInfoSearchService {

    /** 管理者サイトユーザメモ情報Mapper */
    @Autowired
    private RootUserNoteInfoMapper mapper;

    @Select
    @Override
    @Transactional(readOnly = true)
    public List<RootUserNoteInfo> findBySeqLoginId(Long seqLoginId) {

        RootUserNoteInfoExample example = new RootUserNoteInfoExample();
        Criteria criteria = example.createCriteria();

        // 管理者サイトログインID
        criteria.andSeqRootLoginInfoIdEqualTo(seqLoginId);

        return mapper.selectByExample(example);
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public RootUserNoteInfo findById(Long seqRootUserNoteInfoId) {

        RootUserNoteInfoKey key = new RootUserNoteInfoKey();
        key.setSeqRootUserNoteInfoId(seqRootUserNoteInfoId);
        return mapper.selectByPrimaryKey(key);
    }

}
