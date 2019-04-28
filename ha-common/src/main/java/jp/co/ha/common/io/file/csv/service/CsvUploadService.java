package jp.co.ha.common.io.file.csv.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.csv.model.BaseCsvModel;

/**
 * CSVアップロードサービスインターフェース
 *
 * @param <T>
 *     CSVモデル
 */
public interface CsvUploadService<T extends BaseCsvModel> {

	/**
	 * アップロードファイルを解析し、CSVモデルリストを返す
	 *
	 * @param uploadFile
	 *     アップロードファイル
	 * @return List<T> CSVアップロードモデルリスト
	 * @throws BaseException
	 *     基底例外
	 */
	List<T> upload(MultipartFile uploadFile) throws BaseException;

}
