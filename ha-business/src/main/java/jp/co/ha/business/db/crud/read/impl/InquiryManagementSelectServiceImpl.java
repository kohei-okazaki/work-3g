package jp.co.ha.business.db.crud.read.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.read.InquiryManagementSelectService;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.annotation.Select;
import jp.co.ha.db.entity.InquiryManagement;
import jp.co.ha.db.entity.InquiryManagementExample;
import jp.co.ha.db.mapper.InquiryManagementMapper;

/**
 * 問い合わせ管理情報検索サービス実装クラス
 * 
 * @version 1.0.0
 */
@Service
public class InquiryManagementSelectServiceImpl
        implements InquiryManagementSelectService {

    /** 問い合わせ管理情報Mapper */
    @Autowired
    private InquiryManagementMapper mapper;

    @Select
    @Override
    @Transactional(readOnly = true)
    public List<InquiryManagement> findAll(SelectOption selectOption) {

        InquiryManagementExample example = new InquiryManagementExample();
        example.setOrderByClause(selectOption.getOrderBy());

        return mapper.selectByExampleWithRowbounds(example, selectOption.toRowBounds());
    }

}
