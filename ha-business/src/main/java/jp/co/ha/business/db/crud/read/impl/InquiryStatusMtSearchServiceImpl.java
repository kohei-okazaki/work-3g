package jp.co.ha.business.db.crud.read.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.read.InquiryStatusMtSearchService;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.annotation.Select;
import jp.co.ha.db.entity.InquiryStatusMt;
import jp.co.ha.db.entity.InquiryStatusMtExample;
import jp.co.ha.db.mapper.InquiryStatusMtMapper;

/**
 * 問い合わせステータスマスタ検索サービス実装クラス
 * 
 * @version 1.0.0
 */
@Service
public class InquiryStatusMtSearchServiceImpl implements InquiryStatusMtSearchService {

    /** 問い合わせステータスマスタMapper */
    @Autowired
    private InquiryStatusMtMapper inquiryStatusMtMapper;

    @Select
    @Override
    @Transactional(readOnly = true)
    public List<InquiryStatusMt> findAll(SelectOption selectOption) {

        InquiryStatusMtExample example = new InquiryStatusMtExample();
        example.setOrderByClause(selectOption.getOrderBy());

        return inquiryStatusMtMapper.selectByExample(example);
    }

}
