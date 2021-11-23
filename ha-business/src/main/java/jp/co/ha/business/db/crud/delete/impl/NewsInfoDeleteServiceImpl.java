package jp.co.ha.business.db.crud.delete.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.delete.NewsInfoDeleteService;
import jp.co.ha.common.db.annotation.Delete;
import jp.co.ha.db.entity.NewsInfoKey;
import jp.co.ha.db.mapper.NewsInfoMapper;

/**
 * お知らせ情報削除サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class NewsInfoDeleteServiceImpl implements NewsInfoDeleteService {

    /** {@linkplain NewsInfoMapper} */
    @Autowired
    private NewsInfoMapper mapper;

    @Delete
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long seqNewsInfoId) {

        NewsInfoKey key = new NewsInfoKey();
        key.setSeqNewsInfoId(seqNewsInfoId);
        mapper.deleteByPrimaryKey(key);
    }

}
