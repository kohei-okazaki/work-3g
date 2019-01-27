package jp.co.ha.web.service.impl;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.stereotype.Service;

import jp.co.ha.business.io.file.csv.model.ReferenceCsvDownloadModel;
import jp.co.ha.business.io.file.csv.writer.ReferenceCsvWriter;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.common.io.file.csv.service.CsvDownloadService;
import jp.co.ha.common.io.file.csv.writer.CsvWriter;

/**
 * 結果照会画面CSVダウンロードサービスクラス実装クラス
 *
 */
@Service("referenceDownloadCsv")
public class HealthInfoReferCsvDownloadServiceImpl implements CsvDownloadService<ReferenceCsvDownloadModel> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(PrintWriter pw, CsvConfig conf, List<ReferenceCsvDownloadModel> modelList) {
		try (CsvWriter<ReferenceCsvDownloadModel> writer = new ReferenceCsvWriter(conf, pw)) {
			// CSVに書込
			writer.execute(modelList);
			writer.flush();
		}
	}

}
