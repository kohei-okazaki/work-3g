package jp.co.ha.business.io.file.excel.model;

import java.util.List;

import jp.co.ha.business.dto.HealthInfoReferenceDto;
import jp.co.ha.common.io.file.excel.model.BaseExcelComponent;

public class ReferenceExcelComponent implements BaseExcelComponent {

	/** ユーザID */
	private String userId;
	/** 結果リスト */
	private List<HealthInfoReferenceDto> resultList;

	/**
	 * userIdを返す
	 *
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * userIdを設定する
	 *
	 * @param userId
	 *     ユーザID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * resultListを返す
	 *
	 * @return resultList
	 */
	public List<HealthInfoReferenceDto> getResultList() {
		return resultList;
	}

	/**
	 * resultListを設定する
	 *
	 * @param resultList
	 *     結果リスト
	 */
	public void setResultList(List<HealthInfoReferenceDto> resultList) {
		this.resultList = resultList;
	}

}
