package jp.co.ha.business.db.crud.create.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.create.ApiLogCreateService;
import jp.co.ha.common.db.annotation.Insert;
import jp.co.ha.db.entity.ApiLog;
import jp.co.ha.db.mapper.ApiLogMapper;

/**
 * API通信ログ作成サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class ApiLogCreateServiceImpl implements ApiLogCreateService {

    /** API通信ログMapper */
    @Autowired
    private ApiLogMapper mapper;

    @Insert
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ApiLog entity) {
        mapper.insert(entity);
    }

}
