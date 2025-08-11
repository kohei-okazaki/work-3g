package jp.co.ha.business.db.crud.read.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.read.InquiryManagementSelectService;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.annotation.Select;
import jp.co.ha.db.entity.InquiryManagementExample;
import jp.co.ha.db.entity.composite.CompositeInquiry;
import jp.co.ha.db.mapper.InquiryManagementMapper;
import jp.co.ha.db.mapper.composite.CompositeInquriyMapper;

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

    /** 問い合わせ情報複合Mapper */
    @Autowired
    private CompositeInquriyMapper compositeInquriyMapper;

    @Select
    @Override
    @Transactional(readOnly = true)
    public List<CompositeInquiry> findAll(SelectOption selectOption) {

        InquiryManagementExample example = new InquiryManagementExample();
        example.setOrderByClause(selectOption.getOrderBy());

        return compositeInquriyMapper.selectAll(example, selectOption.toRowBounds());
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public long count() {
        InquiryManagementExample example = new InquiryManagementExample();
        return mapper.countByExample(example);
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public boolean isExistBySeqInquiryMngId(long seqInquiryMngId) {

        InquiryManagementExample example = new InquiryManagementExample();
        InquiryManagementExample.Criteria criteria = example.createCriteria();
        criteria.andSeqInquiryMngIdEqualTo(seqInquiryMngId);
        criteria.andDeleteFlagEqualTo(false);

        return mapper.countByExample(example) > 0;

    }

}
