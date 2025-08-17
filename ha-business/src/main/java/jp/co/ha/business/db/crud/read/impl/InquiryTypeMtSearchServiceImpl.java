package jp.co.ha.business.db.crud.read.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.read.InquiryTypeMtSearchService;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.annotation.Select;
import jp.co.ha.db.entity.InquiryTypeMt;
import jp.co.ha.db.entity.InquiryTypeMtExample;
import jp.co.ha.db.mapper.InquiryTypeMtMapper;

/**
 * 問い合わせ種別マスタ検索サービス実装クラス
 * 
 * @version 1.0.0
 */
@Service
public class InquiryTypeMtSearchServiceImpl implements InquiryTypeMtSearchService {

    /** 問い合わせ種別マスタMapper */
    @Autowired
    private InquiryTypeMtMapper inquiryTypeMtMapper;

    @Select
    @Override
    @Transactional(readOnly = true)
    public List<InquiryTypeMt> findAll(SelectOption selectOption) {

        InquiryTypeMtExample example = new InquiryTypeMtExample();
        example.setOrderByClause(selectOption.getOrderBy());

        return inquiryTypeMtMapper.selectByExample(example);
    }
}
