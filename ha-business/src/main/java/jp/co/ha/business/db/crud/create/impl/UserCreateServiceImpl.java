package jp.co.ha.business.db.crud.create.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.create.UserCreateService;
import jp.co.ha.common.db.annotation.Insert;
import jp.co.ha.db.entity.User;
import jp.co.ha.db.mapper.UserMapper;

/**
 * ユーザ情報作成サービスインターフェース実装クラス
 *
 * @version 1.0.0
 */
@Service
public class UserCreateServiceImpl implements UserCreateService {

    /** {@linkplain UserMapper} */
    @Autowired
    private UserMapper mapper;

    @Insert
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(User entity) {
        mapper.insert(entity);
    }

}
