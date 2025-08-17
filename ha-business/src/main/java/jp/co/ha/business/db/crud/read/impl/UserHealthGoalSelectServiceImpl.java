package jp.co.ha.business.db.crud.read.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.read.UserHealthGoalSelectService;
import jp.co.ha.common.db.annotation.Select;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.db.entity.UserHealthGoal;
import jp.co.ha.db.entity.UserHealthGoalExample;
import jp.co.ha.db.mapper.UserHealthGoalMapper;

/**
 * ユーザ健康目標情報サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class UserHealthGoalSelectServiceImpl implements UserHealthGoalSelectService {

    /** ユーザ健康目標情報Mapper */
    @Autowired
    private UserHealthGoalMapper mapper;

    @Select
    @Override
    @Transactional(readOnly = true)
    public Optional<UserHealthGoal> findEnableById(Long seqUserId) {

        UserHealthGoalExample example = new UserHealthGoalExample();
        UserHealthGoalExample.Criteria criteria = example.createCriteria();
        criteria.andSeqUserIdEqualTo(seqUserId);
        criteria.andDeleteFlagEqualTo(false);

        List<UserHealthGoal> entityList = mapper.selectByExample(example);
        if (CollectionUtil.isEmpty(entityList)) {
            return Optional.empty();
        } else {
            // 必ず複数レコード存在しないため、1件目を取得
            return Optional.ofNullable(entityList.get(0));
        }
    }

}
