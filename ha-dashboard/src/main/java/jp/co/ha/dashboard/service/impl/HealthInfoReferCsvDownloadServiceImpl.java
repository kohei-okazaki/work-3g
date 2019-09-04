package jp.co.ha.dashboard.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.stereotype.Service;

import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.io.file.csv.model.ReferenceCsvDownloadModel;
import jp.co.ha.business.io.file.csv.writer.ReferenceCsvWriter;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.common.io.file.csv.service.CsvDownloadService;
import jp.co.ha.common.io.file.csv.writer.CsvWriter;
import jp.co.ha.common.util.FileUtil;
import jp.co.ha.common.util.FileUtil.FileSeparator;

/**
 * 健康情報照会画面CSVダウンロードサービス実装クラス
 *
 */
@Service("referenceDownloadCsv")
public class HealthInfoReferCsvDownloadServiceImpl implements CsvDownloadService<ReferenceCsvDownloadModel> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void download(PrintWriter printWriter, CsvConfig conf, List<ReferenceCsvDownloadModel> modelList)
			throws BaseException {

		try (CsvWriter<ReferenceCsvDownloadModel> writer = new ReferenceCsvWriter(conf, printWriter)) {

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
				throw new SystemException(CommonErrorCode.FILE_WRITE_ERROR, file.getAbsolutePath() + "のファイル作成に失敗しました",
						e);
			}

			try (FileWriter fw = new FileWriter(file.getAbsolutePath());
					PrintWriter pw = new PrintWriter(new BufferedWriter(fw))) {
				pw.println(writer.getData());
				pw.flush();
			} catch (IOException e) {
				throw new BusinessException(CommonErrorCode.FILE_WRITE_ERROR, "ファイルの書き込みに失敗しました", e);
			}
		}
	}

}
