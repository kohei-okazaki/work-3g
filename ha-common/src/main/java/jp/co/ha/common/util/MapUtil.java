package jp.co.ha.common.util;

import java.math.BigDecimal;
import java.util.Map;

/**
 * MapのUtilクラス
 *
 * @version 1.0.0
 */
public class MapUtil {

    /**
     * プライベートコンストラクタ
     */
    private MapUtil() {
    }

    /**
     * 指定したMapがNullまたは空のMapかどうかを返す
     *
     * @param map
     *     検査対象Map
     * @return Nullまたは空のMapの場合True、それ以外の場合False
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 指定したMapにkeyが存在する場合、そのkeyの値とvalueの和をkeyに設定<br>
     * 存在しない場合、そのkeyとvalueを新しくMapに追加する
     *
     * @param map
     *     対象Map
     * @param key
     *     対象キー
     * @param value
     *     対象値
     */
    public static void addOrReplace(Map<String, BigDecimal> map, String key,
            BigDecimal value) {

        if (map == null || key == null || value == null) {
            return;
        }
        map.compute(key, (k, v) -> map.containsKey(k)
                ? map.get(k).add(value)
                : value);
    }
}
