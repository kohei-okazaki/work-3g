package jp.co.ha.business.db.crud.update.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.update.NewsInfoUpdateService;
import jp.co.ha.common.db.annotation.Update;
import jp.co.ha.db.entity.NewsInfo;
import jp.co.ha.db.entity.NewsInfoExample;
import jp.co.ha.db.mapper.NewsInfoMapper;

/**
 * お知らせ情報更新サービス実装クラス
 * 
 * @version 1.0.0
 */
@Service
public class NewsInfoUpdateServiceImpl implements NewsInfoUpdateService {

    /** お知らせ情報Mapper */
    @Autowired
    private NewsInfoMapper mapper;

    @Update
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(NewsInfo entity) {

        NewsInfoExample example = new NewsInfoExample();
        example.createCriteria()
                .andSeqNewsInfoIdEqualTo(entity.getSeqNewsInfoId());

        mapper.updateByExampleSelective(entity, example);
    }

}
