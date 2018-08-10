package jp.co.ha.common.file.csv.service;

import java.io.PrintWriter;
import java.util.List;

import jp.co.ha.common.exception.AppIOException;
import jp.co.ha.common.file.csv.CsvConfig;
import jp.co.ha.common.file.csv.model.BaseCsvModel;

/**
 * CSVダウンロードサービスインターフェース<br>
 *
 * @param <T>
 *     CSVモデル
 */
public interface CsvDownloadService<T extends BaseCsvModel> {

	/**
	 * メイン処理<br>
	 *
	 * @param printWriter
	 *     出力用Writer
	 * @param conf
	 *     CSV設定情報
	 * @param modelList
	 *     モデルリスト
	 * @throws AppIOException
	 *     例外
	 */
	void execute(PrintWriter printWriter, CsvConfig conf, List<T> modelList) throws AppIOException;

}
