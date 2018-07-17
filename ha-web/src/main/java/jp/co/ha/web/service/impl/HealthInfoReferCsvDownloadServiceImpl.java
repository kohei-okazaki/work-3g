package jp.co.ha.web.service.impl;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.stereotype.Service;

import jp.co.ha.common.exception.AppIOException;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.file.csv.CsvConfig;
import jp.co.ha.common.file.csv.service.CsvDownloadService;
import jp.co.ha.common.file.csv.writer.CsvWriter;
import jp.co.ha.web.file.csv.model.ReferenceCsvDownloadModel;
import jp.co.ha.web.file.csv.writer.ReferenceCsvWriter;

/**
 * 結果照会画面CSVダウンロードサービスクラス実装クラス<br>
 *
 */
@Service(value = "referenceDownloadCsv")
public class HealthInfoReferCsvDownloadServiceImpl implements CsvDownloadService<ReferenceCsvDownloadModel> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(PrintWriter printWriter, CsvConfig conf, List<ReferenceCsvDownloadModel> modelList) throws AppIOException {
		try (CsvWriter<ReferenceCsvDownloadModel> writer = new ReferenceCsvWriter(conf, printWriter)) {
			// CSVに書込
			writer.execute(modelList);
			writer.flush();
		} catch (AppIOException e) {
			throw new AppIOException(ErrorCode.FILE_WRITE_ERROR, "ファイルの出力処理に失敗しました");
		}
	}

}
