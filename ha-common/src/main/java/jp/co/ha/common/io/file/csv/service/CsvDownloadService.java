package jp.co.ha.common.io.file.csv.service;

import java.io.PrintWriter;
import java.util.List;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.common.io.file.csv.model.BaseCsvModel;

/**
 * CSVダウンロードサービスインターフェース
 *
 * @param <T>
 *     CSVモデル
 * @version 1.0.0
 */
public interface CsvDownloadService<T extends BaseCsvModel> {

    /**
     * メイン処理
     *
     * @param pw
     *     出力用Writer
     * @param conf
     *     CSV設定情報
     * @param modelList
     *     モデルリスト
     * @throws BaseException
     *     基底例外
     */
    void download(PrintWriter pw, CsvConfig conf, List<T> modelList)
            throws BaseException;

}
