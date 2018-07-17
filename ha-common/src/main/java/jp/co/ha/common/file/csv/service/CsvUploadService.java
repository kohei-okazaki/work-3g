package jp.co.ha.common.file.csv.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jp.co.ha.common.exception.AppIOException;
import jp.co.ha.common.exception.BaseAppException;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.file.csv.model.BaseCsvModel;
import jp.co.ha.common.util.BeanUtil;

/**
 * CSVアップロードサービスインターフェース<br>
 *
 * @param <T>
 *     CSVモデル
 */
public interface CsvUploadService<T extends BaseCsvModel> {

	/**
	 * アップロードファイルを解析し、CSVモデルリストを返す<br>
	 *
	 * @param uploadFile
	 *     アップロードファイル
	 * @return T CSVアップロードモデルリスト
	 * @throws BaseAppException
	 *     アプリ例外
	 */
	List<T> execute(MultipartFile uploadFile) throws BaseAppException;


	/**
	 * 入力されたファイルをリストで返す<br>
	 *
	 * @param is
	 *     InputStream
	 * @return
	 * @throws AppIOException
	 */
	default public List<String> toList(InputStream is) throws AppIOException {

		List<String> list = new ArrayList<String>();
		try (InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr)) {
			String line;
			while (BeanUtil.notNull(line = br.readLine())) {
				list.add(line);
			}
		} catch (IOException e) {
			throw new AppIOException(ErrorCode.FILE_READING_ERROR, "ファイルの読込に失敗しました。");
		}
		return list;
	}
}
