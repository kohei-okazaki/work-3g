package jp.co.ha.business.db.crud.delete.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.delete.RootUserNoteInfoDeleteService;
import jp.co.ha.common.db.annotation.Delete;
import jp.co.ha.db.entity.RootUserNoteInfo;
import jp.co.ha.db.entity.RootUserNoteInfoKey;
import jp.co.ha.db.mapper.RootUserNoteInfoMapper;

/**
 * 管理者サイトユーザメモ情報削除サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class RootUserNoteInfoDeleteServiceImpl implements RootUserNoteInfoDeleteService {

    /** RootUserNoteInfoMapper */
    @Autowired
    private RootUserNoteInfoMapper mapper;

    @Delete
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long seqRootUserNoteInfoId) {

        RootUserNoteInfoKey key = new RootUserNoteInfo();
        key.setSeqRootUserNoteInfoId(seqRootUserNoteInfoId);
        mapper.deleteByPrimaryKey(key);
    }

}
