package jp.co.ha.business.db.crud.update.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.update.RootUserNoteInfoUpdateService;
import jp.co.ha.common.db.annotation.Update;
import jp.co.ha.db.entity.RootUserNoteInfo;
import jp.co.ha.db.mapper.RootUserNoteInfoMapper;

/**
 * 管理者サイトユーザメモ情報更新サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class RootUserNoteInfoUpdateServiceImpl implements RootUserNoteInfoUpdateService {

    /** {@linkplain RootUserNoteInfoMapper} */
    @Autowired
    private RootUserNoteInfoMapper mapper;

    @Update
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long seqRootUserNoteInfoId, String title) {

        RootUserNoteInfo entity = new RootUserNoteInfo();
        entity.setSeqRootUserNoteInfoId(seqRootUserNoteInfoId);
        entity.setTitle(title);
        mapper.updateByPrimaryKeySelective(entity);
    }

}
