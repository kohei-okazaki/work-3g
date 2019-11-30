package jp.co.ha.business.db.crud.read.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.read.MailInfoSearchService;
import jp.co.ha.common.db.annotation.Select;
import jp.co.ha.db.entity.MailInfo;
import jp.co.ha.db.mapper.MailInfoMapper;

/**
 * メール情報検索サービスインターフェース実装クラス
 *
 * @since 1.0
 */
@Service
public class MailInfoSearchServiceImpl implements MailInfoSearchService {

	/** MailInfoMapper */
	@Autowired
	private MailInfoMapper mapper;

	/**
	 * {@inheritDoc}
	 */
	@Select
	@Override
	@Transactional(readOnly = true)
	public Optional<MailInfo> findByUserId(String userId) {
		return Optional.ofNullable(mapper.selectByPrimaryKey(userId));
	}
}
