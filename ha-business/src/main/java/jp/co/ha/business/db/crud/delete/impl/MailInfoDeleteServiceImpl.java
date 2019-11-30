package jp.co.ha.business.db.crud.delete.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.delete.MailInfoDeleteService;
import jp.co.ha.common.db.annotation.Delete;
import jp.co.ha.db.mapper.MailInfoMapper;

/**
 * メール情報削除サービスインターフェース実装クラス
 *
 * @since 1.0
 */
@Service
public class MailInfoDeleteServiceImpl implements MailInfoDeleteService {

	/** MailInfoMapper */
	@Autowired
	private MailInfoMapper mapper;

	/**
	 * {@inheritDoc}
	 */
	@Delete
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteByUserId(String userId) {
		mapper.deleteByPrimaryKey(userId);
	}
}
