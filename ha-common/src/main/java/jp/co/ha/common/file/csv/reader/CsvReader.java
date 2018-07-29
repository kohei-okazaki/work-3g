package jp.co.ha.common.file.csv.reader;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import jp.co.ha.common.exception.AppIOException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.file.csv.model.BaseCsvModel;
import jp.co.ha.common.log.AppLogger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.AccessorType;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.StringUtil;

/**
 * CSV読込クラス<br>
 *
 * @param <T>
 *     CSVモデル
 */
public abstract class CsvReader<T extends BaseCsvModel> {

	private final AppLogger LOG = LoggerFactory.getAppLogger(this.getClass());

	/**
	 * 1行読込を行う<br>
	 *
	 * @param record
	 *     レコード
	 * @return
	 * @throws BaseException
	 *     アプリ例外
	 */
	public T read(String record) throws AppIOException {

		Class<T> clazz = (Class<T>) BeanUtil.getParameterType(this.getClass());
		List<String> colList = BeanUtil.getFieldList(clazz).stream()
															.map(f -> f.getName())
															.collect(Collectors.toList());
		List<String> dataList = StringUtil.toStrList(record, StringUtil.COMMA);
		if (hasFileLengthError(colList, dataList)) {
			throw new AppIOException(ErrorCode.FILE_UPLOAD_ERROR, "CSV1行あたりのレコードが一致しません。");
		}

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
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		LOG.info(model);
		return model;
	}

	/**
	 * ファイル内の長さチェックを行う<br>
	 *
	 * @param colList
	 *     カラムリスト
	 * @param dataList
	 *     データリスト
	 */
	private boolean hasFileLengthError(List<String> colList, List<String> dataList) {
		return dataList.size() != colList.size();
	}

}
