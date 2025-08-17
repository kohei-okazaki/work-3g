package jp.co.ha.business.db.crud.update.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.update.ApiCommunicationDataUpdateService;
import jp.co.ha.common.db.annotation.Update;
import jp.co.ha.db.entity.ApiCommunicationData;
import jp.co.ha.db.mapper.ApiCommunicationDataMapper;

/**
 * API通信情報更新サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class ApiCommunicationDataUpdateServiceImpl
        implements ApiCommunicationDataUpdateService {

    /** API通信情報Mapper */
    @Autowired
    private ApiCommunicationDataMapper mapper;

    @Update
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ApiCommunicationData entity) {
        mapper.updateByPrimaryKey(entity);
    }

}
