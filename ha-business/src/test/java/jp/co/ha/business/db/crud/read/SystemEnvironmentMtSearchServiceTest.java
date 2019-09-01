package jp.co.ha.business.db.crud.read;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import jp.co.ha.business.BaseBusinessTest;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.db.entity.SystemEnvironmentMt;

/**
 * {@link SystemEnvironmentMtSearchService} のjUnit
 *
 */
public class SystemEnvironmentMtSearchServiceTest extends BaseBusinessTest {

	private static final Logger LOG = LoggerFactory.getLogger(SystemEnvironmentMtSearchServiceTest.class);

	@Autowired
	private SystemEnvironmentMtSearchService service;

	@Test
	public void findTest() {
		try {
			SystemEnvironmentMt mt = service.find();
			assertEquals("LOCAL", mt.getEnvironmentId());
			assertEquals(Integer.valueOf("15"), mt.getPagingCount());
		} catch (BaseException e) {
			LOG.error("エラー", e);
		}

	}
}
