package jp.co.ha.common.type;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import jp.co.ha.common.BaseCommonTest;

/**
 * {@link RegexType} のjUnit
 *
 * @since 1.0
 */
public class RegexTypeTest extends BaseCommonTest {

	/**
	 * 半角数字をテスト
	 */
	@Test
	public void halfNumberTest() {
		{
			List<String> list = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
			for (String str : list) {
				assertEquals(RegexType.HALF_NUMBER.is().test(str), true);
			}
		}
		{
			List<String> list = Arrays.asList("a", "あ", "2f2", "///");
			for (String str : list) {
				assertNotEquals(RegexType.HALF_NUMBER.is().test(str), true);
			}
		}
	}

	/**
	 * 半角数字とピリオドをテスト
	 */
	@Test
	public void halfNumberPeriodTest() {
		{
			List<String> list = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", ".");
			for (String str : list) {
				assertEquals(RegexType.HALF_NUMBER_PERIOD.is().test(str), true);
			}
		}
		{
			List<String> list = Arrays.asList("a", "あ", "2f2", "///");
			for (String str : list) {
				assertNotEquals(RegexType.HALF_NUMBER_PERIOD.is().test(str), true);
			}
		}
	}

	/**
	 * 半角文字をテスト
	 */
	@Test
	public void halfNumberCharTest() {
		{
			List<String> list = Arrays.asList("0", "aa", "AA", "cc", "CcC");
			for (String str : list) {
				assertEquals(RegexType.HALF_CHAR.is().test(str), true);
			}
		}
		{
			List<String> list = Arrays.asList("ア", "あ", "ｱ");
			for (String str : list) {
				assertNotEquals(RegexType.HALF_CHAR.is().test(str), true);
			}
		}
	}
}
