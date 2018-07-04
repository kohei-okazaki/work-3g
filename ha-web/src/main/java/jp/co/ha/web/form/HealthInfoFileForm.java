package jp.co.ha.web.form;

import org.springframework.web.multipart.MultipartFile;

import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.common.web.BaseForm;

/**
 * 健康情報ファイル入力画面フォームクラス<br>
 *
 */
public class HealthInfoFileForm implements BaseForm {

	/** シリアルバージョンUID */
	@Ignore
	private static final long serialVersionUID = 1L;

	/** アップロードファイル */
	@Ignore
	private MultipartFile multipartFile;

	/**
	 * multipartFileを返す<br>
	 *
	 * @return multipartFile アップロードファイル
	 */
	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	/**
	 * multipartFileを設定する<br>
	 *
	 * @param multipartFile
	 *            アップロードファイル
	 */
	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

}
