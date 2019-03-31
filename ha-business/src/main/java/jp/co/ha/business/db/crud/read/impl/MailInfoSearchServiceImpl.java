package jp.co.ha.business.db.crud.read.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.read.MailInfoSearchService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.MailInfo;
import jp.co.ha.db.mapper.MailInfoMapper;

/**
 * メール情報検索サービスインターフェース実装クラス
 *
 */
@Service
public class MailInfoSearchServiceImpl implements MailInfoSearchService {

	@Autowired
	private MailInfoMapper mapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public MailInfo findByUserId(String userId) throws BaseException {
		return mapper.selectByPrimaryKey(userId);
	}
}
