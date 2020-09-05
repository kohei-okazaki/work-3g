package jp.co.ha.batch.execute;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import jp.co.ha.batch.type.BatchResult;
import jp.co.ha.common.exception.BaseException;

/**
 * 基底Batchクラス<br>
 * すべてのBatchクラスはこのクラスを継承する
 *
 * @version 1.0.0
 */
public abstract class BaseBatch {

    /**
     * 実処理を行う
     *
     * @param cmd
     *     {@linkplain CommandLine}
     * @return Batch結果
     * @throws BaseException
     *     基底例外
     */
    protected abstract BatchResult execute(CommandLine cmd) throws BaseException;

    /**
     * {@linkplain Options}を返す
     *
     * @return Options
     */
    protected abstract Options getOptions();

}
