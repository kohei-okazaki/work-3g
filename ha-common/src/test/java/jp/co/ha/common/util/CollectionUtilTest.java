package jp.co.ha.common.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * {@link CollectionUtil} のjUnit
 *
 */
public class CollectionUtilTest {

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
}
