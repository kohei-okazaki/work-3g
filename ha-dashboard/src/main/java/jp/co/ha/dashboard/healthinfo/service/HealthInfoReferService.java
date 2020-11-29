package jp.co.ha.dashboard.healthinfo.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import jp.co.ha.business.dto.HealthInfoReferenceDto;
import jp.co.ha.business.io.file.csv.model.ReferenceCsvDownloadModel;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.db.entity.HealthInfoFileSetting;

/**
 * 健康情報照会画面サービスインターフェース
 *
 * @version 1.0.0
 */
public interface HealthInfoReferService {

    /**
     * 健康情報レスポンスリストを取得する
     *
     * @param dto
     *     健康情報照会DTO
     * @param seqUserId
     *     ユーザID
     * @return 健康情報レスポンスリスト
     * @throws BaseException
     *     基底例外
     */
    long getCount(HealthInfoReferenceDto dto, Integer seqUserId)
            throws BaseException;

    /**
     * 健康情報レスポンスリストを取得する
     *
     * @param dto
     *     健康情報照会DTO
     * @param seqUserId
     *     ユーザID
     * @param pageable
     *     {@linkplain Pageable}
     * @return 健康情報レスポンスリスト
     * @throws BaseException
     *     基底例外
     */
    List<HealthInfoReferenceDto> getHealthInfoResponseList(HealthInfoReferenceDto dto,
            Integer seqUserId, Pageable pageable) throws BaseException;

    /**
     * 結果照会CSVモデルリストに変換する
     *
     * @param seqUserId
     *     ユーザID
     * @param resultList
     *     健康情報照会レスポンスリスト
     * @return modelList
     */
    List<ReferenceCsvDownloadModel> toModelList(Integer seqUserId,
            List<HealthInfoReferenceDto> resultList);

    /**
     * 指定した健康情報ファイル設定からCSV設定情報を返す
     *
     * @param entity
     *     健康情報ファイル設定
     * @return CSV設定情報
     * @throws BaseException
     *     基底例外
     */
    CsvConfig getCsvConfig(HealthInfoFileSetting entity) throws BaseException;

}
