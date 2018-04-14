package jp.co.ha.common.file.csv.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * CSVダウンロードサービスインターフェース<br>
 *
 */
public interface CsvDownloadService {

	/**
	 * メイン処理
	 * @param request
	 * @param response
	 */
	void execute(HttpServletRequest request, HttpServletResponse response);

}
