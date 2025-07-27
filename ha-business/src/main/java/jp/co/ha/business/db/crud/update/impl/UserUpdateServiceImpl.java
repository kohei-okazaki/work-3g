package jp.co.ha.business.db.crud.update.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.update.UserUpdateService;
import jp.co.ha.common.db.annotation.Update;
import jp.co.ha.db.entity.User;
import jp.co.ha.db.mapper.UserMapper;

/**
 * ユーザ情報更新サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class UserUpdateServiceImpl implements UserUpdateService {

    /** ユーザ情報Mapper */
    @Autowired
    private UserMapper mapper;

    @Update
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(User entity) {
        mapper.updateByPrimaryKey(entity);
    }

    @Update
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSelective(User entity) {
        mapper.updateByPrimaryKeySelective(entity);
    }

}
