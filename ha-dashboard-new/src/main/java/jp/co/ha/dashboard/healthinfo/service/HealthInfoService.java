package jp.co.ha.dashboard.healthinfo.service;

import java.util.List;

import org.springframework.ui.Model;

import jp.co.ha.business.api.healthinfoapp.response.HealthInfoRegistApiResponse;
import jp.co.ha.business.dto.HealthInfoDto;
import jp.co.ha.business.io.file.csv.model.HealthInfoCsvDownloadModel;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.entity.HealthInfoFileSetting;

/**
 * 健康情報入力画面サービスインターフェース
 *
 * @version 1.0.0
 */
public interface HealthInfoService {

    /**
     * Modelに表示用のメッセージを追加する
     *
     * @param model
     *     Model
     * @param dto
     *     健康情報DTO
     * @param lastHealthInfo
     *     最後に登録した健康情報
     */
    void addModel(Model model, HealthInfoDto dto, HealthInfo lastHealthInfo);

    /**
     * 指定されたユーザIDが初回登録かどうか判定する<br>
     * 初回登録の場合true, それ以外の場合falseを返す<br>
     *
     * @param seqUserId
     *     ユーザID
     * @return 判定結果
     * @throws BaseException
     *     基底例外
     */
    boolean isFirstReg(Long seqUserId) throws BaseException;

    /**
     * CSVモデルリストに変換する
     *
     * @param healthInfoList
     *     健康情報リスト
     * @return CSVモデルリスト
     */
    List<HealthInfoCsvDownloadModel> toModelList(List<HealthInfo> healthInfoList);

    /**
     * 健康情報を登録する
     *
     * @param dto
     *     健康情報DTO
     * @param seqUserId
     *     ユーザID
     * @return HealthInfoRegistResponse
     * @throws BaseException
     *     基底例外
     */
    HealthInfoRegistApiResponse regist(HealthInfoDto dto, Long seqUserId)
            throws BaseException;

    /**
     * 健康情報登録確認メールを送信する
     *
     * @param apiResponse
     *     健康情報登録APIレスポンス
     * @throws BaseException
     *     メール送信に失敗した場合
     */
    void sendHealthInfoMail(HealthInfoRegistApiResponse apiResponse) throws BaseException;

    /**
     * CSV設定情報を取得する
     *
     * @param entity
     *     健康情報ファイル設定
     * @return CsvConfig
     * @throws BaseException
     *     基底例外
     */
    CsvConfig getCsvConfig(HealthInfoFileSetting entity) throws BaseException;

}
