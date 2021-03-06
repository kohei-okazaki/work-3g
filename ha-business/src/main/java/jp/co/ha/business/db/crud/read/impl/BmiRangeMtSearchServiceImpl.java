package jp.co.ha.business.db.crud.read.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.cache.BmiRangeMtCacheComponent;
import jp.co.ha.business.db.crud.read.BmiRangeMtSearchService;
import jp.co.ha.common.db.annotation.Select;
import jp.co.ha.db.entity.BmiRangeMt;
import jp.co.ha.db.entity.BmiRangeMtExample;
import jp.co.ha.db.mapper.BmiRangeMtMapper;

/**
 * BMI範囲マスタ検索サービスインターフェース実装クラス
 *
 * @version 1.0.0
 */
@Service
// @CacheConfig(cacheNames = "bmiRangeMt")
public class BmiRangeMtSearchServiceImpl implements BmiRangeMtSearchService {

    /** {@linkplain BmiRangeMtMapper} */
    @Autowired
    private BmiRangeMtMapper mapper;
    /** {@linkplain BmiRangeMtCacheComponent} */
    @Autowired
    private BmiRangeMtCacheComponent cacheComponent;

    @Select
    @Override
    @Transactional(readOnly = true)
    public Optional<BmiRangeMt> findById(Long seqBmiRangeMtId) {
        return this.findAll().stream()
                .filter(e -> e.getSeqBmiRangeMtId().equals(seqBmiRangeMtId))
                .findFirst();
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public List<BmiRangeMt> findAll() {
        return cacheComponent.get(() -> mapper.selectByExample(new BmiRangeMtExample()));
    }

}
