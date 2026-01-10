package jp.co.ha.db.mapper.custom;

import java.time.LocalDateTime;
import java.util.List;

import jp.co.ha.db.entity.ApiLog;
import jp.co.ha.db.mapper.ApiLogMapper;

/**
 * API通信ログ取得Mapper<br>
 * ページング機能を持つ検索を行う場合、{@link ApiLogMapper}ではなく、こちらのMapperを利用すること<br>
 * 以下の機能で呼び出しを想定<br>
 * <ul>
 * <li>日次API通信ログデータ分析連携バッチ</li>
 * </ul>
 * 
 * @version 1.0.0
 */
public interface PagingApiLogMapper {

    /**
     * ページングされたAPI通信ログを検索する
     * 
     * @param from
     *     登録日時(開始)
     * @param to
     *     登録日時(終了)
     * @return API通信ログリスト
     */
    List<ApiLog> selectByRegDate(LocalDateTime from, LocalDateTime to);
}
