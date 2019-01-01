package jp.co.ha.business.healthInfo;

import jp.co.ha.db.entity.HealthInfoFileSetting;

/**
 * 健康情報利用機能サービスインターフェース<br>
 * アカウントごとの利用機能の判定などを定義するクラス
 *
 */
public interface HealthInfoFunctionService {

	/**
	 * 指定したアカウントが健康情報マスク利用フラグを使うかどうかの判定を行う<br>
	 * マスク表示する場合true, それ以外の場合falseを返す
	 *
	 * @param entity
	 *     健康情報ファイル設定
	 * @return 判定結果
	 */
	boolean useHealthInfoMask(HealthInfoFileSetting entity);

	/**
	 * 指定したアカウントがファイル囲い文字利用フラグを使うかどうかの判定を行う<br>
	 * 囲い文字を利用する場合true, それ以外の場合falseを返す
	 *
	 * @param entity
	 *     健康情報ファイル設定
	 * @return 判定結果
	 */
	boolean useFileEnclosureCharFlag(HealthInfoFileSetting entity);

}
