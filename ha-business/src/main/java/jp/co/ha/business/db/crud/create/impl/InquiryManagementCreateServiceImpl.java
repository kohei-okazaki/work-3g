package jp.co.ha.business.db.crud.create.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.create.InquiryManagementCreateService;
import jp.co.ha.common.db.annotation.Insert;
import jp.co.ha.db.entity.InquiryManagement;
import jp.co.ha.db.mapper.InquiryManagementMapper;

/**
 * 問い合わせ管理情報作成サービス実装クラス
 * 
 * @version 1.0.0
 */
@Service
public class InquiryManagementCreateServiceImpl
        implements InquiryManagementCreateService {

    /** 問い合わせ管理情報Mapper */
    @Autowired
    private InquiryManagementMapper mapper;

    @Insert
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(InquiryManagement entity) {
        mapper.insert(entity);
    }

}
