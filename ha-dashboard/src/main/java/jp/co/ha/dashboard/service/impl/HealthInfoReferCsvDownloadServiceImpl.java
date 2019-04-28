package jp.co.ha.dashboard.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.stereotype.Service;

import jp.co.ha.business.io.file.csv.model.ReferenceCsvDownloadModel;
import jp.co.ha.business.io.file.csv.writer.ReferenceCsvWriter;
import jp.co.ha.common.exception.AppIOException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.common.io.file.csv.service.CsvDownloadService;
import jp.co.ha.common.io.file.csv.writer.CsvWriter;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.FileUtil;
import jp.co.ha.common.util.FileUtil.FileSeparator;

/**
 * 結果照会画面CSVダウンロードサービスクラス実装クラス
 *
 */
@Service("referenceDownloadCsv")
public class HealthInfoReferCsvDownloadServiceImpl implements CsvDownloadService<ReferenceCsvDownloadModel> {

	/** LOG */
	private static final Logger LOG = LoggerFactory.getLogger(HealthInfoReferCsvDownloadServiceImpl.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void download(PrintWriter pw, CsvConfig conf, List<ReferenceCsvDownloadModel> modelList)
			throws BaseException {

		try (CsvWriter<ReferenceCsvDownloadModel> writer = new ReferenceCsvWriter(conf, pw)) {
			// CSVに書込
			writer.execute(modelList);
			writer.flush();

			if (!FileUtil.isExists(conf.getOutputPath())) {
				// ユーザIDディレクトリが存在しない場合
				FileUtil.mkdir(conf.getOutputPath());
			}

			// ダウンロードファイル
			File file = FileUtil
					.getFile(conf.getOutputPath() + FileSeparator.SYSTEM.getValue() + conf.getFileName());

			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new AppIOException(CommonErrorCode.FILE_WRITE_ERROR, file.getAbsolutePath() + "のファイル作成に失敗しました",
						e);
			}

			try (FileWriter fw = new FileWriter(file.getAbsolutePath());
					PrintWriter pWriter = new PrintWriter(new BufferedWriter(fw))) {
				pWriter.println(writer.getData());
				pWriter.flush();
			} catch (IOException e) {
				LOG.error("", e);
			}
		}
	}

}
