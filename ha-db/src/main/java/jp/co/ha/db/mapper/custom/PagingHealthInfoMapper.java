package jp.co.ha.db.mapper.custom;

import java.time.LocalDateTime;
import java.util.List;

import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.mapper.HealthInfoMapper;

/**
 * 健康情報取得共通Mapper<br>
 * ページング機能を持つ検索を行う場合、{@link HealthInfoMapper}ではなく、こちらのMapperを利用すること<br>
 * 以下の機能で呼び出しを想定<br>
 * <ul>
 * <li>健康情報連携バッチ</li>
 * <li>月次健康情報集計バッチ</li>
 * <li>日次健康情報データ分析連携バッチ</li>
 * </ul>
 * 
 * @version 1.0.0
 */
public interface PagingHealthInfoMapper {

    /**
     * ページングされた健康情報リストを検索する
     * 
     * @param from
     *     健康情報登録日時(開始)
     * @param to
     *     健康情報登録日時(終了)
     * @return 健康情報リスト
     */
    List<HealthInfo> selectByHealthInfoRegDate(LocalDateTime from, LocalDateTime to);
}
