package jp.co.ha.common.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

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

	}
}
