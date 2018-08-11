package jp.co.ha.web.view;

import java.util.Arrays;

import jp.co.ha.common.web.BaseView;

/**
 * ログイン情報をチェックしないview列挙<br>
 *
 */
public enum IgnoreLoginInfoView implements BaseView {

	/** ログイン画面URL */
	LOGIN(ManageWebView.LOGIN.getName() + ".html"),
	/** TOP画面URL */
	TOP(ManageWebView.TOP.getName() + ".html"),
	/** アカウント作成入力画面URL */
	ACCOUNT_REGIST_INPUT(ManageWebView.ACCOUNT_REGIST_INPUT.getName() + ".html"),
	/** アカウント作成入力確認画面URL */
	ACCOUNT_REGIST_CONFIRM(ManageWebView.ACCOUNT_REGIST_CONFIRM.getName() + ".html"),
	/** アカウント作成入力完了画面URL */
	ACCOUNT_REGIST_COMPLETE(ManageWebView.ACCOUNT_REGIST_COMPLETE.getName() + ".html"),
	/** エラー画面URL */
	ERROR(ManageWebView.ERROR.getName() + ".html");

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

	public static IgnoreLoginInfoView of(String name) {
		return Arrays.asList(IgnoreLoginInfoView.class.getEnumConstants())
				.stream()
				.filter(e -> e.getName().equals(name))
				.findFirst()
				.orElse(null);
	}
}
