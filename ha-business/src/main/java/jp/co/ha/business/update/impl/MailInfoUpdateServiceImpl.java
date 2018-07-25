package jp.co.ha.business.update.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.update.MailInfoUpdateService;
import jp.co.ha.common.dao.MailInfoDao;
import jp.co.ha.common.entity.MailInfo;
import jp.co.ha.common.exception.DataBaseException;

/**
 * メール情報更新サービス実装クラス<br>
 *
 */
@Service
public class MailInfoUpdateServiceImpl implements MailInfoUpdateService {

	/** メール情報Dao */
	@Autowired
	private MailInfoDao mailInfoDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(MailInfo mailInfo) throws DataBaseException {
		mailInfoDao.update(mailInfo);
	}

}
