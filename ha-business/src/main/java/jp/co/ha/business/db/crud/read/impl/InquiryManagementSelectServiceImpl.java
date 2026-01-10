package jp.co.ha.business.db.crud.read.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.component.InquiryComponent.Status;
import jp.co.ha.business.db.crud.read.InquiryManagementSelectService;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.annotation.Select;
import jp.co.ha.db.entity.InquiryManagementExample;
import jp.co.ha.db.entity.custom.CompositeInquiry;
import jp.co.ha.db.mapper.InquiryManagementMapper;
import jp.co.ha.db.mapper.custom.CompositeInquriyMapper;

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

    @Override
    public long count() {
        return this.count(null);
    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public long count(Long seqInquiryMngId) {
        InquiryManagementExample example = new InquiryManagementExample();
        InquiryManagementExample.Criteria criteria = example.createCriteria();
        if (seqInquiryMngId != null) {
            criteria.andSeqInquiryMngIdEqualTo(seqInquiryMngId);
        }
        criteria.andDeleteFlagEqualTo(false);
        return mapper.countByExample(example);
    }

    @Override
    public boolean isExistBySeqInquiryMngId(Long seqInquiryMngId) {
        return this.count(seqInquiryMngId) > 0;

    }

    @Select
    @Override
    @Transactional(readOnly = true)
    public long countByStatusList(Status... statuses) {
        InquiryManagementExample example = new InquiryManagementExample();
        InquiryManagementExample.Criteria criteria = example.createCriteria();
        if (statuses != null) {
            criteria.andInquiryStatusIn(Arrays.asList(statuses).stream()
                    .map(e -> e.getValue()).collect(Collectors.toList()));
        }
        criteria.andDeleteFlagEqualTo(false);
        return mapper.countByExample(example);
    }

}
