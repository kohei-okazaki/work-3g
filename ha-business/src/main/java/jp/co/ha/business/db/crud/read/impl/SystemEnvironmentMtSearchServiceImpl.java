package jp.co.ha.business.db.crud.read.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ha.business.db.crud.read.SystemEnvironmentMtSearchService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.SystemEnvironmentMt;
import jp.co.ha.db.entity.SystemEnvironmentMtExample;
import jp.co.ha.db.mapper.SystemEnvironmentMtMapper;

/**
 * システム環境マスタ検索サービスインターフェース実装クラス
 *
 */
@Service
public class SystemEnvironmentMtSearchServiceImpl implements SystemEnvironmentMtSearchService {

	@Autowired
	private SystemEnvironmentMtMapper mapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public SystemEnvironmentMt find() throws BaseException {
		List<SystemEnvironmentMt> list = mapper.selectByExample(new SystemEnvironmentMtExample());
		return list.get(0);
	}

}
