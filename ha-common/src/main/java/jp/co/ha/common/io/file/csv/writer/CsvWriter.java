package jp.co.ha.common.io.file.csv.writer;

import java.io.Closeable;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.common.io.file.csv.annotation.CsvDownloadModel;
import jp.co.ha.common.io.file.csv.model.BaseCsvModel;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.StringUtil;

/**
 * CSV書込クラス<br>
 *
 * @param <T>
 *     CSVモデル
 */
public abstract class CsvWriter<T extends BaseCsvModel> implements Closeable {

	/** CSV設定情報 */
	protected CsvConfig conf;
	/** 出力用PrintWriter */
	protected PrintWriter printWriter;

	/**
	 * コンストラクタ<br>
	 *
	 * @param conf
	 *     CSV設定情報
	 * @param printWriter
	 *     writer
	 */
	public CsvWriter(CsvConfig conf, PrintWriter printWriter) {
		this.conf = conf;
		this.printWriter = printWriter;
	}

	/**
	 * メイン処理を実施<br>
	 *
	 * @param modelList
	 *     モデルリスト
	 */
	@SuppressWarnings("unchecked")
	public void execute(List<T> modelList) {
		StringJoiner record = new StringJoiner(StringUtil.NEW_LINE);
		if (this.conf.hasHeader()) {
			// ヘッダを書込
			writeHeader(record, (Class<T>) BeanUtil.getParameterType(this.getClass()));
		}
		// データを書込
		modelList.stream().forEach(e -> writeData(record, e));
		if (this.conf.hasFooter()) {
			// フッタを書込
			writeFooter(record, (Class<T>) BeanUtil.getParameterType(this.getClass()));
		}
		this.printWriter.print(record.toString());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() {
		if (BeanUtil.notNull(this.printWriter)) {
			this.printWriter.close();
		}
	}

	/**
	 * flush処理を行う<br>
	 */
	public void flush() {
		if (BeanUtil.notNull(this.printWriter)) {
			this.printWriter.flush();
		}
	}

	/**
	 * 指定されたデータの書き込み処理を行う<br>
	 *
	 * @param joiner
	 *     StringJoiner
	 * @param data
	 *     書き込みたいデータ
	 */
	protected void write(StringJoiner joiner, String data) {
		// 囲い文字
		String enclosureChar = conf.hasEnclosure() ? conf.getCsvFileChar().getValue() : StringUtil.EMPTY;
		joiner.add(enclosureChar + data + enclosureChar);
	}

	/**
	 * ヘッダを書込
	 *
	 * @param recordJoiner
	 *     StringJoiner
	 * @param clazz
	 *     CSVモデルクラス型
	 */
	protected void writeHeader(StringJoiner recordJoiner, Class<T> clazz) {
		StringJoiner sj = new StringJoiner(StringUtil.COMMA);
		getHeaderList(clazz).stream().forEach(e -> write(sj, e));
		recordJoiner.add(sj.toString());
	}

	/**
	 * フッタを書込
	 *
	 * @param recordJoiner
	 *     StringJoiner
	 * @param clazz
	 *     CSVモデルクラス型
	 */
	protected void writeFooter(StringJoiner recordJoiner, Class<T> clazz) {
		StringJoiner sj = new StringJoiner(StringUtil.COMMA);
		getFooterList(clazz).stream().forEach(e -> write(sj, e));
		recordJoiner.add(sj.toString());
	}

	/**
	 * データレコードをつめる<br>
	 *
	 * @param recordJoiner
	 *     StringJoiner
	 * @param model
	 *     T CSV出力モデル
	 */
	protected abstract void writeData(StringJoiner recordJoiner, T model);

	/**
	 * ヘッダ名を取得する<br>
	 *
	 * @param clazz
	 *     CsvModelアノテーションのついたクラス型
	 * @return ヘッダ名
	 */
	protected List<String> getHeaderList(Class<?> clazz) {
		List<String> headerList = new ArrayList<>();
		headerList.addAll(List.of(clazz.getAnnotation(CsvDownloadModel.class).headerNames()));
		return headerList;
	}

	/**
	 * フッタ名を取得する<br>
	 *
	 * @param clazz
	 *     CsvModelアノテーションのついたクラス型
	 * @return
	 */
	protected List<String> getFooterList(Class<?> clazz) {
		List<String> footerList = new ArrayList<>();
		footerList.addAll(List.of(clazz.getAnnotation(CsvDownloadModel.class).footerNames()));
		return footerList;
	}

}
