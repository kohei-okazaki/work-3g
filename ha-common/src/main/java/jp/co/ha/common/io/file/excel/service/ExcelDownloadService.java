package jp.co.ha.common.io.file.excel.service;

import org.springframework.web.servlet.View;

import jp.co.ha.common.exception.BaseException;

/**
 * Excelダウンロードサービスインターフェース<br>
 * 継承先で@Service(value = "サービス実装用のAnnotation") をつけてInjectionすること<br>
 *
 * @param <T>
 *     出力対象データ
 */
public interface ExcelDownloadService<T> {

	/**
	 * メイン処理<br>
	 * 継承先で詳細を書く<br>
	 * 渡したいデータを引数に指定する<br>
	 *
	 * @param t
	 *     出力対象データ
	 * @return View
	 * @throws BaseException
	 */
	View execute(T t) throws BaseException;

}