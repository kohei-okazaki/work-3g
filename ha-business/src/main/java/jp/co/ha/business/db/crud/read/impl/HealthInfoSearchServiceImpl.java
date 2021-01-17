package jp.co.ha.business.db.crud.read.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.annotation.Select;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.entity.HealthInfoExample;
import jp.co.ha.db.entity.HealthInfoExample.Criteria;
import jp.co.ha.db.entity.composite.CompositeHealthInfo;
import jp.co.ha.db.entity.composite.CompositeHealthInfoKey;
import jp.co.ha.db.mapper.HealthInfoMapper;
import jp.co.ha.db.mapper.composite.CompositeHealthInfoMapper;

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
    /** CompositeHealthInfoMapper */
    @Autowired
    private CompositeHealthInfoMapper compositeHealthInfoMapper;

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

        if (selectOption.getPageable() != null) {
            // ページング
            example.setPageable(selectOption.getPageable());
            RowBounds rowBounds = new RowBounds(
                    (int) selectOption.getPageable().getOffset(),
                    selectOption.getPageable().getPageSize());
            return mapper.selectByExampleWithRowbounds(example, rowBounds);
        }

        return mapper.selectByExample(example);
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public long countBySeqUserIdBetweenHealthInfoRegDate(Integer seqUserId,
            LocalDateTime fromHealthInfoRegDate, LocalDateTime toHealthInfoRegDate) {

        HealthInfoExample example = new HealthInfoExample();
        Criteria criteria = example.createCriteria();
        // ユーザID
        criteria.andSeqUserIdEqualTo(seqUserId);
        // 健康情報登録日時
        criteria.andHealthInfoRegDateBetween(fromHealthInfoRegDate, toHealthInfoRegDate);

        return mapper.countByExample(example);
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
    public long countByHealthInfoIdAndSeqUserId(Integer seqHealthInfoId,
            Integer seqUserId) {

        HealthInfoExample example = new HealthInfoExample();
        Criteria criteria = example.createCriteria();
        // 健康情報ID
        criteria.andSeqHealthInfoIdEqualTo(seqHealthInfoId);
        // ユーザID
        criteria.andSeqUserIdEqualTo(seqUserId);

        return mapper.countByExample(example);
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

    @Select
    @Override
    @Transactional(readOnly = true)
    public CompositeHealthInfo findHealthInfoDetail(Integer seqHealthInfoId,
            Integer seqUserId) {

        CompositeHealthInfoKey key = new CompositeHealthInfoKey();
        key.setSeqHealthInfoId(seqHealthInfoId);
        key.setSeqUserId(seqUserId);

        return compositeHealthInfoMapper.selectByPrimaryKey(key);
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public List<CompositeHealthInfo> findHealthInfoDetailList() {
        return compositeHealthInfoMapper.selectAll();
    }

}
