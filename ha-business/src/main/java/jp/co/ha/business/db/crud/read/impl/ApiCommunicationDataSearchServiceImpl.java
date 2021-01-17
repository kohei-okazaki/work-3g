package jp.co.ha.business.db.crud.read.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.read.ApiCommunicationDataSearchService;
import jp.co.ha.db.entity.ApiCommunicationData;
import jp.co.ha.db.entity.ApiCommunicationDataExample;
import jp.co.ha.db.mapper.ApiCommunicationDataMapper;

/**
 * API通信情報検索サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class ApiCommunicationDataSearchServiceImpl
        implements ApiCommunicationDataSearchService {

    /** {@linkplain ApiCommunicationDataMapper} */
    @Autowired
    private ApiCommunicationDataMapper mapper;

    @Override
    public List<ApiCommunicationData> findAll() {
        ApiCommunicationDataExample example = new ApiCommunicationDataExample();
        return mapper.selectByExample(example);
    }

}
