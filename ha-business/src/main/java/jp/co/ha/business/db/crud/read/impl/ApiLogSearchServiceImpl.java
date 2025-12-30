package jp.co.ha.business.db.crud.read.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.read.ApiLogSearchService;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.annotation.Select;
import jp.co.ha.db.entity.ApiLog;
import jp.co.ha.db.entity.ApiLogExample;
import jp.co.ha.db.mapper.ApiLogMapper;

/**
 * API通信ログ検索サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class ApiLogSearchServiceImpl implements ApiLogSearchService {

    /** API通信ログMapper */
    @Autowired
    private ApiLogMapper mapper;

    @Select
    @Override
    @Transactional(readOnly = true)
    public List<ApiLog> findAll(SelectOption selectOption) {

        ApiLogExample example = new ApiLogExample();
        example.setOrderByClause(selectOption.getOrderBy());

        return mapper.selectByExampleWithRowbounds(example, selectOption.toRowBounds());
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public long countBySeqApiLogId(Long seqApiLogId) {

        ApiLogExample example = new ApiLogExample();
        ApiLogExample.Criteria criteria = example.createCriteria();
        if (seqApiLogId != null) {
            // API通信情報ID
            criteria.andSeqApiLogIdEqualTo(seqApiLogId);
        }
        return mapper.countByExample(example);
    }

}
