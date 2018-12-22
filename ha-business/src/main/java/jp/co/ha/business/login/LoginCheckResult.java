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

	/**
	 * エラーが存在するかどうかを返す
	 *
	 * @return 判定結果
	 */
	public boolean hasError() {
		return errorCount > 0;
	}

	/**
	 * エラーを追加する
	 */
	public void addError() {
		this.errorCount++;
	}

	/**
	 * 名前を返す
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 名前を設定する
	 *
	 * @param name
	 *     名前
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * エラー詳細を返す
	 *
	 * @return errorDetail
	 */
	public String getErrorDetail() {
		return errorDetail;
	}

	/**
	 * エラー詳細を設定する
	 *
	 * @param errorDetail
	 *     エラー詳細
	 */
	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}

}
