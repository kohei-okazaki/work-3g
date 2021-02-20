package jp.co.ha.business.db.crud.read;

import java.time.LocalDateTime;
import java.util.List;

import jp.co.ha.common.db.SelectOption;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.entity.composite.CompositeHealthInfo;
import jp.co.ha.db.entity.composite.CompositeMonthlyRegData;

/**
 * 健康情報検索サービスインターフェース
 *
 * @version 1.0.0
 */
public interface HealthInfoSearchService {

    /**
     * 指定されたユーザIDと指定された健康情報作成日時の期間内の健康情報のリストを返す
     *
     * @param seqUserId
     *     ユーザID
     * @param fromHealthInfoRegDate
     *     YYYYMMDD
     * @param toHealthInfoRegDate
     *     YYYYMMDD
     * @param selectOption
     *     {@linkplain SelectOption}
     * @return 健康情報リスト
     */
    List<HealthInfo> findBySeqUserIdBetweenHealthInfoRegDate(Integer seqUserId,
            LocalDateTime fromHealthInfoRegDate, LocalDateTime toHealthInfoRegDate,
            SelectOption selectOption);

    /**
     * 指定されたユーザIDと指定された健康情報作成日時の期間内の健康情報リストの件数を返す
     *
     * @param seqUserId
     *     ユーザID
     * @param fromHealthInfoRegDate
     *     YYYYMMDD
     * @param toHealthInfoRegDate
     * @return 健康情報リストの件数
     */
    long countBySeqUserIdBetweenHealthInfoRegDate(Integer seqUserId,
            LocalDateTime fromHealthInfoRegDate, LocalDateTime toHealthInfoRegDate);

    /**
     * 指定された健康情報IDとユーザIDと一致する健康情報を返す
     *
     * @param seqHealthInfoId
     *     健康情報ID
     * @param seqUserId
     *     ユーザID
     * @return 健康情報リスト
     */
    List<HealthInfo> findByHealthInfoIdAndSeqUserId(Integer seqHealthInfoId,
            Integer seqUserId);

    /**
     * 指定された健康情報IDとユーザIDと一致する健康情報の件数を返す
     *
     * @param seqHealthInfoId
     *     健康情報ID
     * @param seqUserId
     *     ユーザID
     * @return 健康情報リストの件数
     */
    long countByHealthInfoIdAndSeqUserId(Integer seqHealthInfoId,
            Integer seqUserId);

    /**
     * 指定されたユーザIDの件数を返す
     *
     * @param seqUserId
     *     ユーザID
     * @return 件数
     */
    int getSelectCountBySeqUserId(Integer seqUserId);

    /**
     * 指定されたユーザIDで健康情報を検索する<br>
     * 以下の検索オプションを実行する
     * <ul>
     * <li>ソート処理</li>
     * <li>検索上限</li>
     * </ul>
     *
     * @param seqUserId
     *     ユーザID
     * @param selectOption
     *     {@linkplain SelectOption}
     * @return 健康情報リスト
     */
    List<HealthInfo> findBySeqUserId(Integer seqUserId, SelectOption selectOption);

    /**
     * 指定した開始日時から終了日時の間の健康情報リストを返す
     *
     * @param fromHealthInfoRegDate
     *     開始日時
     * @param toHealthInfoRegDate
     *     終了日時
     * @param selectOption
     *     {@linkplain SelectOption}
     * @return 健康情報リスト
     */
    List<HealthInfo> findByBetweenHealthInfoRegDate(LocalDateTime fromHealthInfoRegDate,
            LocalDateTime toHealthInfoRegDate, SelectOption selectOption);

    /**
     * 指定された健康情報IDとユーザIDより健康情報とBMI範囲マスタの複合Entityを検索する
     *
     * @param seqHealthInfoId
     *     健康情報ID
     * @param seqUserId
     *     ユーザID
     * @return 健康情報とBMI範囲マスタの複合Entity
     */
    CompositeHealthInfo findHealthInfoDetail(Integer seqHealthInfoId, Integer seqUserId);

    /**
     * 健康情報とBMI範囲マスタの複合Entityを検索する
     *
     * @return 健康情報とBMI範囲マスタの複合Entityリスト
     */
    List<CompositeHealthInfo> findHealthInfoDetailList();

    /**
     * 月ごとの健康情報リストを検索する
     *
     * @param from
     *     健康情報登録日時(from)
     * @param to
     *     健康情報登録日時(to)
     * @return 月ごとの健康情報リスト
     */
    List<CompositeMonthlyRegData> findMonthly(LocalDateTime from, LocalDateTime to);

}
