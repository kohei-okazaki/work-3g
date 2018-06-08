package jp.co.ha.common.file.excel.service;

import org.springframework.web.servlet.View;

/**
 * Excelダウンロードサービスインターフェース<br>
 * 継承先で@Service(value = "サービス実装用のAnnotation") をつけてInjectionすること<br>
 *
 * @param <F>
 *            出力対象データ
 */
public interface ExcelDownloadService<F> {

	/**
	 * メイン処理<br>
	 * 継承先で詳細を書く<br>
	 * 渡したいデータを引数に指定する<br>
	 *
	 * @param f
	 *            出力対象データ
	 * @return View
	 */
	View execute(F f);

}