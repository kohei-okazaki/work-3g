package jp.co.ha.batch.invoke;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import jp.co.ha.batch.execute.BaseBatch;
import jp.co.ha.batch.type.BatchResult;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

/**
 * Batchの呼び出しクラス
 *
 */
public class BatchInvoker {

	/** LOG */
	private static final Logger LOG = LoggerFactory.getLogger(BatchInvoker.class);

	/** パッケージ名の接頭語 */
	private static final String PACKAGE_PREFIX = "jp.co.ha.batch.execute.";

	/**
	 * invoke処理
	 * @param args バッチ引数(第一引数：Batch名第二引数以降：オプション引数)
	 */
	@SuppressWarnings("unchecked")
	public static void invoke(String[] args) {
		LOG.info("■■■■■ Batch処理開始 ■■■■■");

		String batchName = PACKAGE_PREFIX + args[0];
		List<String> optionList = new ArrayList<>();
		for (int i = 0; i < args.length; i++) {
			if (i == 0) {
				continue;
			} else {
				optionList.add(args[i]);
			}
		}
		BatchResult batchResult = BatchResult.FAILURE;
		try {
			// batch名からインスタンスを取得
			Class<? extends BaseBatch> batch = (Class<? extends BaseBatch>) Class.forName(batchName);
			BaseBatch batchInstance =  batch.getDeclaredConstructor().newInstance();

			// setOptions 実行
			Method setOptions = batch.getMethod("setOptions", List.class);
			setOptions.invoke(batchInstance, optionList);

			// execute 実行
			Method executeMethod = batch.getMethod("execute");
			batchResult = (BatchResult) executeMethod.invoke(batchInstance);

		} catch (ClassNotFoundException e) {
			LOG.error(batchName + "が存在しません", e);
		} catch (NoSuchMethodException | SecurityException e) {
			LOG.error("setOptionsメソッドがありません", e);
		} catch (Exception e) {
			LOG.error("インスタンスの生成に失敗", e);
		} finally {
			LOG.info(batchResult.getComment());
		}

		LOG.info("■■■■■ Batch処理終了 ■■■■■");
	}



}
