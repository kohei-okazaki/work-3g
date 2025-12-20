package jp.co.ha.common.validator;

import static jp.co.ha.common.validator.LengthMode.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import jp.co.ha.common.BaseCommonTest;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.validator.annotation.Length;
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
    public void minLengthTest() {

        // =を含むケース
        {
            LOG.debug("#minLengthTest");
            MinEqualsLengthTestBean bean = new MinEqualsLengthTestBean();
            bean.setName("1234");

            ValidateErrorResult result = new BeanValidator<>().validate(bean);
            assertTrue(result.hasError());
        }
        {
            LOG.debug("#minLengthTest");
            MinEqualsLengthTestBean bean = new MinEqualsLengthTestBean();
            bean.setName("123");

            ValidateErrorResult result = new BeanValidator<>().validate(bean);
            assertFalse(result.hasError());
        }
        {
            LOG.debug("#minLengthTest");
            MinEqualsLengthTestBean bean = new MinEqualsLengthTestBean();
            bean.setName("12");

            ValidateErrorResult result = new BeanValidator<>().validate(bean);
            assertFalse(result.hasError());
        }

        // =を含まないケース
        {
            LOG.debug("#minLengthTest");
            MinLengthTestBean bean = new MinLengthTestBean();
            bean.setName("1234");

            ValidateErrorResult result = new BeanValidator<>().validate(bean);
            assertTrue(result.hasError());
        }
        {
            LOG.debug("#minLengthTest");
            MinLengthTestBean bean = new MinLengthTestBean();
            bean.setName("123");

            ValidateErrorResult result = new BeanValidator<>().validate(bean);
            assertTrue(result.hasError());
        }
        {
            LOG.debug("#minLengthTest");
            MinLengthTestBean bean = new MinLengthTestBean();
            bean.setName("12");

            ValidateErrorResult result = new BeanValidator<>().validate(bean);
            assertFalse(result.hasError());
        }
    }

    /**
     * {@linkplain BeanValidator#validate}
     */
    @Test
    public void maxLengthTest() {

        // =を含むケース
        {
            LOG.debug("#maxLengthTest");
            MaxEqualsLengthTestBean bean = new MaxEqualsLengthTestBean();
            bean.setName("1234");

            ValidateErrorResult result = new BeanValidator<>().validate(bean);
            assertFalse(result.hasError());
        }
        {
            LOG.debug("#maxLengthTest");
            MaxEqualsLengthTestBean bean = new MaxEqualsLengthTestBean();
            bean.setName("123");

            ValidateErrorResult result = new BeanValidator<>().validate(bean);
            assertFalse(result.hasError());
        }
        {
            LOG.debug("#maxLengthTest");
            MaxEqualsLengthTestBean bean = new MaxEqualsLengthTestBean();
            bean.setName("12");

            ValidateErrorResult result = new BeanValidator<>().validate(bean);
            assertTrue(result.hasError());
        }

        // =を含まないケース
        {
            LOG.debug("#maxLengthTest");
            MaxLengthTestBean bean = new MaxLengthTestBean();
            bean.setName("1234");

            ValidateErrorResult result = new BeanValidator<>().validate(bean);
            assertFalse(result.hasError());
        }
        {
            LOG.debug("#maxLengthTest");
            MaxLengthTestBean bean = new MaxLengthTestBean();
            bean.setName("123");

            ValidateErrorResult result = new BeanValidator<>().validate(bean);
            assertTrue(result.hasError());
        }
        {
            LOG.debug("#maxLengthTest");
            MaxLengthTestBean bean = new MaxLengthTestBean();
            bean.setName("12");

            ValidateErrorResult result = new BeanValidator<>().validate(bean);
            assertTrue(result.hasError());
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
     * MinEqualsLengthTestBean<br>
     * XX以下のテストで使用
     *
     * @version 1.0.0
     */
    static class MinEqualsLengthTestBean {

        /** name */
        @Length(length = 3, mode = LESS_EQUAL)
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
     * MinLengthTestBean<br>
     * XX未満のテストで使用
     *
     * @version 1.0.0
     */
    static class MinLengthTestBean {

        /** name */
        @Length(length = 3, mode = LESS_THAN)
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
     * MaxEqualsLengthTestBean<br>
     * XX以上のテストで使用
     *
     * @version 1.0.0
     */
    static class MaxEqualsLengthTestBean {

        /** name */
        @Length(length = 3, mode = GREATER_EQUAL)
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
     * MaxLengthTestBean<br>
     * XXより大きいのテストで使用
     *
     * @version 1.0.0
     */
    static class MaxLengthTestBean {

        /** name */
        @Length(length = 3, mode = GREATER_THAN)
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
