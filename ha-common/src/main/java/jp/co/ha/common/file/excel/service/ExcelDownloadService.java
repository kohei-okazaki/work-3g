package jp.co.ha.common.file.excel.service;

import org.springframework.web.servlet.View;

import jp.co.ha.common.exception.BaseException;

/**
 * Excelダウンロードサービスインターフェース<br>
 * 継承先で@Service(value = "サービス実装用のAnnotation") をつけてInjectionすること<br>
 *
 * @param <F>
 *     出力対象データ
 */
public interface ExcelDownloadService<F> {

	/**
	 * メイン処理<br>
	 * 継承先で詳細を書く<br>
	 * 渡したいデータを引数に指定する<br>
	 *
	 * @param f
	 *     出力対象データ
	 * @return View
	 * @throws BaseException
	 */
	View execute(F f) throws BaseException;

	/**
	 * Excel設定情報を取得<br>
	 *
	 * @return
	 */
//	default ExcelConfig getExcelConfig(HealthInfoFileSetting healthInfoFileSetting) {
//		ExcelConfig conf = new ExcelConfig();
//		conf.setCharset(Charset.UTF_8);
//		conf.setHasHeader(StringUtil.isTrue(healthInfoFileSetting.getHeaderFlag()));
//		conf.setHasFooter(StringUtil.isTrue(healthInfoFileSetting.getFooterFlag()));
//		return conf;
//	}

}