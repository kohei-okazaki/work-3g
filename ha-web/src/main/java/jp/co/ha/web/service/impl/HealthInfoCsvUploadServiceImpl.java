package jp.co.ha.web.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jp.co.ha.business.io.file.csv.model.HealthInfoCsvUploadModel;
import jp.co.ha.business.io.file.csv.reader.HealthInfoCsvReader;
import jp.co.ha.common.exception.AppIOException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.io.file.csv.reader.CsvReader;
import jp.co.ha.common.io.file.csv.service.CsvUploadService;

/**
 * 健康情報ファイルアップロードサービス実装クラス<br>
 *
 */
@Service("healthInfoUploadCsv")
public class HealthInfoCsvUploadServiceImpl implements CsvUploadService<HealthInfoCsvUploadModel> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthInfoCsvUploadModel> execute(MultipartFile uploadFile) throws BaseException {

		List<String> list = null;
		try (InputStream is = uploadFile.getInputStream()) {
			list = toList(is);
		} catch (IOException e) {
			throw new AppIOException(ErrorCode.FILE_UPLOAD_ERROR, "ファイルを読み込めませんでした。ファイル名" + uploadFile.getOriginalFilename());
		}

		List<HealthInfoCsvUploadModel> modelList = new ArrayList<>();
		CsvReader<HealthInfoCsvUploadModel> reader = new HealthInfoCsvReader();
		for (String data : list) {
			modelList.add(reader.read(data));
		}
		return modelList;
	}

}
