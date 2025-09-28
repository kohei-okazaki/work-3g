package jp.co.ha.business.db.crud.read.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.annotation.Select;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.entity.HealthInfoExample;
import jp.co.ha.db.entity.composite.CompositeHealthInfo;
import jp.co.ha.db.entity.composite.CompositeHealthInfoKey;
import jp.co.ha.db.entity.composite.CompositeMonthlyRegData;
import jp.co.ha.db.mapper.HealthInfoMapper;
import jp.co.ha.db.mapper.composite.CompositeHealthInfoMapper;
import jp.co.ha.db.mapper.composite.CompositeMonthlyMapper;

/**
 * 健康情報検索サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class HealthInfoSearchServiceImpl implements HealthInfoSearchService {

    /** 健康情報Mapper */
    @Autowired
    private HealthInfoMapper mapper;
    /** 健康情報とBMI範囲マスタの複合Mapper */
    @Autowired
    private CompositeHealthInfoMapper compositeHealthInfoMapper;
    /** 月ごとの健康情報Mapper */
    @Autowired
    private CompositeMonthlyMapper compositeMonthlyMapper;

    @Select
    @Override
    @Transactional(readOnly = true)
    public List<HealthInfo> findBySeqUserIdBetweenHealthInfoRegDate(Long seqUserId,
            LocalDateTime fromHealthInfoRegDate, LocalDateTime toHealthInfoRegDate,
            SelectOption selectOption) {

        HealthInfoExample example = new HealthInfoExample();
        HealthInfoExample.Criteria criteria = example.createCriteria();

        // ユーザID
        if (seqUserId != null) {
            criteria.andSeqUserIdEqualTo(seqUserId);
        }
        // 健康情報登録日時
        criteria.andHealthInfoRegDateBetween(fromHealthInfoRegDate, toHealthInfoRegDate);
        // ソート処理
        example.setOrderByClause(selectOption.getOrderBy());

        if (selectOption.getPageable() != null) {
            // ページング
            example.setPageable(selectOption.getPageable());
            return mapper.selectByExampleWithRowbounds(example,
                    selectOption.toRowBounds());
        }

        return mapper.selectByExample(example);
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public long countBySeqUserIdBetweenHealthInfoRegDate(Long seqUserId,
            LocalDateTime fromHealthInfoRegDate, LocalDateTime toHealthInfoRegDate) {

        HealthInfoExample example = new HealthInfoExample();
        HealthInfoExample.Criteria criteria = example.createCriteria();
        // ユーザID
        criteria.andSeqUserIdEqualTo(seqUserId);
        // 健康情報登録日時
        criteria.andHealthInfoRegDateBetween(fromHealthInfoRegDate, toHealthInfoRegDate);

        return mapper.countByExample(example);
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public List<HealthInfo> findByHealthInfoIdAndSeqUserId(Long seqHealthInfoId,
            Long seqUserId) {

        HealthInfoExample example = new HealthInfoExample();
        HealthInfoExample.Criteria criteria = example.createCriteria();
        // 健康情報ID
        criteria.andSeqHealthInfoIdEqualTo(seqHealthInfoId);
        // ユーザID
        criteria.andSeqUserIdEqualTo(seqUserId);

        return mapper.selectByExample(example);
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public long countByHealthInfoIdAndSeqUserId(Long seqHealthInfoId,
            Long seqUserId) {

        HealthInfoExample example = new HealthInfoExample();
        HealthInfoExample.Criteria criteria = example.createCriteria();

        if (seqHealthInfoId != null) {
            // 健康情報ID
            criteria.andSeqHealthInfoIdEqualTo(seqHealthInfoId);
        }
        if (seqUserId != null) {
            // ユーザID
            criteria.andSeqUserIdEqualTo(seqUserId);
        }

        return mapper.countByExample(example);
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public List<HealthInfo> findBySeqUserId(Long seqUserId,
            SelectOption selectOption) {

        HealthInfoExample example = new HealthInfoExample();
        HealthInfoExample.Criteria criteria = example.createCriteria();
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
    public CompositeHealthInfo findHealthInfoDetail(Long seqHealthInfoId,
            Long seqUserId) {

        CompositeHealthInfoKey key = new CompositeHealthInfoKey();
        key.setSeqHealthInfoId(seqHealthInfoId);
        key.setSeqUserId(seqUserId);

        return compositeHealthInfoMapper.selectByPrimaryKey(key);
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public List<CompositeHealthInfo> findHealthInfoDetailList(SelectOption selectOption) {

        HealthInfoExample example = new HealthInfoExample();
        example.setOrderByClause(selectOption.getOrderBy());

        return compositeHealthInfoMapper.selectAll(example, selectOption.toRowBounds());
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public List<CompositeMonthlyRegData> findMonthly(LocalDateTime from,
            LocalDateTime to) {
        return compositeMonthlyMapper.selectByHealthInfoRegDate(from, to);
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public HealthInfo findBySeqUserIdAndLowerSeqHealthInfoId(Long seqHealthInfoId,
            Long seqUserId, SelectOption selectOption) {

        HealthInfoExample example = new HealthInfoExample();
        HealthInfoExample.Criteria criteria = example.createCriteria();

        // 健康情報ID
        criteria.andSeqHealthInfoIdLessThan(seqHealthInfoId);
        // ユーザID
        criteria.andSeqUserIdEqualTo(seqUserId);
        // ソート処理
        example.setOrderByClause(selectOption.getOrderBy());
        // 検索上限数
        example.setLimit(selectOption.getLimit());

        List<HealthInfo> list = mapper.selectByExample(example);
        return CollectionUtil.isEmpty(list) ? null : list.get(0);
    }

}
