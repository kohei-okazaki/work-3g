package jp.co.ha.business.db.crud.read;

import java.util.Date;
import java.util.List;

import jp.co.ha.common.db.SelectOption;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 健康情報検索サービスインターフェース
 *
 * @version 1.0.0
 */
public interface HealthInfoSearchService {

    /**
     * 指定されたユーザIDと指定された健康情報作成日時の期間内の健康情報のリストを返す
     *
     * @param userId
     *     ユーザID
     * @param fromHealthInfoRegDate
     *     YYYYMMDD
     * @param toHealthInfoRegDate
     *     YYYYMMDD
     * @param selectOption
     *     検索オプション
     * @return 健康情報のリスト
     */
    List<HealthInfo> findByUserIdBetweenHealthInfoRegDate(String userId,
            Date fromHealthInfoRegDate, Date toHealthInfoRegDate,
            SelectOption selectOption);

    /**
     * 指定された健康情報IDとユーザIDと一致する健康情報を返す
     *
     * @param seqHealthInfoId
     *     健康情報ID
     * @param userId
     *     ユーザID
     * @return 健康情報リスト
     */
    List<HealthInfo> findByHealthInfoIdAndUserId(Integer seqHealthInfoId, String userId);

    /**
     * 指定されたユーザIDの件数を返す
     *
     * @param userId
     *     ユーザID
     * @return 件数
     */
    int getSelectCountByUserId(String userId);

    /**
     * 指定されたユーザIDで健康情報を検索する<br>
     * 以下の検索オプションを実行する
     * <ul>
     * <li>ソート処理</li>
     * <li>検索上限</li>
     * </ul>
     *
     * @param userId
     *     ユーザID
     * @param selectOption
     *     検索オプション
     * @return 健康情報リスト
     */
    List<HealthInfo> findByUserId(String userId, SelectOption selectOption);

}
