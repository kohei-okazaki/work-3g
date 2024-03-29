package jp.co.ha.business.db.crud.create.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.create.NewsInfoCreateService;
import jp.co.ha.common.db.annotation.Insert;
import jp.co.ha.db.entity.NewsInfo;
import jp.co.ha.db.mapper.NewsInfoMapper;

/**
 * お知らせ情報登録サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class NewsInfoCreateServiceImpl implements NewsInfoCreateService {

    /** {@linkplain NewsInfoMapper} */
    @Autowired
    private NewsInfoMapper mapper;

    @Insert
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(NewsInfo entity) {
        mapper.insert(entity);
    }

}
