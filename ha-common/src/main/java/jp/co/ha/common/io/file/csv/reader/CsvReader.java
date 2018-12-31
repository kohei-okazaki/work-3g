package jp.co.ha.common.io.file.csv.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

import jp.co.ha.common.exception.AppIOException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.io.file.csv.model.BaseCsvModel;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.BeanUtil.AccessorType;
import jp.co.ha.common.util.StringUtil;

/**
 * CSV読込クラス<br>
 *
 * @param <T>
 *     CSVモデル
 */
public abstract class CsvReader<T extends BaseCsvModel> {

	/** LOG */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	/**
	 * 指定されたアップロードファイルを読み込み、CSVモデルリストを返す
	 *
	 * @param uploadFile
	 *     アップロードファイル
	 * @return CSVモデルリスト
	 * @throws BaseException
	 *     基底例外
	 */
	public List<T> readMultipartFile(MultipartFile uploadFile) throws BaseException {

		List<T> modelList = new ArrayList<>();
		try (InputStream is = uploadFile.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr)) {
			String record;
			if (BeanUtil.notNull(record = br.readLine())) {
				modelList.add(this.read(record));
			}
		} catch (IOException e) {
			throw new AppIOException(ErrorCode.FILE_READING_ERROR, "ファイルの読込に失敗しました。");
		}

		return modelList;
	}

	/**
	 * 1行読込を行う<br>
	 *
	 * @param record
	 *     レコード
	 * @return CSVモデル
	 * @throws BaseException
	 *     基底例外
	 */
	@SuppressWarnings("unchecked")
	public T read(String record) throws BaseException {

		Class<T> clazz = (Class<T>) BeanUtil.getParameterType(this.getClass());
		List<String> colList = BeanUtil.getFieldList(clazz).stream()
															.map(e -> e.getName())
															.collect(Collectors.toList());
		List<String> dataList = StringUtil.toStrList(record, StringUtil.COMMA);
		checkFileLength(colList, dataList);
		T model = null;
		try {
			model = clazz.getDeclaredConstructor().newInstance();
			for (int i = 0; i < colList.size(); i++) {
				String column = colList.get(i);
				String data = dataList.get(i);
				for (Field f : BeanUtil.getFieldList(clazz)) {
					if (column.equals(f.getName())) {
						// setter呼び出し
						Method setter = BeanUtil.getAccessor(f.getName(), clazz, AccessorType.SETTER);
						setter.invoke(model, data);
					}
				}
			}
		} catch (IllegalAccessException e) {
			LOG.error("", e);
		} catch (IllegalArgumentException e) {
			LOG.error("", e);
		} catch (InvocationTargetException e) {
			LOG.error("", e);
		} catch (InstantiationException e) {
			LOG.error("", e);
		} catch (NoSuchMethodException e) {
			LOG.error("", e);
		} catch (SecurityException e) {
			LOG.error("", e);
		}
		LOG.infoRes(model);
		return model;
	}

	/**
	 * ファイル内の長さチェックを行う<br>
	 *
	 * @param colList
	 *     カラムリスト
	 * @param dataList
	 *     データリスト
	 * @throws BaseException
	 *     基底例外
	 */
	private void checkFileLength(List<String> colList, List<String> dataList) throws BaseException {
		if (dataList.size() != colList.size()) {
			throw new AppIOException(ErrorCode.FILE_UPLOAD_ERROR, "CSV1行あたりのレコードが一致しません。");
		}
	}

}
