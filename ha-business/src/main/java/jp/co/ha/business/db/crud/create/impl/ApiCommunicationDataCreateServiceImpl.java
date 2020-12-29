package jp.co.ha.business.db.crud.create.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.create.ApiCommunicationDataCreateService;
import jp.co.ha.common.db.annotation.Insert;
import jp.co.ha.db.entity.ApiCommunicationData;
import jp.co.ha.db.mapper.ApiCommunicationDataMapper;

/**
 * API通信情報作成サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class ApiCommunicationDataCreateServiceImpl
        implements ApiCommunicationDataCreateService {

    /** API通信情報Mapper */
    @Autowired
    private ApiCommunicationDataMapper mapper;

    @Insert
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ApiCommunicationData entity) {
        mapper.insert(entity);
    }

    // @Insert
    // @Override
    // @Transactional(rollbackFor = Exception.class)
    // public ApiCommunicationData create(String apiName, Integer seqUserId) {
    //
    // ApiCommunicationData entity = new ApiCommunicationData();
    // entity.setApiName(apiName);
    // entity.setSeqUserId(seqUserId);
    // entity.setRequestDate(DateTimeUtil.getSysDate());
    //
    // create(entity);
    //
    // return entity;
    // }

}
