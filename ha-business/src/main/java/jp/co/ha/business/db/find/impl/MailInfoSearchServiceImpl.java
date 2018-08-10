package jp.co.ha.business.db.find.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.dao.MailInfoDao;
import jp.co.ha.business.db.entity.MailInfo;
import jp.co.ha.business.db.find.MailInfoSearchService;
import jp.co.ha.common.exception.BaseException;

/**
 * メール情報検索サービスインターフェース実装クラス<br>
 *
 */
@Service
public class MailInfoSearchServiceImpl implements MailInfoSearchService {

	/** メール情報Dao */
	@Autowired
	private MailInfoDao mailInfoDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MailInfo findByUserId(String userId) throws BaseException {
		return mailInfoDao.selectByUserId(userId);
	}
}
