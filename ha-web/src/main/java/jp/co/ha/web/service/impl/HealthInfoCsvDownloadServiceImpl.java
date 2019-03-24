package jp.co.ha.web.service.impl;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.stereotype.Service;

import jp.co.ha.business.io.file.csv.model.HealthInfoCsvDownloadModel;
import jp.co.ha.business.io.file.csv.writer.HealthInfoCsvWriter;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.common.io.file.csv.service.CsvDownloadService;
import jp.co.ha.common.io.file.csv.writer.CsvWriter;

/**
 * 健康情報CSVダウンロードサービス実装クラス
 *
 */
@Service("healthInfoDownloadCsv")
public class HealthInfoCsvDownloadServiceImpl implements CsvDownloadService<HealthInfoCsvDownloadModel> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(PrintWriter pw, CsvConfig conf, List<HealthInfoCsvDownloadModel> modelList)
			throws BaseException {
		try (CsvWriter<HealthInfoCsvDownloadModel> writer = new HealthInfoCsvWriter(conf, pw)) {
			// CSVに書込
			writer.execute(modelList);
			writer.flush();
		}
	}
}
