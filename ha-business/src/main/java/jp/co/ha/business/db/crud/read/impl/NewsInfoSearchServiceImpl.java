package jp.co.ha.business.db.crud.read.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.read.NewsInfoSearchService;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.annotation.Select;
import jp.co.ha.db.entity.NewsInfo;
import jp.co.ha.db.entity.NewsInfoExample;
import jp.co.ha.db.entity.NewsInfoExample.Criteria;
import jp.co.ha.db.entity.NewsInfoKey;
import jp.co.ha.db.mapper.NewsInfoMapper;

/**
 * お知らせ情報検索サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class NewsInfoSearchServiceImpl implements NewsInfoSearchService {

    /** お知らせ情報Mapper */
    @Autowired
    private NewsInfoMapper mapper;

    @Select
    @Override
    @Transactional(readOnly = true)
    public List<NewsInfo> findAll(SelectOption selectOption) {

        NewsInfoExample example = new NewsInfoExample();
        NewsInfoExample.Criteria criteria = example.createCriteria();
        criteria.andDeleteFlagEqualTo(false);
        example.setOrderByClause(selectOption.getOrderBy());

        return mapper.selectByExampleWithRowbounds(example, selectOption.toRowBounds());
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public long countBySeqNewsInfoId(Long seqNewsInfoId) {

        NewsInfoExample example = new NewsInfoExample();
        Criteria criteria = example.createCriteria();
        if (seqNewsInfoId != null) {
            // お知らせ情報ID
            criteria.andSeqNewsInfoIdEqualTo(seqNewsInfoId);
        }
        criteria.andDeleteFlagEqualTo(false);

        return mapper.countByExample(example);
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public Optional<NewsInfo> findById(Long seqNewsInfoId) {
        NewsInfoKey key = new NewsInfoKey();
        key.setSeqNewsInfoId(seqNewsInfoId);
        return Optional.ofNullable(mapper.selectByPrimaryKey(key));
    }

}
