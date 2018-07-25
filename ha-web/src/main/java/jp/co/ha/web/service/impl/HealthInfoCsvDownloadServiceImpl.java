package jp.co.ha.web.service.impl;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.stereotype.Service;

import jp.co.ha.common.exception.AppIOException;
import jp.co.ha.common.file.csv.CsvConfig;
import jp.co.ha.common.file.csv.service.CsvDownloadService;
import jp.co.ha.common.file.csv.writer.CsvWriter;
import jp.co.ha.web.file.csv.model.HealthInfoCsvDownloadModel;
import jp.co.ha.web.file.csv.writer.HealthInfoCsvWriter;

/**
 * 健康情報CSVダウンロードサービス実装クラス<br>
 *
 */
@Service("healthInfoDownloadCsv")
public class HealthInfoCsvDownloadServiceImpl implements CsvDownloadService<HealthInfoCsvDownloadModel> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(PrintWriter printWriter, CsvConfig conf, List<HealthInfoCsvDownloadModel> modelList) throws AppIOException {
		try (CsvWriter<HealthInfoCsvDownloadModel> writer = new HealthInfoCsvWriter(conf, printWriter)) {
			// CSVに書込
			writer.execute(modelList);
			writer.flush();
		}
	}

}
