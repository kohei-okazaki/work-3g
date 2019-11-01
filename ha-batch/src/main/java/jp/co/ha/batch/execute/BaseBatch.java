package jp.co.ha.batch.execute;

import java.util.List;

import org.apache.commons.cli.Options;

import jp.co.ha.batch.type.BatchResult;
import jp.co.ha.common.exception.BaseException;

/**
 * 基底Batchクラス<br>
 * すべてのBatchクラスはこのクラスを継承する
 * 
 * @since 1.0
 */
public abstract class BaseBatch {

	/**
	 * 実処理を行う
	 *
	 * @return Batch結果
	 * @throws BaseException
	 *     基底例外
	 */
	protected abstract BatchResult execute() throws BaseException;

	/**
	 * オプションを取得
	 *
	 * @param optionList
	 *     オプションリスト
	 * @return Options
	 */
	protected abstract Options getOptions(List<String> optionList);

}
