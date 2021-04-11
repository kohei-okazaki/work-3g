package jp.co.ha.business.db.crud.create.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.create.RootUserNoteInfoCreateService;
import jp.co.ha.common.db.annotation.Insert;
import jp.co.ha.db.entity.RootUserNoteInfo;
import jp.co.ha.db.mapper.RootUserNoteInfoMapper;

/**
 * 管理者サイトユーザメモ情報登録サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class RootUserNoteInfoCreateServiceImpl implements RootUserNoteInfoCreateService {

    /** 管理者サイトユーザメモ情報Mapper */
    @Autowired
    private RootUserNoteInfoMapper mapper;

    @Insert
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(RootUserNoteInfo entity) {
        mapper.insert(entity);
    }

}
