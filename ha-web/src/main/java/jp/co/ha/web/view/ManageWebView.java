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

	/** トップ画面URL */
	TOP("top"),
	/** エラー画面URL */
	ERROR("error"),

	/** 健康情報照会画面URL */
	HEALTH_INFO_REFFERNCE("healthInfo-reference"),

	/** アカウント設定入力画面URL */
	ACCOUNT_SETTING_INPUT("accountSetting-input"),
	/** アカウント設定入力確認画面URL */
	ACCOUNT_SETTING_CONFIRM("accountSetting-confirm"),
	/** アカウント設定入力完了画面URL */
	ACCOUNT_SETTING_COMPLETE("accountSetting-complete"),

	/** アカウント作成入力画面URL */
	ACCOUNT_REGIST_INPUT("accountRegist-input"),
	/** アカウント作成入力確認画面URL */
	ACCOUNT_REGIST_CONFIRM("accountRegist-confirm"),
	/** アカウント作成入力完了画面URL */
	ACCOUNT_REGIST_COMPLETE("accountRegist-complete"),

	/** 健康情報ファイル入力画面URL */
	HEALTH_INFO_FILE_INPUT("healthInfoFile-input"),
	/** 健康情報ファイル確認画面URL */
	HEALTH_INFO_FILE_CONFIRM("healthInfoFile-confirm"),
	/** 健康情報ファイル入力完了画面URL */
	HEALTH_INFO_FILE_COMPLETE("healthInfoFile-complete"),

	/** 健康情報ファイル設定入力画面URL */
	HEALTH_INFO_FILE_SETTING_INPUT("healthInfoFileSetting-input"),
	/** 健康情報ファイル設定確認画面URL */
	HEALTH_INFO_FILE_SETTING_CONFIRM("healthInfoFileSetting-confirm"),
	/** 健康情報ファイル設定入力完了画面URL */
	HEALTH_INFO_FILE_SETTING_COMPLETE("healthInfoFileSetting-complete"),
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
