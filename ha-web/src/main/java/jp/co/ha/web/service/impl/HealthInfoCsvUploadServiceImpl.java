package jp.co.ha.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jp.co.ha.business.io.file.csv.model.HealthInfoCsvUploadModel;
import jp.co.ha.business.io.file.csv.reader.HealthInfoCsvReader;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.csv.reader.CsvReader;
import jp.co.ha.common.io.file.csv.service.CsvUploadService;
import jp.co.ha.common.type.Charset;

/**
 * 健康情報ファイルアップロードサービス実装クラス
 *
 */
@Service("healthInfoUploadCsv")
public class HealthInfoCsvUploadServiceImpl implements CsvUploadService<HealthInfoCsvUploadModel> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthInfoCsvUploadModel> execute(MultipartFile uploadFile) throws BaseException {
		CsvReader<HealthInfoCsvUploadModel> reader = new HealthInfoCsvReader();
		List<HealthInfoCsvUploadModel> modelList = reader.readMultipartFile(uploadFile, Charset.UTF_8);
		return modelList;
	}

}
