package jp.co.ha.web.view;

import jp.co.ha.common.web.BaseView;

public enum IgnoreLoginInfoView implements BaseView {

	/** ログイン画面URL */
	LOGIN("login/index"),
	/** アカウント作成入力画面URL */
	ACCOUNT_REGIST_INPUT("accountRegist/input"),
	/** アカウント作成入力確認画面URL */
	ACCOUNT_REGIST_CONFIRM("accountRegist/confirm"),
	/** アカウント作成入力完了画面URL */
	ACCOUNT_REGIST_COMPLETE("accountRegist/complete"),;

	private IgnoreLoginInfoView(String name) {
		this.name = name;
	}

	private String name;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return this.name;
	}
}
