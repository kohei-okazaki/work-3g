package jp.co.ha.business.db.crud.create.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.create.UserHealthGoalCreateService;
import jp.co.ha.common.db.annotation.Insert;
import jp.co.ha.db.entity.UserHealthGoal;
import jp.co.ha.db.mapper.UserHealthGoalMapper;

/**
 * ユーザ健康目標情報作成サービスインターフェース実装クラス
 *
 * @version 1.0.0
 */
@Service
public class UserHealthGoalCreateServiceImpl implements UserHealthGoalCreateService {

    /** ユーザ健康目標情報Mapper */
    @Autowired
    private UserHealthGoalMapper mapper;

    @Insert
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(UserHealthGoal entity) {
        mapper.insert(entity);
    }

}
