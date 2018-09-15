package jp.co.ha.api.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import jp.co.ha.api.request.HealthInfoRegistRequest;
import jp.co.ha.api.service.impl.HealthInfoRegistServiceImpl;
import jp.co.ha.common.api.RequestType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.ErrorCode;

public class HealthInfoRegistServiceTest {

	/** 登録サービス */
	private HealthInfoRegistService service = new HealthInfoRegistServiceImpl();

	@Test
	public void testCheckRequest() {
		HealthInfoRegistRequest request = getRequest();
		try {
			service.checkRequest(request);
		} catch (BaseException e) {
			assertTrue(ErrorCode.REQUIRE == e.getErrorCode());
		}
	}

	private HealthInfoRegistRequest getRequest() {
		HealthInfoRegistRequest request = new HealthInfoRegistRequest();
		request.setRequestType(RequestType.HEALTH_INFO_REGIST);
		request.setUserId("master");
		request.setHeight(new BigDecimal("170.000"));
		request.setWeight(new BigDecimal("50.000"));
		request.setApiKey("");
		return request;
	}
}
