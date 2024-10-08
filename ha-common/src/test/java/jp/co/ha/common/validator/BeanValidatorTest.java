package jp.co.ha.common.validator;

import static org.junit.Assert.*;

import org.junit.Test;

import jp.co.ha.common.BaseCommonTest;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.validator.annotation.Max;
import jp.co.ha.common.validator.annotation.Min;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;

/**
 * {@linkplain BeanValidator} のjUnit
 *
 * @version 1.0.0
 */
public class BeanValidatorTest extends BaseCommonTest {

    /** LOG */
    private final static Logger LOG = LoggerFactory.getLogger(BeanValidatorTest.class);

    /**
     * {@linkplain BeanValidator#validate}
     */
    @Test
    public void requiredTest() {
        {
            LOG.debug("#requiredTest");
            RequiredTestBean bean = new RequiredTestBean();

            ValidateErrorResult result = new BeanValidator<RequiredTestBean>()
                    .validate(bean);
            assertTrue(result.hasError());
        }
        {
            LOG.debug("#requiredTest");
            RequiredTestBean bean = new RequiredTestBean();
            bean.setName("test");

            ValidateErrorResult result = new BeanValidator<>().validate(bean);
            assertFalse(result.hasError());
        }
    }

    /**
     * {@linkplain BeanValidator#validate}
     */
    @Test
    public void minTest() {

        // =を含むケース
        {
            LOG.debug("#minTest");
            MinEqualsTestBean bean = new MinEqualsTestBean();
            bean.setName("123");

            ValidateErrorResult result = new BeanValidator<>().validate(bean);
            assertFalse(result.hasError());
        }
        {
            LOG.debug("#minTest");
            MinEqualsTestBean bean = new MinEqualsTestBean();
            bean.setName("12");

            ValidateErrorResult result = new BeanValidator<>().validate(bean);
            assertTrue(result.hasError());
        }

        // =を含まないケース
        {
            LOG.debug("#minTest");
            MinTestBean bean = new MinTestBean();
            bean.setName("1234");

            ValidateErrorResult result = new BeanValidator<>().validate(bean);
            assertFalse(result.hasError());
        }
        {
            LOG.debug("#minTest");
            MinTestBean bean = new MinTestBean();
            bean.setName("123");

            ValidateErrorResult result = new BeanValidator<>().validate(bean);
            assertTrue(result.hasError());
        }
        {
            LOG.debug("#minTest");
            MinTestBean bean = new MinTestBean();
            bean.setName("12");

            ValidateErrorResult result = new BeanValidator<>().validate(bean);
            assertTrue(result.hasError());
        }
    }

    /**
     * {@linkplain BeanValidator#validate}
     */
    @Test
    public void maxTest() {

        // =を含むケース
        {
            LOG.debug("#maxTest");
            MaxEqualsTestBean bean = new MaxEqualsTestBean();
            bean.setName("123");

            ValidateErrorResult result = new BeanValidator<>().validate(bean);
            assertFalse(result.hasError());
        }
        {
            LOG.debug("#maxTest");
            MaxEqualsTestBean bean = new MaxEqualsTestBean();
            bean.setName("1234");

            ValidateErrorResult result = new BeanValidator<>().validate(bean);
            assertTrue(result.hasError());
        }

        // =を含まないケース
        {
            LOG.debug("#maxTest");
            MaxTestBean bean = new MaxTestBean();
            bean.setName("1234");

            ValidateErrorResult result = new BeanValidator<>().validate(bean);
            assertTrue(result.hasError());
        }
        {
            LOG.debug("#maxTest");
            MaxTestBean bean = new MaxTestBean();
            bean.setName("123");

            ValidateErrorResult result = new BeanValidator<>().validate(bean);
            assertTrue(result.hasError());
        }
        {
            LOG.debug("#maxTest");
            MaxTestBean bean = new MaxTestBean();
            bean.setName("12");

            ValidateErrorResult result = new BeanValidator<>().validate(bean);
            assertFalse(result.hasError());
        }
    }

    /**
     * {@linkplain BeanValidator#validate}
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
                assertFalse(result.hasError());
            }
            {
                LOG.debug("#patternTest");
                HalfNumberPatternTestBean bean = new HalfNumberPatternTestBean();
                bean.setValue("a");

                ValidateErrorResult result = new BeanValidator<>().validate(bean);
                assertTrue(result.hasError());
            }
            {
                LOG.debug("#patternTest");
                HalfNumberPatternTestBean bean = new HalfNumberPatternTestBean();
                bean.setValue("1a");

                ValidateErrorResult result = new BeanValidator<>().validate(bean);
                assertTrue(result.hasError());
            }
        }

        {
            // 半角英数字
            {
                LOG.debug("#patternTest");
                HalfCharPatternTestBean bean = new HalfCharPatternTestBean();
                bean.setValue("1");

                ValidateErrorResult result = new BeanValidator<>().validate(bean);
                assertFalse(result.hasError());
            }
            {
                LOG.debug("#patternTest");
                HalfCharPatternTestBean bean = new HalfCharPatternTestBean();
                bean.setValue("a");

                ValidateErrorResult result = new BeanValidator<>().validate(bean);
                assertFalse(result.hasError());
            }
            {
                LOG.debug("#patternTest");
                HalfCharPatternTestBean bean = new HalfCharPatternTestBean();
                bean.setValue("1a");

                ValidateErrorResult result = new BeanValidator<>().validate(bean);
                assertFalse(result.hasError());
            }
            {
                LOG.debug("#patternTest");
                HalfCharPatternTestBean bean = new HalfCharPatternTestBean();
                bean.setValue("あ");

                ValidateErrorResult result = new BeanValidator<>().validate(bean);
                assertTrue(result.hasError());
            }
        }

    }

    /**
     * RequiredTestBean
     *
     * @version 1.0.0
     */
    static class RequiredTestBean {

        /** name */
        @Required
        private String name;

        /**
         * nameを設定する
         *
         * @param name
         *     name
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

    /**
     * MinEqualsTestBean
     *
     * @version 1.0.0
     */
    static class MinEqualsTestBean {

        /** name */
        @Min(size = 3)
        private String name;

        /**
         * nameを設定する
         *
         * @param name
         *     name
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

    /**
     * MinTestBean
     *
     * @version 1.0.0
     */
    static class MinTestBean {

        /** name */
        @Min(size = 3, isEqual = false)
        private String name;

        /**
         * nameを設定する
         *
         * @param name
         *     name
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

    /**
     * MaxEqualsTestBean
     *
     * @version 1.0.0
     */
    static class MaxEqualsTestBean {

        /** name */
        @Max(size = 3)
        private String name;

        /**
         * nameを設定する
         *
         * @param name
         *     name
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

    /**
     * MaxTestBean
     *
     * @version 1.0.0
     */
    static class MaxTestBean {

        /** name */
        @Max(size = 3, isEqual = false)
        private String name;

        /**
         * nameを設定する
         *
         * @param name
         *     name
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

    /**
     * HalfNumberPatternTestBean
     *
     * @version 1.0.0
     */
    static class HalfNumberPatternTestBean {

        /** name */
        @Pattern(regixPattern = RegexType.HALF_NUMBER)
        private String value;

        /**
         * valueを設定する
         *
         * @param value
         *     value
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

    /**
     * HalfCharPatternTestBean
     *
     * @version 1.0.0
     */
    static class HalfCharPatternTestBean {

        /** name */
        @Pattern(regixPattern = RegexType.HALF_CHAR)
        private String value;

        /**
         * valueを設定する
         *
         * @param value
         *     value
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
