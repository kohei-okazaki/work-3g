package jp.co.ha.business.db.crud.update.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.update.InquiryManagementUpdateService;
import jp.co.ha.common.db.annotation.Update;
import jp.co.ha.db.entity.InquiryManagement;
import jp.co.ha.db.mapper.InquiryManagementMapper;

/**
 * 問い合わせ管理情報更新サービス実装クラス
 * 
 * @version 1.0.0
 */
@Service
public class InquiryManagementUpdateServiceImpl
        implements InquiryManagementUpdateService {

    /** 問い合わせ管理情報Mapper */
    @Autowired
    private InquiryManagementMapper mapper;

    @Update
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatusById(InquiryManagement entity) {
        mapper.updateByPrimaryKeySelective(entity);
    }
}
