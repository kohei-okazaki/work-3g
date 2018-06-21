package jp.co.ha.common.file.csv.writer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.StringJoiner;

import javax.servlet.http.HttpServletResponse;

import org.springframework.util.MimeTypeUtils;

import jp.co.ha.common.exception.AppIOException;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.file.csv.CsvConfig;
import jp.co.ha.common.file.csv.model.BaseCsvModel;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.CsvUtil;
import jp.co.ha.common.util.StringUtil;

/**
 * CSV書き込み基底クラス<br>
 *
 * @param <M>
 *            CSV出力モデルリスト
 */
public abstract class BaseCsvWriter<M extends BaseCsvModel> {

	/** CSV設定情報 */
	protected CsvConfig conf;
	/** モデルリスト */
	protected List<M> modelList;

	/**
	 * コンストラクタ<br>
	 *
	 * @param conf
	 *            CSV設定情報
	 * @param modelList
	 *            モデルリスト
	 */
	public BaseCsvWriter(CsvConfig conf, List<M> modelList) {
		this.conf = conf;
		this.modelList = modelList;
	}

	/**
	 * メイン処理を実施<br>
	 *
	 * @param response
	 *            HttpServletResponse
	 */
	public void execute(HttpServletResponse response) {

		// 初期化処理
		this.init(response);

		try (PrintWriter writer = response.getWriter()) {
			StringJoiner recordJoiner = new StringJoiner(StringUtil.NEW_LINE);

			if (this.conf.hasHeader()) {
				// ヘッダを書込
				writeHeader(recordJoiner, (Class<M>) BeanUtil.getParameterType(this.getClass()));
			}

			// データを書込
			modelList.stream().forEach(model -> writeData(recordJoiner, model));

			if (this.conf.hasFooter()) {
				// フッタを書込
				writeFooter(recordJoiner, (Class<M>) BeanUtil.getParameterType(this.getClass()));
			}

			writer.print(recordJoiner.toString());

		} catch (AppIOException e) {
			throw new AppIOException(ErrorCode.FILE_WRITE_ERROR, "ファイルの出力処理に失敗しました");
		} catch (IOException e) {
			throw new AppIOException(ErrorCode.FILE_WRITE_ERROR, "ファイルの出力処理に失敗しました");
		}

	}

	/**
	 * 初期処理<br>
	 *
	 * @param response
	 *            HttpServletResponse
	 */
	private void init(HttpServletResponse response) {
		response.setContentType(MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE + ";charset=" + conf.getCharset().toString().toLowerCase());
		response.setHeader("Content-Disposition", "attachment; filename=" + conf.getFileName());
	}

	/**
	 * 指定されたデータの書き込み処理を行う<br>
	 *
	 * @param joiner
	 *            StringJoiner
	 * @param data
	 *            書き込みたいデータ
	 */
	protected void write(StringJoiner joiner, String data) {
		String enclosureChar = conf.hasEnclosure() ? conf.getEnclosureChar() : StringUtil.EMPTY;
		joiner.add(enclosureChar + data + enclosureChar);
	}

	/**
	 * ヘッダを書込
	 *
	 * @param recordJoiner
	 *            StringJoiner
	 * @param clazz
	 *            CSVモデルクラス型
	 */
	protected void writeHeader(StringJoiner recordJoiner, Class<M> clazz) {
		StringJoiner joiner = new StringJoiner(StringUtil.COMMA);
		CsvUtil.getHeaderList(clazz).stream().forEach(headerName -> write(joiner, headerName));
		recordJoiner.add(joiner.toString());
	}

	/**
	 * フッタを書込
	 *
	 * @param recordJoiner
	 *            StringJoiner
	 * @param clazz
	 *            CSVモデルクラス型
	 */
	protected void writeFooter(StringJoiner recordJoiner, Class<M> clazz) {
		StringJoiner joiner = new StringJoiner(StringUtil.COMMA);
		CsvUtil.getFooterList(clazz).stream().forEach(footerName -> write(joiner, footerName));
		recordJoiner.add(joiner.toString());
	}

	/**
	 * データレコードをつめる<br>
	 *
	 * @param recordJoiner
	 *            StringJoiner
	 * @param model
	 *            M CSV出力モデル
	 */
	protected abstract void writeData(StringJoiner recordJoiner, M model);

}
