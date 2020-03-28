package jp.co.ha.dashboard.healthinfo.form;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jp.co.ha.business.io.file.csv.model.HealthInfoCsvUploadModel;
import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.web.form.BaseForm;

/**
 * 健康情報ファイル入力画面フォームクラス
 *
 * @version 1.0.0
 */
public class HealthInfoFileForm implements BaseForm {

    /** アップロードファイル */
    @Ignore
    @Required(message = "アップロードファイルが未入力です")
    private MultipartFile multipartFile;
    /** 健康情報ファイルモデルリスト */
    private List<HealthInfoCsvUploadModel> modelList;

    /**
     * multipartFileを返す
     *
     * @return multipartFile
     */
    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    /**
     * multipartFileを設定する
     *
     * @param multipartFile
     *     アップロードファイル
     */
    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    /**
     * modelListを返す
     *
     * @return modelList
     */
    public List<HealthInfoCsvUploadModel> getModelList() {
        return modelList;
    }

    /**
     * modelListを設定する
     *
     * @param modelList
     *     健康情報モデルリスト
     */
    public void setModelList(List<HealthInfoCsvUploadModel> modelList) {
        this.modelList = modelList;
    }

}
