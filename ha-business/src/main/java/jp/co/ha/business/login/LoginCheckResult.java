package jp.co.ha.business.login;

/**
 * ログイン情報チェック結果保持クラス
 *
 */
public class LoginCheckResult {

	/** エラーカウント */
	private int errorCount = 0;
	/** エラー項目名 */
	private String name;
	/** 詳細 */
	private String errorDetail;

	public boolean hasError() {
		return errorCount > 0;
	}

	public void addError() {
		this.errorCount++;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getErrorDetail() {
		return errorDetail;
	}

	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}

}
