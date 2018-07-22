package jp.co.ha.web.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jp.co.ha.common.exception.AppIOException;
import jp.co.ha.common.exception.BaseAppException;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.file.csv.reader.CsvReader;
import jp.co.ha.common.file.csv.service.CsvUploadService;
import jp.co.ha.web.file.csv.model.HealthInfoCsvUploadModel;
import jp.co.ha.web.file.csv.reader.HealthInfoCsvReader;

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
	public List<HealthInfoCsvUploadModel> execute(MultipartFile uploadFile) throws BaseAppException {
		CsvReader<HealthInfoCsvUploadModel> reader = new HealthInfoCsvReader();
		List<String> list = null;
		try {
			list = toList(uploadFile.getInputStream());
		} catch (IOException e) {
			throw new AppIOException(ErrorCode.FILE_UPLOAD_ERROR, "ファイルを読み込めませんでした。ファイル名" + uploadFile.getOriginalFilename());
		}
		return list.stream().map(data -> reader.read(data)).collect(Collectors.toList());
	}

}
