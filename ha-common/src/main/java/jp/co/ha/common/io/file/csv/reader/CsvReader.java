package jp.co.ha.common.io.file.csv.reader;

import static jp.co.ha.common.exception.CommonErrorCode.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.io.file.csv.model.BaseCsvModel;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.Charset;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.BeanUtil.AccessorType;
import jp.co.ha.common.util.StringUtil;

/**
 * CSV読込クラス
 *
 * @param <T>
 *     CSVモデル
 * @version 1.0.0
 */
public abstract class CsvReader<T extends BaseCsvModel> {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(CsvReader.class);

    /**
     * 指定された{@linkplain InputStream}を読み込み、CSVモデルリストを返す
     *
     * @param is
     *     {@linkplain InputStream}
     * @param charset
     *     文字コード
     * @return CSVモデルリスト
     * @throws BaseException
     *     基底例外
     */
    public List<T> readInputStream(InputStream is, Charset charset) throws BaseException {

        List<T> modelList = new ArrayList<>();
        try (InputStreamReader isr = new InputStreamReader(is, charset.getValue());
                BufferedReader br = new BufferedReader(isr)) {
            String record;
            while (BeanUtil.notNull(record = br.readLine())) {
                modelList.add(this.read(record));
            }
        } catch (UnsupportedEncodingException e) {
            throw new SystemException(FILE_READING_ERROR,
                    "指定した文字コードが無効です:" + charset.getValue(), e);
        } catch (IOException e) {
            throw new SystemException(FILE_READING_ERROR, "ファイルの読込に失敗しました。", e);
        }

        return modelList;
    }

    /**
     * 指定された{@linkplain MultipartFile}を読み込み、CSVモデルリストを返す
     *
     * @param uploadFile
     *     {@linkplain MultipartFile}
     * @param charset
     *     文字コード
     * @return CSVモデルリスト
     * @throws BaseException
     *     ファイルの読込に失敗した場合
     * @see CsvReader#readInputStream(InputStream, Charset)
     */
    public List<T> readMultipartFile(MultipartFile uploadFile, Charset charset)
            throws BaseException {

        try (InputStream is = uploadFile.getInputStream()) {
            return readInputStream(is, charset);
        } catch (IOException e) {
            throw new SystemException(FILE_READING_ERROR, "ファイルの読込に失敗しました。", e);
        }

    }

    /**
     * 1行読込を行う
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
        List<String> colList = BeanUtil.getFieldList(clazz).stream().map(e -> e.getName())
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
                        Method setter = BeanUtil.getAccessor(f.getName(), clazz,
                                AccessorType.SETTER);
                        setter.invoke(model, data);
                    }
                }
            }
        } catch (Exception e) {
            throw new SystemException(UNEXPECTED_ERROR, "Beanの生成に失敗しました", e);
        }

        LOG.debugBean(model);
        return model;
    }

    /**
     * ファイル内の長さチェックを行う
     *
     * @param colList
     *     カラムリスト
     * @param dataList
     *     データリスト
     * @throws BaseException
     *     基底例外
     */
    private void checkFileLength(List<String> colList, List<String> dataList)
            throws BaseException {
        if (dataList.size() != colList.size()) {
            throw new SystemException(FILE_UPLOAD_ERROR, "CSV1行あたりのレコードが一致しません。");
        }
    }

}
