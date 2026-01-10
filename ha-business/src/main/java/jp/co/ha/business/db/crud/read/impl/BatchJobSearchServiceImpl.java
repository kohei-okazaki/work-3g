package jp.co.ha.business.db.crud.read.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.read.BatchJobSearchService;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.annotation.Select;
import jp.co.ha.db.entity.custom.CustomJobData;
import jp.co.ha.db.mapper.custom.CustomJobMapper;

/**
 * Batch起動履歴検索サービス実装クラス
 * 
 * @version 1.0.0
 */
@Service
public class BatchJobSearchServiceImpl implements BatchJobSearchService {

    /** Batch起動履歴Mapper */
    @Autowired
    private CustomJobMapper mapper;

    @Select
    @Override
    @Transactional(readOnly = true)
    public List<CustomJobData> findAll(SelectOption selectOption) {
        return mapper.selectPageable(selectOption.toRowBounds());
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public long count() {
        return mapper.count();
    }

}
