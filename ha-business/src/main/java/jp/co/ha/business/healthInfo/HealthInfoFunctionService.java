package jp.co.ha.business.healthInfo;

import jp.co.ha.common.entity.Account;

/**
 * 健康情報利用機能サービスインターフェース<br>
 * アカウントごとの利用機能の判定などを定義するクラス<br>
 *
 */
public interface HealthInfoFunctionService {

	/**
	 * 指定したアカウントが健康情報マスク利用フラグを使うかどうかの判定を行う<br>
	 * マスク表示する場合true, それ以外の場合falseを返す<br>
	 * @param account
	 * @return
	 */
	boolean useHealthInfoMask(Account account);

	/**
	 * 指定したアカウントがファイル囲い文字利用フラグを使うかどうかの判定を行う<br>
	 * 囲い文字を利用する場合true, それ以外の場合falseを返す<br>
	 * @param account
	 * @return
	 */
	boolean useFileEnclosureCharFlag(Account account);
}
