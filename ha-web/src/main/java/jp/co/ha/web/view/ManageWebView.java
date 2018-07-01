package jp.co.ha.web.view;

import jp.co.ha.common.web.BaseView;

/**
 * 健康管理View列挙
 *
 */
public enum ManageWebView implements BaseView {

	/** ログイン画面URL */
	LOGIN("login"),
	/** 健康情報入力画面URL */
	HEALTH_INFO_INPUT("healthInfo-input"),
	/** 健康情報入力確認画面URL */
	HEALTH_INFO_CONFIRM("healthInfo-confirm"),
	/** 健康情報入力完了画面URL */
	HEALTH_INFO_COMPLETE("healthInfo-complete"),
	/** メニュー画面URL */
	MENU("menu"),
	/** エラー画面URL */
	ERROR("error"),
	/** 健康情報照会画面URL */
	HEALTH_INFO_REFFERNCE("healthInfo-reference"),
	/** アカウント設定入力画面URL */
	ACCOUNT_SETTING_INPUT("account-setting-input"),
	/** アカウント設定入力確認画面URL */
	ACCOUNT_SETTING_CONFIRM("account-setting-confirm"),
	/** アカウント設定入力完了画面URL */
	ACCOUNT_SETTING_COMPLETE("account-setting-complete"),
	/** 通知設定入力画面URL */
	NOTICE_SETTING_INPUT("notice-setting-input"),
	/** 通知設定入力画面URL */
	NOTICE_SETTING_CONFIRM("notice-setting-confirm"),
	/** 通知設定入力完了画面URL */
	NOTICE_SETTING_COMPLETE("notice-setting-complete"),
	/** アカウント作成入力画面URL */
	ACCOUNT_REGIST_INPUT("account-regist-input"),
	/** アカウント作成入力確認画面URL */
	ACCOUNT_REGIST_CONFIRM("account-regist-confirm"),
	/** アカウント作成入力完了画面URL */
	ACCOUNT_REGIST_COMPLETE("account-regist-complete"),
	/** 健康情報ファイル入力画面URL */
	HEALTH_INFO_FILE_INPUT("healthInfo-fileInput"),
	/** 健康情報ファイル確認画面URL */
	HEALTH_INFO_FILE_CONFIRM("healthInfo-fileConfirm"),
	/** 健康情報ファイル入力完了画面URL */
	HEALTH_INFO_FILE_COMPLETE("healthInfo-fileComplete"),
	;

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
