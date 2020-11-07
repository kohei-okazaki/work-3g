package jp.co.ha.common.io.file.csv.writer;

import java.io.Closeable;
import java.io.PrintWriter;
import java.util.List;
import java.util.StringJoiner;

import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.common.io.file.csv.annotation.CsvDownloadModel;
import jp.co.ha.common.io.file.csv.model.BaseCsvModel;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.common.util.StringUtil;

/**
 * CSV書込クラス
 *
 * @param <T>
 *     CSVモデル
 * @version 1.0.0
 */
public abstract class CsvWriter<T extends BaseCsvModel> implements Closeable {

    /** CSV設定情報 */
    protected CsvConfig conf;
    /** 出力用PrintWriter */
    protected PrintWriter pw;
    /** 出力データ */
    protected StringJoiner data;

    /**
     * コンストラクタ
     *
     * @param conf
     *     CSV設定情報
     * @param pw
     *     writer
     */
    public CsvWriter(CsvConfig conf, PrintWriter pw) {
        this.conf = conf;
        this.pw = pw;
        this.data = new StringJoiner(StringUtil.NEW_LINE);
    }

    /**
     * メイン処理を実施
     *
     * @param modelList
     *     モデルリスト
     */
    @SuppressWarnings("unchecked")
    public void execute(List<T> modelList) {
        if (this.conf.hasHeader()) {
            // ヘッダを書込
            writeHeader((Class<T>) BeanUtil.getParameterType(this.getClass()));
        }
        // データを書込
        modelList.stream().forEach(e -> writeData(this.data, e));
        if (this.conf.hasFooter()) {
            // フッタを書込
            writeFooter((Class<T>) BeanUtil.getParameterType(this.getClass()));
        }
        this.pw.print(this.data.toString());
    }

    @Override
    public void close() {
        if (BeanUtil.notNull(this.pw)) {
            this.pw.close();
        }
    }

    /**
     * flush処理を行う
     */
    public void flush() {
        if (BeanUtil.notNull(this.pw)) {
            this.pw.flush();
        }
    }

    /**
     * 指定されたデータの書き込み処理を行う
     *
     * @param joiner
     *     StringJoiner
     * @param data
     *     書き込みたいデータ
     */
    protected void write(StringJoiner joiner, String data) {
        // 囲い文字
        String enclosureChar = conf.hasEnclosure() ? conf.getCsvFileChar().getValue()
                : StringUtil.EMPTY;
        joiner.add(enclosureChar + data + enclosureChar);
    }

    /**
     * ヘッダ部を書込
     *
     * @param clazz
     *     CSVモデルクラス型
     */
    protected void writeHeader(Class<T> clazz) {
        StringJoiner sj = new StringJoiner(StringUtil.COMMA);
        getHeaderList(clazz).stream().forEach(e -> write(sj, e));
        data.add(sj.toString());
    }

    /**
     * データ部を書込
     *
     * @param data
     *     出力データ
     * @param model
     *     T CSV出力モデル
     */
    protected abstract void writeData(StringJoiner data, T model);

    /**
     * フッタ部を書込
     *
     * @param clazz
     *     CSVモデルクラス型
     */
    protected void writeFooter(Class<T> clazz) {
        StringJoiner sj = new StringJoiner(StringUtil.COMMA);
        getFooterList(clazz).stream().forEach(e -> write(sj, e));
        this.data.add(sj.toString());
    }

    /**
     * ヘッダ名を取得する
     *
     * @param clazz
     *     CsvModelアノテーションのついたクラス型
     * @return ヘッダーリスト
     */
    protected List<String> getHeaderList(Class<?> clazz) {
        return CollectionUtil
                .toList(clazz.getAnnotation(CsvDownloadModel.class).headerNames());
    }

    /**
     * フッタ名を取得する
     *
     * @param clazz
     *     CSVModelアノテーションのついたクラス型
     * @return フッターリスト
     */
    protected List<String> getFooterList(Class<?> clazz) {
        return CollectionUtil
                .toList(clazz.getAnnotation(CsvDownloadModel.class).footerNames());
    }

    /**
     * dataを返す
     *
     * @return data
     */
    public StringJoiner getData() {
        return this.data;
    }

}
