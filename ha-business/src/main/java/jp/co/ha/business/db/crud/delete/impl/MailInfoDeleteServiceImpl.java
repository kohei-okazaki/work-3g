package jp.co.ha.business.db.crud.delete.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.delete.MailInfoDeleteService;
import jp.co.ha.common.db.annotation.Delete;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.mapper.MailInfoMapper;

/**
 * メール情報削除サービスインターフェース実装クラス
 *
 */
public class MailInfoDeleteServiceImpl implements MailInfoDeleteService {

	@Autowired
	private MailInfoMapper mapper;

	/**
	 * {@inheritDoc}
	 */
	@Delete
	@Override
	@Transactional
	public void deleteByUserId(String userId) throws BaseException {
		mapper.deleteByPrimaryKey(userId);
	}
}
