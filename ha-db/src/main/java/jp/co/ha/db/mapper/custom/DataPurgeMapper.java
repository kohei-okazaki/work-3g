package jp.co.ha.db.mapper.custom;

import java.time.LocalDateTime;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;

/**
 * データパージMapper<br>
 * 本interfaceでdelete文対象のテーブル名などを決めるため、<br>
 * <code>DataPurgeMapper.xml</code>は作成しない<br>
 * 
 * @version 1.0.0
 */
public interface DataPurgeMapper {

    /**
     * 削除処理
     * 
     * @param tableKey
     *     テーブル名キー
     * @param updateDate
     *     更新日時
     * @return 削除件数
     */
    @DeleteProvider(type = SqlProvider.class, method = "deleteByUpdateDate")
    int deleteExpired(@Param("tableKey") String tableKey,
            @Param("updateDate") LocalDateTime updateDate);

    /**
     * SQL-provider<br>
     * 呼び出し元でテーブル名と更新日時の指定をすること
     * 
     * @version 1.0.0
     */
    public class SqlProvider {

        /**
         * DELETE文作成処理
         * 
         * @param tableNameMap
         *     テーブル名Map
         * @return DELETE文
         */
        public String deleteByUpdateDate(Map<String, String> tableNameMap) {

            String key = tableNameMap.get("tableKey");

            String table = switch (key) {
            case "user" -> "USER";
            case "api_communication_data" -> "API_COMMUNICATION_DATA";
            default -> throw new IllegalArgumentException("invalid tableKey: " + key);
            };

            return "DELETE FROM " + table
                    + " WHERE DELETE_FLAG = 1 AND UPDATE_DATE <= #{updateDate}";
        }
    }
}
