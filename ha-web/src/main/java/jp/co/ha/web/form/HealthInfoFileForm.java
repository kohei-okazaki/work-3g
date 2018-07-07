package jp.co.ha.web.form;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.common.web.BaseForm;
import jp.co.ha.web.file.csv.model.HealthInfoUploadModel;

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

	/** 健康情報ファイルモデルリスト */
	private List<HealthInfoUploadModel> modelList;

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

	/**
	 * modelListを返す
	 * @return modelList
	 */
	public List<HealthInfoUploadModel> getModelList() {
		return modelList;
	}

	/**
	 * modelListを設定する
	 * @param modelList 健康情報モデルリスト
	 */
	public void setModelList(List<HealthInfoUploadModel> modelList) {
		this.modelList = modelList;
	}

}
