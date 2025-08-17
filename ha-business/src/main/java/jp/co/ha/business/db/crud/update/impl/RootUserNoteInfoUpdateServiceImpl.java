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

    /** 管理者サイトユーザメモ情報Mapper */
    @Autowired
    private RootUserNoteInfoMapper mapper;

    @Update
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(RootUserNoteInfo entity) {
        mapper.updateByPrimaryKeySelective(entity);
    }

}
