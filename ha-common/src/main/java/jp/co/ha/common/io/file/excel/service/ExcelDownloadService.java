package jp.co.ha.common.io.file.excel.service;

import org.springframework.web.servlet.View;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.excel.ExcelConfig;
import jp.co.ha.common.io.file.excel.model.BaseExcelComponent;

/**
 * Excelダウンロードサービスインターフェース<br>
 * 継承先で@Service(value = "サービス実装用のAnnotation") をつけてInjectionすること
 *
 * @param <T>
 *     Excel出力情報設定情報継承クラス
 * @version 1.0.0
 */
public interface ExcelDownloadService<T extends BaseExcelComponent> {

    /**
     * メイン処理
     *
     * @param t
     *     Excel出力情報設定情報
     * @param config
     *     {@linkplain ExcelConfig}
     * @return View
     * @throws BaseException
     *     基底例外
     */
    View download(T t, ExcelConfig config) throws BaseException;

}