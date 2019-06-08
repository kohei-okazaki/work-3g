package jp.co.ha.common.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * {@link StringUtil} のjUnit
 *
 */
public class StringUtilTest {

	@Test
	public void isEmptyTest() {
		{
			String str = "";
			assertEquals(StringUtil.isEmpty(str), true);
		}
		{
			String str = null;
			assertEquals(StringUtil.isEmpty(str), true);
		}
		{
			String str = "a";
			assertEquals(StringUtil.isEmpty(str), false);
		}
	}

	@Test
	public void hasValueTest() {
		{
			String str = "";
			assertEquals(StringUtil.hasValue(str), false);
		}
		{
			String str = null;
			assertEquals(StringUtil.hasValue(str), false);
		}
		{
			String str = "a";
			assertEquals(StringUtil.hasValue(str), true);
		}
	}
}
