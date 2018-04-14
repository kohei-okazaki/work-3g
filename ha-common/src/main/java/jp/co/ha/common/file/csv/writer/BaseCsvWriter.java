package jp.co.ha.common.file.csv.writer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.StringJoiner;

import javax.servlet.http.HttpServletResponse;

import org.springframework.util.MimeTypeUtils;

import jp.co.ha.common.exception.AppIOException;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.file.csv.model.BaseCsvModel;
import jp.co.ha.common.other.Charset;
import jp.co.ha.common.util.StringUtil;

/**
 * CSV書き込み基底クラス<br>
 * @param <M>CSV出力モデルリスト
 */
public abstract class BaseCsvWriter<M extends BaseCsvModel> {

	/** 囲い文字(デフォルトでは空文字(未指定)) */
	protected String enclosureChar = StringUtil.EMPTY;
	/** モデルリスト */
	protected List<M> modelList;

	/**
	 * コンストラクタ<br>
	 */
	public BaseCsvWriter() {
	}

	/**
	 * コンストラクタ<br>
	 * @param enclosureChar
	 */
	public BaseCsvWriter(String enclosureChar) {
		this.enclosureChar = enclosureChar;
	}

	/**
	 * modelListを返す
	 * @return modelList
	 */
	public List<M> getModelList() {
		return modelList;
	}

	/**
	 * modelListを設定する
	 * @param modelList
	 */
	public void setModelList(List<M> modelList) {
		this.modelList = modelList;
	}

	/**
	 * メイン処理を実施<br>
	 * @param response
	 */
	public void execute(HttpServletResponse response) {

		// ファイル名を取得
		String fileName = getFileName();

		// 初期化処理
		this.init(response, fileName);

		try (PrintWriter writer = response.getWriter()) {
			StringJoiner recordJoiner = new StringJoiner(StringUtil.NEW_LINE);
			// ヘッダーを書込
			writeHeader(recordJoiner);

			// データを書込
			modelList.stream().forEach(model -> writeData(recordJoiner, model));
			writer.print(recordJoiner.toString());

		} catch (AppIOException e) {
			throw new AppIOException(ErrorCode.FILE_WRITE_ERROR, "ファイルの出力処理に失敗しました");
		} catch (IOException e) {
			throw new AppIOException(ErrorCode.FILE_WRITE_ERROR, "ファイルの出力処理に失敗しました");
		}

	}

	/**
	 * 初期処理<br>
	 * @param response
	 * @param fileName
	 */
	protected void init(HttpServletResponse response, String fileName) {

		response.setContentType(MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE + ";charset=" + Charset.UTF_8.toString().toLowerCase());
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

	}

	/**
	 * 指定されたデータの書き込み処理を行う<br>
	 * @param joiner
	 * @param data 書き込みたいデータ
	 */
	protected void write(StringJoiner joiner, String data) {
		joiner.add(enclosureChar + data + enclosureChar);
	}

	/**
	 * ファイル名を取得<br>
	 * @return fileName
	 */
	protected abstract String getFileName();

	/**
	 * ヘッダーレコードをつめる<br>
	 * @param recordJoiner
	 */
	protected abstract void writeHeader(StringJoiner recordJoiner);

	/**
	 * データレコードをつめる<br>
	 * @param recordJoiner
	 * @param model
	 */
	protected abstract void writeData(StringJoiner recordJoiner, M model);

}
