package jp.co.ha.common.io.file.csv.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.csv.model.BaseCsvModel;

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
	 * @return List<T> CSVアップロードモデルリスト
	 * @throws BaseException
	 *     基底例外
	 */
	List<T> execute(MultipartFile uploadFile) throws BaseException;

}
