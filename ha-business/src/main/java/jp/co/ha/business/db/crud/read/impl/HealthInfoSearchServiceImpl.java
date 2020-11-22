package jp.co.ha.business.db.crud.read.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.annotation.Select;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.entity.HealthInfoExample;
import jp.co.ha.db.entity.HealthInfoExample.Criteria;
import jp.co.ha.db.mapper.HealthInfoMapper;

/**
 * 健康情報検索サービスインターフェース実装クラス
 *
 * @version 1.0.0
 */
@Service
public class HealthInfoSearchServiceImpl implements HealthInfoSearchService {

    /** HealthInfoMapper */
    @Autowired
    private HealthInfoMapper mapper;

    @Select
    @Override
    @Transactional(readOnly = true)
    public List<HealthInfo> findBySeqUserIdBetweenHealthInfoRegDate(Integer seqUserId,
            LocalDateTime fromHealthInfoRegDate, LocalDateTime toHealthInfoRegDate,
            SelectOption selectOption) {

        HealthInfoExample example = new HealthInfoExample();
        Criteria criteria = example.createCriteria();
        // ユーザID
        criteria.andSeqUserIdEqualTo(seqUserId);
        // 健康情報登録日時
        criteria.andHealthInfoRegDateBetween(fromHealthInfoRegDate, toHealthInfoRegDate);
        // ソート処理
        example.setOrderByClause(selectOption.getOrderBy());
        return mapper.selectByExample(example);
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public List<HealthInfo> findByHealthInfoIdAndSeqUserId(Integer seqHealthInfoId,
            Integer seqUserId) {

        HealthInfoExample example = new HealthInfoExample();
        Criteria criteria = example.createCriteria();
        // 健康情報ID
        criteria.andSeqHealthInfoIdEqualTo(seqHealthInfoId);
        // ユーザID
        criteria.andSeqUserIdEqualTo(seqUserId);

        return mapper.selectByExample(example);
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public int getSelectCountBySeqUserId(Integer seqUserId) {

        HealthInfoExample example = new HealthInfoExample();
        Criteria criteria = example.createCriteria();
        // ユーザID
        criteria.andSeqUserIdEqualTo(seqUserId);

        return (int) mapper.countByExample(example);
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public List<HealthInfo> findBySeqUserId(Integer seqUserId,
            SelectOption selectOption) {

        HealthInfoExample example = new HealthInfoExample();
        Criteria criteria = example.createCriteria();
        // ユーザID
        criteria.andSeqUserIdEqualTo(seqUserId);
        // ソート処理
        example.setOrderByClause(selectOption.getOrderBy());
        // 検索上限数
        example.setLimit(selectOption.getLimit());

        return mapper.selectByExample(example);
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public List<HealthInfo> findByBetweenHealthInfoRegDate(
            LocalDateTime fromHealthInfoRegDate, LocalDateTime toHealthInfoRegDate,
            SelectOption selectOption) {

        HealthInfoExample example = new HealthInfoExample();
        Criteria criteria = example.createCriteria();
        criteria.andHealthInfoRegDateBetween(fromHealthInfoRegDate,
                toHealthInfoRegDate);

        // ソート処理
        example.setOrderByClause(selectOption.getOrderBy());
        return mapper.selectByExample(example);
    }

}
