package jp.co.ha.business.db.crud.read.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.cache.RootRoleMtCacheComponent;
import jp.co.ha.business.db.crud.read.RootRoleMtSearchService;
import jp.co.ha.common.db.annotation.Select;
import jp.co.ha.db.entity.RootRoleMt;
import jp.co.ha.db.entity.RootRoleMtExample;
import jp.co.ha.db.mapper.RootRoleMtMapper;

/**
 * 管理者サイト権限マスタ検索サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class RootRoleMtSearchServiceImpl implements RootRoleMtSearchService {

    /** 管理者サイト権限マスタMapper */
    @Autowired
    private RootRoleMtMapper mapper;
    /** 管理者サイト権限マスタのキャッシュComponent */
    @Autowired
    private RootRoleMtCacheComponent cacheComponent;

    @Select
    @Override
    @Transactional(readOnly = true)
    public List<RootRoleMt> findByRoles(List<String> roles) {
        return this.findAll().stream()
                .filter(e -> roles.contains(e.getRole()))
                .collect(Collectors.toList());
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public List<RootRoleMt> findAll() {
        return cacheComponent.get(() -> mapper.selectByExample(new RootRoleMtExample()));
    }

}
