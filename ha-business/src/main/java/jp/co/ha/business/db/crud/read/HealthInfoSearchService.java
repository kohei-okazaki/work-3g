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
     *     ユーザID(nullを指定した場合、ユーザIDをwhere句に含めない)
     * @param fromHealthInfoRegDate
     *     健康情報作成日時(開始)
     * @param toHealthInfoRegDate
     *     健康情報作成日時(終了)
     * @param selectOption
     *     {@linkplain SelectOption}
     * @return 健康情報リスト
     */
    List<HealthInfo> findBySeqUserIdBetweenHealthInfoRegDate(Long seqUserId,
            LocalDateTime fromHealthInfoRegDate, LocalDateTime toHealthInfoRegDate,
            SelectOption selectOption);

    /**
     * 指定されたユーザIDと指定された健康情報作成日時の期間内の健康情報リストの件数を返す
     *
     * @param seqUserId
     *     ユーザID
     * @param fromHealthInfoRegDate
     *     健康情報作成日時(開始)
     * @param toHealthInfoRegDate
     *     健康情報作成日時(終了)
     * @return 健康情報リストの件数
     */
    long countBySeqUserIdBetweenHealthInfoRegDate(Long seqUserId,
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
    List<HealthInfo> findByHealthInfoIdAndSeqUserId(Long seqHealthInfoId,
            Long seqUserId);

    /**
     * 指定された健康情報IDとユーザIDと一致する健康情報の件数を返す
     *
     * @param seqHealthInfoId
     *     健康情報ID
     * @param seqUserId
     *     ユーザID
     * @return 健康情報リストの件数
     */
    long countByHealthInfoIdAndSeqUserId(Long seqHealthInfoId,
            Long seqUserId);

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
    List<HealthInfo> findBySeqUserId(Long seqUserId, SelectOption selectOption);

    /**
     * 指定された健康情報IDとユーザIDより健康情報とBMI範囲マスタの複合Entityを検索する
     *
     * @param seqHealthInfoId
     *     健康情報ID
     * @param seqUserId
     *     ユーザID
     * @return 健康情報とBMI範囲マスタの複合Entity
     */
    CompositeHealthInfo findHealthInfoDetail(Long seqHealthInfoId, Long seqUserId);

    /**
     * 健康情報とBMI範囲マスタの複合Entityを検索する
     *
     * @param selectOption
     *     検索オプション
     * @return 健康情報とBMI範囲マスタの複合Entityリスト
     */
    List<CompositeHealthInfo> findHealthInfoDetailList(SelectOption selectOption);

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

    /**
     * 以下の条件で健康情報を検索する
     * <ul>
     * <li>ユーザID = 指定したユーザID</li>
     * <li>健康情報ID < 指定した健康情報ID</li>
     * <li>最後の1件</li>
     * </ul>
     *
     * @param seqHealthInfoId
     *     健康情報ID
     * @param seqUserId
     *     ユーザID
     * @param selectOption
     *     {@linkplain SelectOption}
     * @return 健康情報
     */
    HealthInfo findBySeqUserIdAndLowerSeqHealthInfoId(Long seqHealthInfoId,
            Long seqUserId, SelectOption selectOption);

}
