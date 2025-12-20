package jp.co.ha.common.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

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
            assertTrue(CollectionUtil.existsCount(list, count));
        }
        {
            List<String> list = Arrays.asList("1", "2");
            int count = 1;
            assertFalse(CollectionUtil.existsCount(list, count));
        }
        {
            List<String> list = null;
            int count = 1;
            assertFalse(CollectionUtil.existsCount(list, count));
        }
    }

    /**
     * {@linkplain CollectionUtil#isEmpty}
     */
    @Test
    public void isEmptyTest() {
        {
            List<String> list = null;
            assertTrue(CollectionUtil.isEmpty(list));
        }
        {
            List<String> list = new ArrayList<>();
            assertTrue(CollectionUtil.isEmpty(list));
        }
        {
            List<String> list = Arrays.asList("1");
            assertFalse(CollectionUtil.isEmpty(list));
        }
        {
            List<String> list = Arrays.asList("1", "2");
            assertFalse(CollectionUtil.isEmpty(list));
        }
    }

    /**
     * {@linkplain CollectionUtil#isMultiple}
     */
    @Test
    public void isMultipleTest() {
        {
            List<String> list = null;
            assertFalse(CollectionUtil.isMultiple(list));
        }
        {
            List<String> list = new ArrayList<>();
            assertFalse(CollectionUtil.isMultiple(list));
        }
        {
            List<String> list = Arrays.asList("1");
            assertFalse(CollectionUtil.isMultiple(list));
        }
        {
            List<String> list = Arrays.asList("1", "2");
            assertTrue(CollectionUtil.isMultiple(list));
        }
    }

    /**
     * {@link CollectionUtil#exists}
     */
    @Test
    public void existsTest() {
        {
            List<String> list = null;
            assertFalse(CollectionUtil.exists(list));
        }
        {
            List<String> list = new ArrayList<>();
            assertFalse(CollectionUtil.exists(list));
        }
        {
            List<String> list = Arrays.asList("1");
            assertTrue(CollectionUtil.exists(list));
        }
    }
}
