package jp.co.ha.common.file.excel.service;

import org.springframework.web.servlet.View;

import jp.co.ha.common.entity.HealthInfoFileSetting;
import jp.co.ha.common.exception.BaseAppException;
import jp.co.ha.common.file.excel.ExcelConfig;
import jp.co.ha.common.util.Charset;
import jp.co.ha.common.util.StringUtil;

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
	 * @throws BaseAppException
	 */
	View execute(F f) throws BaseAppException;

	/**
	 * Excel設定情報を取得<br>
	 *
	 * @return
	 */
	default ExcelConfig getExcelConfig(HealthInfoFileSetting healthInfoFileSetting) {
		ExcelConfig conf = new ExcelConfig();
		conf.setCharset(Charset.UTF_8);
		conf.setHasHeader(StringUtil.isTrue(healthInfoFileSetting.getHeaderFlag()));
		conf.setHasFooter(StringUtil.isTrue(healthInfoFileSetting.getFooterFlag()));
		return conf;
	}

}