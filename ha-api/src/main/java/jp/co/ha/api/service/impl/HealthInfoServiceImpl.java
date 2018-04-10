package jp.co.ha.api.service.impl;

import org.springframework.stereotype.Service;

import jp.co.ha.api.exception.HealthInfoException;
import jp.co.ha.api.request.HealthInfoRequest;
import jp.co.ha.api.response.HealthInfoResponse;
import jp.co.ha.api.service.HealthInfoService;

@Service
public class HealthInfoServiceImpl implements HealthInfoService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkRequest(HealthInfoRequest request) throws HealthInfoException {


	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfoResponse execute(HealthInfoRequest request) throws HealthInfoException {

		return null;
	}

}
