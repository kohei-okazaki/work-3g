package jp.co.ha.common.file.csv.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.ha.common.entity.Account;
import jp.co.ha.common.file.csv.CsvConfig;
import jp.co.ha.common.util.CsvUtil;
import jp.co.ha.common.util.StringUtil;

/**
 * CSVダウンロードサービスインターフェース<br>
 *
 */
public interface CsvDownloadService {

	/**
	 * メイン処理
	 *
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 */
	void execute(HttpServletRequest request, HttpServletResponse response);

	/**
	 * CSV設定情報を取得する<br>
	 *
	 * @param fileName
	 *            ファイル名
	 * @param account
	 *            アカウント情報
	 * @return CsvConfig
	 */
	default CsvConfig getCsvConfig(String fileName, Account account) {

		// 囲い文字利用有無
		boolean enclosureFlag = StringUtil.isTrue(account.getFileEnclosureCharFlag());

		CsvConfig csvConfig = new CsvConfig();
		csvConfig.setFileName(fileName);
		csvConfig.setEnclosureChar(CsvUtil.DOBBLE_QUOTE);
		csvConfig.setHasEnclosure(enclosureFlag);

		return csvConfig;
	}

}
