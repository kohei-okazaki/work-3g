package jp.co.ha.common.util;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import jp.co.ha.common.BaseCommonTest;

/**
 * {@linkplain MapUtil}のjUnit
 *
 * @version 1.0.0
 */
public class MapUtilTest  extends BaseCommonTest {

    /**
     * {@linkplain MapUtil#isEmpty(Map)}
     */
    @Test
    public void testIsEmpty() {
        {
            Map<String, String> map = new HashMap<>();
            assertTrue(MapUtil.isEmpty(map));
        }
        {
            Map<String, String> map = null;
            assertTrue(MapUtil.isEmpty(map));
        }
        {
            Map<String, String> map = new HashMap<>();
            map.put("", "");
            assertFalse(MapUtil.isEmpty(map));
        }
    }

    /**
     * {@linkplain MapUtil#addOrReplace(Map, String, BigDecimal)}
     */
    @Test
    public void testAddOrReplace() {
        {
            // Mapが空のケース
            Map<String, BigDecimal> map = new HashMap<>();
            String key = "hoge";
            BigDecimal value = BigDecimal.ONE;
            MapUtil.addOrReplace(map, key, value);
            assertEquals(map.get(key), value);
        }
        {
            // Map要素が1つの場合
            Map<String, BigDecimal> map = new HashMap<>();
            String key = "hoge";
            BigDecimal value = BigDecimal.ONE;
            map.put(key, value);
            MapUtil.addOrReplace(map, key, value);
            assertEquals(map.get(key), new BigDecimal("2"));
        }
        {
            // Map要素が2つ かつ 1つ更新の場合
            Map<String, BigDecimal> map = new HashMap<>();
            {
                String key = "hoge";
                BigDecimal value = BigDecimal.ONE;
                map.put(key, value);
            }
            {
                String key = "fuga";
                BigDecimal value = BigDecimal.TEN;
                map.put(key, value);
            }
            String key = "fuga";
            BigDecimal value = BigDecimal.ONE;
            MapUtil.addOrReplace(map, key, value);

            assertEquals(map.get("hoge"), new BigDecimal("1"));
            assertEquals(map.get("fuga"), new BigDecimal("11"));
        }

    }

}
