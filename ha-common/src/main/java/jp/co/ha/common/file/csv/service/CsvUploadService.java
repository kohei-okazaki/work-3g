package jp.co.ha.common.file.csv.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jp.co.ha.common.exception.BaseAppException;
import jp.co.ha.common.file.csv.model.BaseCsvModel;

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

}
