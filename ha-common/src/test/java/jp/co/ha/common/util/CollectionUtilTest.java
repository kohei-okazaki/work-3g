package jp.co.ha.common.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import jp.co.ha.common.BaseCommonTest;

/**
 * {@linkplain CollectionUtil} のjUnit
 *
 * @version 1.0.0
 */
public class CollectionUtilTest extends BaseCommonTest {

    /**
     * {@linkplain CollectionUtil#existsCount}
     */
    @Test
    public void existsCountTest() {
        {
            List<String> list = Arrays.asList("");
            int count = 1;
            assertEquals(CollectionUtil.existsCount(list, count), true);
        }
        {
            List<String> list = Arrays.asList("1", "2");
            int count = 1;
            assertEquals(CollectionUtil.existsCount(list, count), false);
        }
        {
            List<String> list = null;
            int count = 1;
            assertEquals(CollectionUtil.existsCount(list, count), false);
        }
    }

    /**
     * {@linkplain CollectionUtil#isEmpty}
     */
    @Test
    public void isEmptyTest() {
        {
            List<String> list = null;
            assertEquals(CollectionUtil.isEmpty(list), true);
        }
        {
            List<String> list = new ArrayList<>();
            assertEquals(CollectionUtil.isEmpty(list), true);
        }
        {
            List<String> list = Arrays.asList("1");
            assertEquals(CollectionUtil.isEmpty(list), false);
        }
        {
            List<String> list = Arrays.asList("1", "2");
            assertEquals(CollectionUtil.isEmpty(list), false);
        }
    }

    /**
     * {@linkplain CollectionUtil#isMultiple}
     */
    @Test
    public void isMultipleTest() {
        {
            List<String> list = null;
            assertEquals(CollectionUtil.isMultiple(list), false);
        }
        {
            List<String> list = new ArrayList<>();
            assertEquals(CollectionUtil.isMultiple(list), false);
        }
        {
            List<String> list = Arrays.asList("1");
            assertEquals(CollectionUtil.isMultiple(list), false);
        }
        {
            List<String> list = Arrays.asList("1", "2");
            assertEquals(CollectionUtil.isMultiple(list), true);
        }
    }

    /**
     * {@link CollectionUtil#exists}
     */
    @Test
    public void existsTest() {
        {
            List<String> list = null;
            assertEquals(CollectionUtil.exists(list), false);
        }
        {
            List<String> list = new ArrayList<>();
            assertEquals(CollectionUtil.exists(list), false);
        }
        {
            List<String> list = Arrays.asList("1");
            assertEquals(CollectionUtil.exists(list), true);
        }
    }
}
