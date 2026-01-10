package jp.co.ha.business.healthInfo.service.impl;

import static jp.co.ha.common.exception.CommonErrorCode.*;

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
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.common.io.file.csv.service.CsvDownloadService;
import jp.co.ha.common.io.file.csv.writer.CsvWriter;
import jp.co.ha.common.util.FileUtil;
import jp.co.ha.common.util.FileUtil.FileSeparator;

/**
 * 健康情報照会画面CSVダウンロードサービス実装クラス
 *
 * @version 1.0.0
 */
@Service("referenceDownloadCsv")
public class HealthInfoReferCsvDownloadServiceImpl
        implements CsvDownloadService<ReferenceCsvDownloadModel> {

    @Override
    public void download(PrintWriter printWriter, CsvConfig conf,
            List<ReferenceCsvDownloadModel> modelList) throws BaseException {

        try (CsvWriter<ReferenceCsvDownloadModel> writer = new ReferenceCsvWriter(conf,
                printWriter)) {

            // CSVに書込
            writer.execute(modelList);
            writer.flush();

            if (!FileUtil.isExists(conf.getOutputPath())) {
                // ユーザIDディレクトリが存在しない場合
                FileUtil.mkdir(conf.getOutputPath());
            }

            // 一時ファイル
            File file = FileUtil.getFile(conf.getOutputPath()
                    + FileSeparator.SYSTEM.getValue() + conf.getFileName());
            // 一時ファイル書込
            writeFile(file, writer);
        }
    }

    /**
     * {@linkplain CsvWriter}のデータを{@linkplain File}に書き込みを行う
     *
     * @param file
     *     ファイル
     * @param writer
     *     {@linkplain CsvWriter}
     * @throws BaseException
     *     ファイルの書込に失敗した場合
     */
    private void writeFile(File file, CsvWriter<ReferenceCsvDownloadModel> writer)
            throws BaseException {

        try (FileWriter fw = new FileWriter(file.getPath());
                PrintWriter pw = new PrintWriter(new BufferedWriter(fw), true)) {
            pw.println(writer.getData());
        } catch (IOException e) {
            throw new BusinessException(FILE_WRITE_ERROR, "ファイルの書き込みに失敗しました", e);
        }
    }

}
