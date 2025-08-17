package jp.co.ha.business.db.crud.update.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.update.UserHealthGoalUpdateService;
import jp.co.ha.common.db.annotation.Update;
import jp.co.ha.db.entity.UserHealthGoal;
import jp.co.ha.db.mapper.UserHealthGoalMapper;

/**
 * ユーザ健康目標情報更新サービス実装クラス
 * 
 * @version 1.0.0
 */
@Service
public class UserHealthGoalUpdateServiceImpl implements UserHealthGoalUpdateService {

    /** ユーザ健康目標情報Mapper */
    @Autowired
    private UserHealthGoalMapper mapper;

    @Update
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDeleteFlag(UserHealthGoal entity) {
        entity.setDeleteFlag(true);
        mapper.updateByPrimaryKey(entity);
    }

}
