package jp.co.ha.business.db.crud.update.impl;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.ha.business.db.crud.update.RootLoginInfoUpdateService;
import jp.co.ha.db.mapper.RootLoginInfoMapper;

/**
 * 管理者サイトユーザログイン情報更新サービス実装クラス
 *
 * @version 1.0.0
 */
public class RootLoginInfoUpdateServiceImpl implements RootLoginInfoUpdateService {

    /** 管理者サイトログイン情報Mapper */
    @Autowired
    private RootLoginInfoMapper mapper;

}
