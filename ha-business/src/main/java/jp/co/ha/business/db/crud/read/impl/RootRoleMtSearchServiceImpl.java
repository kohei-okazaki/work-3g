package jp.co.ha.business.db.crud.read.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.read.RootRoleMtSearchService;
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

}
