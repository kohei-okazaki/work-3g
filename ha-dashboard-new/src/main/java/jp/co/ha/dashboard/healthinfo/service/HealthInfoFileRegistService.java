package jp.co.ha.dashboard.healthinfo.service;

import java.util.List;

import jp.co.ha.business.api.healthinfoapp.response.BaseAppApiResponse.ResultType;
import jp.co.ha.business.io.file.csv.model.HealthInfoCsvUploadModel;
import jp.co.ha.common.exception.BaseException;

/**
 * 健康情報ファイル入力画面サービスインターフェース
 *
 * @version 1.0.0
 */
public interface HealthInfoFileRegistService {

    /**
     * 健康情報CSVアップロードモデルリストに不正なデータが含まれていないかチェックを行う
     *
     * @param modelList
     *     健康情報CSVアップロードモデルリスト
     * @param seqUserId
     *     ユーザID
     * @throws BaseException
     *     基底例外
     */
    void formatCheck(List<HealthInfoCsvUploadModel> modelList, Long seqUserId)
            throws BaseException;

    /**
     * 指定されたモデルリストの登録処理を行う
     *
     * @param modelList
     *     健康情報CSVアップロードモデルリスト
     * @param seqUserId
     *     ユーザID
     * @return 処理結果
     * @throws BaseException
     *     基底例外
     */
    ResultType regist(List<HealthInfoCsvUploadModel> modelList, Long seqUserId)
            throws BaseException;

}
