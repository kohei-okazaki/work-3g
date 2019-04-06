package jp.co.ha.batch.execute;

import java.util.List;

import jp.co.ha.batch.type.BatchResult;
import jp.co.ha.common.exception.BaseException;

/**
 * 基底Batchクラス<br>
 * すべてのBatchクラスはこのクラスを継承する
 *
 */
public abstract class BaseBatch {

	/**
	 * 実処理を行う
	 * @return Batch結果
	 * @throws BaseException 基底例外
	 */
	protected abstract BatchResult execute() throws BaseException;

	/**
	 * オプションリストを設定する
	 * @param optionList オプションリスト
	 */
	protected abstract void setOptions(List<String> optionList);

}
