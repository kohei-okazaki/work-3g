package jp.co.ha.common.io.file.excel.builder;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import jp.co.ha.common.io.file.excel.ExcelConfig;
import jp.co.ha.common.io.file.excel.annotation.ExcelDownloadModel;
import jp.co.ha.common.io.file.excel.model.BaseExcelModel;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.CollectionUtil;

/**
 * Excel出力の基底クラス
 *
 * @param <T>
 *     Excel出力モデル
 * @version 1.0.0
 */
public abstract class BaseExcelBuilder<T extends BaseExcelModel>
        extends AbstractXlsxView {

    /** ヘッダー位置 */
    protected final int HEADER_POSITION = 0;
    /** Excelモデルリスト */
    protected List<T> modelList;
    /** Excel設定情報 */
    protected ExcelConfig conf;

    /**
     * コンストラクタ
     *
     * @param conf
     *     Excel設定情報
     * @param modelList
     *     Excel出力モデルリスト
     */
    public BaseExcelBuilder(ExcelConfig conf, List<T> modelList) {
        this.conf = conf;
        this.modelList = modelList;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        init(response);
        String sheetName = getSheetName(
                (Class<BaseExcelModel>) BeanUtil.getParameterType(this.getClass()));
        Sheet sheet = workbook.createSheet(sheetName);

        if (this.conf.hasHeader()) {
            // ヘッダを書込
            writeHeader(sheet,
                    (Class<BaseExcelModel>) BeanUtil.getParameterType(this.getClass()));
        }

        // データを書込
        writeData(sheet);

        if (this.conf.hasFooter()) {
            // フッタを書込
            writeFooter(sheet,
                    (Class<BaseExcelModel>) BeanUtil.getParameterType(this.getClass()));
        }

    }

    /**
     * ヘッダを書込
     *
     * @param sheet
     *     Sheet
     * @param clazz
     *     Excelモデルインターフェースクラス型
     */
    protected void writeHeader(Sheet sheet, Class<BaseExcelModel> clazz) {
        // ヘッダ名取得
        List<String> header = getHeaderList(clazz);
        Stream.iterate(0, i -> ++i).limit(header.size()).forEach(i -> {
            String headerName = header.get(i);
            Cell cell = getCell(sheet, HEADER_POSITION, i);
            setText(cell, headerName);
        });
    }

    /**
     * データを書込
     *
     * @param sheet
     *     Sheet
     */
    protected abstract void writeData(Sheet sheet);

    /**
     * フッタを書込
     *
     * @param sheet
     *     Sheet
     * @param clazz
     *     Excelモデルインターフェースクラス型
     */
    protected void writeFooter(Sheet sheet, Class<BaseExcelModel> clazz) {
        // フッタ名取得
        List<String> footer = getFooterList(clazz);
        Stream.iterate(0, i -> ++i).limit(footer.size()).forEach(i -> {
            String footerName = footer.get(i);
            Cell cell = null;
            if (conf.hasHeader()) {
                // ヘッダ利用する場合、ヘッダレコード + データレコードの行数にフッタを書き出す
                cell = getCell(sheet, modelList.size() + 1, i);
            } else {
                // ヘッダ利用しない場合、データレコードの行数のみ計算してフッタを書き出す
                cell = getCell(sheet, modelList.size(), i);
            }
            setText(cell, footerName);
        });
    }

    /**
     * 指定されたシートのrow行目のcol番目のセルを返す
     *
     * @param sheet
     *     Sheet
     * @param row
     *     行数
     * @param col
     *     カラム位置
     * @return cell
     */
    protected Cell getCell(Sheet sheet, final int row, int col) {

        // row取得
        Row sheetRow = sheet.getRow(row);
        if (BeanUtil.isNull(sheetRow)) {
            sheetRow = sheet.createRow(row);
        }

        // cell取得
        Cell cell = sheetRow.getCell(col);
        if (BeanUtil.isNull(cell)) {
            cell = sheetRow.createCell(col);
        }
        return cell;
    }

    /**
     * 指定されたセルにtextを設定する
     *
     * @param cell
     *     Cell
     * @param text
     *     テキスト
     */
    protected void setText(Cell cell, String text) {
        cell.setCellValue(text);
    }

    /**
     * シート名を取得する
     *
     * @param clazz
     *     ExcelDownloadModelアノテーションのついたクラス型
     * @return シート名
     */
    protected String getSheetName(Class<BaseExcelModel> clazz) {
        return clazz.getAnnotation(ExcelDownloadModel.class).sheetName();
    }

    /**
     * ヘッダ名を取得する
     *
     * @param clazz
     *     ExcelDownloadModelアノテーションのついたクラス型
     * @return headerList
     */
    protected List<String> getHeaderList(Class<?> clazz) {
        return CollectionUtil
                .toList(clazz.getAnnotation(ExcelDownloadModel.class).headerNames());
    }

    /**
     * フッタ名を取得する
     *
     * @param clazz
     *     ExcelDownloadModelアノテーションのついたクラス型
     * @return footerList
     */
    protected List<String> getFooterList(Class<?> clazz) {
        return CollectionUtil
                .toList(clazz.getAnnotation(ExcelDownloadModel.class).footerNames());
    }

    /**
     * 初期処理
     *
     * @param response
     *     HttpServletResponse
     * @throws UnsupportedEncodingException
     *     Encoding例外
     */
    private void init(HttpServletResponse response) throws UnsupportedEncodingException {

        String fileName = new String(
                "sample.xlsx".getBytes(conf.getCharsetType().getValue()), "ISO-8859-1");
        response.setHeader("Content-Desposition", "attachment; filename=" + fileName);
    }
}
