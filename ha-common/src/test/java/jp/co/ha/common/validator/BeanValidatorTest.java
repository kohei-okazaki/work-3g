package jp.co.ha.common.validator;

import static org.junit.Assert.*;

import org.junit.Test;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.test.BaseCommonTest;
import jp.co.ha.common.type.RegixType;
import jp.co.ha.common.validator.annotation.Max;
import jp.co.ha.common.validator.annotation.Min;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;

/**
 * {@link BeanValidator} のjUnit
 */
public class BeanValidatorTest extends BaseCommonTest {

	/** LOG */
	private final static Logger LOG = LoggerFactory.getLogger(BeanValidatorTest.class);

	/**
	 * 妥当性チェック(必須チェック)のケース
	 */
	@Test
	public void requiredTest() {
		{
			LOG.debug("#requiredTest");
			RequiredTestBean bean = new RequiredTestBean();

			ValidateErrorResult result = new BeanValidator<RequiredTestBean>().validate(bean);
			assertEquals(true, result.hasError());
		}
		{
			LOG.debug("#requiredTest");
			RequiredTestBean bean = new RequiredTestBean();
			bean.setName("test");

			ValidateErrorResult result = new BeanValidator<>().validate(bean);
			assertEquals(false, result.hasError());
		}
	}

	/**
	 * 妥当性チェック(最小桁数チェック)のケース
	 */
	@Test
	public void minTest() {

		// =を含むケース
		{
			LOG.debug("#minTest");
			MinEqualsTestBean bean = new MinEqualsTestBean();
			bean.setName("123");

			ValidateErrorResult result = new BeanValidator<>().validate(bean);
			assertEquals(false, result.hasError());
		}
		{
			LOG.debug("#minTest");
			MinEqualsTestBean bean = new MinEqualsTestBean();
			bean.setName("12");

			ValidateErrorResult result = new BeanValidator<>().validate(bean);
			assertEquals(true, result.hasError());
		}

		// =を含まないケース
		{
			LOG.debug("#minTest");
			MinTestBean bean = new MinTestBean();
			bean.setName("1234");

			ValidateErrorResult result = new BeanValidator<>().validate(bean);
			assertEquals(false, result.hasError());
		}
		{
			LOG.debug("#minTest");
			MinTestBean bean = new MinTestBean();
			bean.setName("123");

			ValidateErrorResult result = new BeanValidator<>().validate(bean);
			assertEquals(true, result.hasError());
		}
		{
			LOG.debug("#minTest");
			MinTestBean bean = new MinTestBean();
			bean.setName("12");

			ValidateErrorResult result = new BeanValidator<>().validate(bean);
			assertEquals(true, result.hasError());
		}
	}

	/**
	 * 妥当性チェック(最大桁数チェック)のケース
	 */
	@Test
	public void maxTest() {

		// =を含むケース
		{
			LOG.debug("#maxTest");
			MaxEqualsTestBean bean = new MaxEqualsTestBean();
			bean.setName("123");

			ValidateErrorResult result = new BeanValidator<>().validate(bean);
			assertEquals(false, result.hasError());
		}
		{
			LOG.debug("#maxTest");
			MaxEqualsTestBean bean = new MaxEqualsTestBean();
			bean.setName("1234");

			ValidateErrorResult result = new BeanValidator<>().validate(bean);
			assertEquals(true, result.hasError());
		}

		// =を含まないケース
		{
			LOG.debug("#maxTest");
			MaxTestBean bean = new MaxTestBean();
			bean.setName("1234");

			ValidateErrorResult result = new BeanValidator<>().validate(bean);
			assertEquals(true, result.hasError());
		}
		{
			LOG.debug("#maxTest");
			MaxTestBean bean = new MaxTestBean();
			bean.setName("123");

			ValidateErrorResult result = new BeanValidator<>().validate(bean);
			assertEquals(true, result.hasError());
		}
		{
			LOG.debug("#maxTest");
			MaxTestBean bean = new MaxTestBean();
			bean.setName("12");

			ValidateErrorResult result = new BeanValidator<>().validate(bean);
			assertEquals(false, result.hasError());
		}
	}

	/**
	 * 妥当性チェック(属性チェック)のケース
	 */
	@Test
	public void patternTest() {
		{
			// 半角数字
			{
				LOG.debug("#patternTest");
				HalfNumberPatternTestBean bean = new HalfNumberPatternTestBean();
				bean.setValue("1");

				ValidateErrorResult result = new BeanValidator<>().validate(bean);
				assertEquals(false, result.hasError());
			}
			{
				LOG.debug("#patternTest");
				HalfNumberPatternTestBean bean = new HalfNumberPatternTestBean();
				bean.setValue("a");

				ValidateErrorResult result = new BeanValidator<>().validate(bean);
				assertEquals(true, result.hasError());
			}
			{
				LOG.debug("#patternTest");
				HalfNumberPatternTestBean bean = new HalfNumberPatternTestBean();
				bean.setValue("1a");

				ValidateErrorResult result = new BeanValidator<>().validate(bean);
				assertEquals(true, result.hasError());
			}
		}

		{
			// 半角英数字
			{
				LOG.debug("#patternTest");
				HalfCharPatternTestBean bean = new HalfCharPatternTestBean();
				bean.setValue("1");

				ValidateErrorResult result = new BeanValidator<>().validate(bean);
				assertEquals(false, result.hasError());
			}
			{
				LOG.debug("#patternTest");
				HalfCharPatternTestBean bean = new HalfCharPatternTestBean();
				bean.setValue("a");

				ValidateErrorResult result = new BeanValidator<>().validate(bean);
				assertEquals(false, result.hasError());
			}
			{
				LOG.debug("#patternTest");
				HalfCharPatternTestBean bean = new HalfCharPatternTestBean();
				bean.setValue("1a");

				ValidateErrorResult result = new BeanValidator<>().validate(bean);
				assertEquals(false, result.hasError());
			}
			{
				LOG.debug("#patternTest");
				HalfCharPatternTestBean bean = new HalfCharPatternTestBean();
				bean.setValue("あ");

				ValidateErrorResult result = new BeanValidator<>().validate(bean);
				assertEquals(true, result.hasError());
			}
		}

	}

	private static class RequiredTestBean {

		@Required
		private String name;

		/**
		 * nameを設定する
		 *
		 * @param name
		 *     name
		 *
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * nameを返す
		 *
		 * @return name
		 */
		public String getName() {
			return name;
		}
	}

	private static class MinEqualsTestBean {

		@Min(size = 3)
		private String name;

		/**
		 * nameを設定する
		 *
		 * @param name
		 *     name
		 *
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * nameを返す
		 *
		 * @return name
		 */
		public String getName() {
			return name;
		}
	}

	private static class MinTestBean {

		@Min(size = 3, isEqual = false)
		private String name;

		/**
		 * nameを設定する
		 *
		 * @param name
		 *     name
		 *
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * nameを返す
		 *
		 * @return name
		 */
		public String getName() {
			return name;
		}

	}

	private static class MaxEqualsTestBean {

		@Max(size = 3)
		private String name;

		/**
		 * nameを設定する
		 *
		 * @param name
		 *     name
		 *
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * nameを返す
		 *
		 * @return name
		 */
		public String getName() {
			return name;
		}
	}

	private static class MaxTestBean {

		@Max(size = 3, isEqual = false)
		private String name;

		/**
		 * nameを設定する
		 *
		 * @param name
		 *     name
		 *
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * nameを返す
		 *
		 * @return name
		 */
		public String getName() {
			return name;
		}

	}

	private static class HalfNumberPatternTestBean {

		@Pattern(regixPattern = RegixType.HALF_NUMBER)
		private String value;

		/**
		 * valueを設定する
		 *
		 * @param value
		 *     value
		 *
		 */
		public void setValue(String value) {
			this.value = value;
		}

		/**
		 * valueを返す
		 *
		 * @return value
		 */
		public String getValue() {
			return value;
		}

	}

	private static class HalfCharPatternTestBean {

		@Pattern(regixPattern = RegixType.HALF_CHAR)
		private String value;

		/**
		 * valueを設定する
		 *
		 * @param value
		 *     value
		 *
		 */
		public void setValue(String value) {
			this.value = value;
		}

		/**
		 * valueを返す
		 *
		 * @return value
		 */
		public String getValue() {
			return value;
		}

	}
}
