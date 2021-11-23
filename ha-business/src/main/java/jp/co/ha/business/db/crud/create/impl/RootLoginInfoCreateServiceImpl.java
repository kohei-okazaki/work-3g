package jp.co.ha.business.db.crud.create.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.create.RootLoginInfoCreateService;
import jp.co.ha.common.db.annotation.Insert;
import jp.co.ha.db.entity.RootLoginInfo;
import jp.co.ha.db.mapper.RootLoginInfoMapper;

/**
 * 管理者サイトユーザログイン情報作成サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class RootLoginInfoCreateServiceImpl implements RootLoginInfoCreateService {

    /** {@linkplain RootLoginInfoMapper} */
    @Autowired
    private RootLoginInfoMapper mapper;

    @Insert
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(RootLoginInfo entity) {
        mapper.insert(entity);
    }

}
