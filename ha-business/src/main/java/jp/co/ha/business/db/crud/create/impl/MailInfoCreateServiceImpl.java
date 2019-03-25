package jp.co.ha.business.db.crud.create.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.create.MailInfoCreateService;
import jp.co.ha.common.db.annotation.Insert;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.MailInfo;
import jp.co.ha.db.mapper.MailInfoMapper;

/**
 * メール情報作成サービスインターフェース実装クラス
 *
 */
public class MailInfoCreateServiceImpl implements MailInfoCreateService {

	@Autowired
	private MailInfoMapper mapper;

	/**
	 * {@inheritDoc}
	 */
	@Insert
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void create(MailInfo entity) throws BaseException {
		mapper.insert(entity);
	}
}
