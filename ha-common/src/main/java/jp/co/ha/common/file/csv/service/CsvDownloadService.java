package jp.co.ha.common.file.csv.service;

import java.io.PrintWriter;
import java.util.List;

import jp.co.ha.common.entity.HealthInfoFileSetting;
import jp.co.ha.common.exception.AppIOException;
import jp.co.ha.common.file.csv.CsvConfig;
import jp.co.ha.common.file.csv.model.BaseCsvModel;
import jp.co.ha.common.file.csv.writer.CsvWriter;
import jp.co.ha.common.util.Charset;
import jp.co.ha.common.util.StringUtil;

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

	/**
	 * CSV設定情報を取得する<br>
	 *
	 * @param fileName
	 *     ファイル名
	 * @param healthInfoFileSetting
	 *     健康情報ファイル設定
	 * @return CsvConfig
	 */
	default CsvConfig getCsvConfig(String fileName, HealthInfoFileSetting healthInfoFileSetting) {

		CsvConfig csvConfig = new CsvConfig();
		csvConfig.setFileName(fileName);
		csvConfig.setEnclosureChar(CsvWriter.DOBBLE_QUOTE);
		csvConfig.setHasEnclosure(StringUtil.isTrue(healthInfoFileSetting.getEnclosureCharFlag()));
		csvConfig.setCharset(Charset.UTF_8);
		return csvConfig;
	}

}
