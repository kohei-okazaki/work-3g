package jp.co.ha.common.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import jp.co.ha.common.BaseCommonTest;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.Charset;
import jp.co.ha.common.util.StringUtil.PaddingType;

/**
 * {@link StringUtil} のjUnit
 *
 * @since 1.0
 */
public class StringUtilTest extends BaseCommonTest {

	/** LOG */
	private static Logger LOG = LoggerFactory.getLogger(StringUtilTest.class);

	/**
	 * {@link StringUtil#toStrList}
	 */
	@Test
	public void toStrListTest() {
		{
			String str = "1,2,3,4";
			List<String> resultList = StringUtil.toStrList(str, StringUtil.COMMA);
			List<String> list = Arrays.asList("1", "2", "3", "4");
			for (int i = 0; i < list.size(); i++) {
				assertEquals(resultList.get(i), list.get(i));
			}
		}
		{
			String str = "1,2,3,4";
			List<String> resultList = StringUtil.toStrList(str, StringUtil.COMMA);
			List<String> list = Arrays.asList("1", "2", "3", "3");
			assertEquals(resultList.get(0), list.get(0));
			assertEquals(resultList.get(1), list.get(1));
			assertEquals(resultList.get(2), list.get(2));
			assertNotEquals(resultList.get(3), list.get(3));
		}
	}

	/**
	 * {@link StringUtil#isEmpty}
	 */
	@Test
	public void isEmptyTest() {
		{
			String str = null;
			assertEquals(StringUtil.isEmpty(str), true);
		}
		{
			String str = "";
			assertEquals(StringUtil.isEmpty(str), true);
		}
		{
			String str = "  ";
			assertEquals(StringUtil.isEmpty(str), true);
		}
		{
			String str = "a";
			assertEquals(StringUtil.isEmpty(str), false);
		}
	}

	/**
	 * {@link StringUtil#isBrank}
	 */
	@Test
	public void isBrankTest() {
		{
			String str = null;
			assertEquals(StringUtil.isBrank(str), true);
		}
		{
			String str = "";
			assertEquals(StringUtil.isBrank(str), true);
		}
		{
			String str = "  ";
			assertEquals(StringUtil.isBrank(str), false);
		}
		{
			String str = "a";
			assertEquals(StringUtil.isBrank(str), false);
		}
	}

	/**
	 * {@link StringUtil#hasValue}
	 */
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

	/**
	 * {@link StringUtil#paddingSpace}
	 */
	@Test
	public void paddingSpaceTest() {
		try {
			{
				String target = "";
				int length = 5;
				PaddingType paddingType = PaddingType.LEFT;
				assertEquals(StringUtil.paddingSpace(target, length, paddingType), "     ");
			}
			{
				String target = "";
				int length = 5;
				PaddingType paddingType = PaddingType.LEFT;
				assertNotEquals(StringUtil.paddingSpace(target, length, paddingType), " ");
			}
			{
				String target = "a";
				int length = 5;
				PaddingType paddingType = PaddingType.LEFT;
				assertEquals(StringUtil.paddingSpace(target, length, paddingType), "a    ");
			}
			{
				String target = "a";
				int length = 5;
				PaddingType paddingType = PaddingType.RIGHT;
				assertEquals(StringUtil.paddingSpace(target, length, paddingType), "    a");
			}
			{
				String target = "12345";
				int length = 1;
				PaddingType paddingType = PaddingType.LEFT;
				assertEquals(StringUtil.paddingSpace(target, length, paddingType), "12345");
			}
			{
				String target = "12345";
				int length = 1;
				PaddingType paddingType = PaddingType.RIGHT;
				assertEquals(StringUtil.paddingSpace(target, length, paddingType), "12345");
			}
		} catch (BaseException e) {
			LOG.error("PaddingTypeの指定が不正", e);
		}

	}

	/**
	 * {@link StringUtil#slice}
	 */
	@Test
	public void testSlice() {
		{
			String str = "あいうえお";
			Charset charset = Charset.SHIFT_JIS;
			int length = 4;
			assertEquals("あい", StringUtil.slice(str, charset, length));
		}
		{
			String str = "あいうえお";
			Charset charset = Charset.SHIFT_JIS;
			int length = 5;
			assertEquals("あい", StringUtil.slice(str, charset, length));
		}
		{
			String str = "";
			Charset charset = Charset.SHIFT_JIS;
			int length = 4;
			assertEquals(null, StringUtil.slice(str, charset, length));
		}
		{
			String str = null;
			Charset charset = Charset.SHIFT_JIS;
			int length = 5;
			assertEquals(null, StringUtil.slice(str, charset, length));
		}

		{
			String str = "あいうえお";
			Charset charset = Charset.UTF_8;
			int length = 4;
			assertEquals("あ", StringUtil.slice(str, charset, length));
		}
		{
			String str = "あいうえお";
			Charset charset = Charset.UTF_8;
			int length = 5;
			assertEquals("あ", StringUtil.slice(str, charset, length));
		}
		{
			String str = "あいうえお";
			Charset charset = Charset.UTF_8;
			int length = 6;
			assertEquals("あい", StringUtil.slice(str, charset, length));
		}
		{
			String str = "あいうえお";
			Charset charset = Charset.UTF_8;
			int length = 7;
			assertEquals("あい", StringUtil.slice(str, charset, length));
		}
	}

	/**
	 * {@link StringUtil#trimFullAndHalfSpace}
	 */
	@Test
	public void testTrimFullAndHalfSpace() {
		{
			String value = null;
			assertEquals(null, StringUtil.trimFullAndHalfSpace(value));
		}
		{
			String value = "";
			assertEquals("", StringUtil.trimFullAndHalfSpace(value));
		}
		{
			String value = null;
			assertEquals(null, StringUtil.trimFullAndHalfSpace(value));
		}
		{
			String value = " 　";
			assertEquals("", StringUtil.trimFullAndHalfSpace(value));
		}
		{
			String value = " 　A 　B 　";
			assertEquals("AB", StringUtil.trimFullAndHalfSpace(value));
		}
	}

}
