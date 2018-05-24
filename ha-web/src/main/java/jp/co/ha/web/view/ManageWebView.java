package jp.co.ha.web.view;

import jp.co.ha.common.web.BaseView;

/**
 * 健康管理View列挙
 *
 */
public enum ManageWebView implements BaseView {

	/** ログイン画面URL */
	LOGIN("login"),
	/** 健康情報画面URL */
	HEALTH_INFO_INPUT("healthInfo-input"),
	HEALTH_INFO_CONFIRM("healthInfo-confirm"),
	HEALTH_INFO_COMPLETE("healthInfo-complete"),
	/** メニュー画面URL */
	MENU("menu"),
	/** エラー画面URL */
	ERROR("error"),
	/** 結果照会画面URL */
	RESULT_REFFERNCE("result-reference"),
	/** アカウント設定画面URL */
	ACCOUNT_SETTING_INPUT("account-setting-input"),
	ACCOUNT_SETTING_CONFIRM("account-setting-confirm"),
	ACCOUNT_SETTING_COMPLETE("account-setting-complete"),
	/** 通知設定画面URL */
	NOTICE_SETTING_INPUT("notice-setting-input"),
	NOTICE_SETTING_CONFIRM("notice-setting-confirm"),
	NOTICE_SETTING_COMPLETE("notice-setting-complete"),
	/** アカウント作成画面URL */
	ACCOUNT_REGIST_INPUT("account-regist-input"),
	ACCOUNT_REGIST_CONFIRM("account-regist-confirm"),
	ACCOUNT_REGIST_COMPLETE("account-regist-complete");

	/** view名 */
	private String name;

	/**
	 * コンストラクタ<br>
	 *
	 * @param name
	 *            名前
	 */
	private ManageWebView(String name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return this.name;
	}

}
