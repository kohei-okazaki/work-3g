package jp.co.ha.common.util;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import jp.co.ha.common.BaseCommonTest;
import jp.co.ha.common.util.DateUtil.DateFormatType;

/**
 * {@linkplain DateUtil} のjUnit
 *
 * @version 1.0.0
 */
public class DateUtilTest extends BaseCommonTest {

    /**
     * {@linkplain DateUtil#toDate}
     */
    @Test
    public void toDateTest() {
        {
            Date date = DateUtil.toDate("hoge");
            assertEquals(null, date);
        }
        {
            Date date1 = DateUtil.toDate("2000/01/01 12:34:56");
            Date date2 = DateUtil.toDate("2000/01/01 12:34:56");
            assertEquals(date1, date2);
        }
        {
            Date date1 = DateUtil.toDate("2000/01/01 12:34:56");
            Date date2 = DateUtil.toDate("2000/01/01 12:34:57");
            assertNotEquals(date1, date2);
        }
    }

    /**
     * {@linkplain DateUtil#isBefore}
     */
    @Test
    public void isBeforeTest() {
        {
            Date before = DateUtil.toDate("2000/01/01 12:34:56");
            Date after = DateUtil.toDate("2000/01/01 12:34:57");
            assertEquals(DateUtil.isBefore(before, after, false), true);
        }
        {
            Date before = DateUtil.toDate("2000/01/01 12:34:56");
            Date after = DateUtil.toDate("2000/01/01 12:34:56");
            assertEquals(DateUtil.isBefore(before, after, true), true);
        }
        {
            Date before = DateUtil.toDate("2000/01/01 12:34:57");
            Date after = DateUtil.toDate("2000/01/01 12:34:56");
            assertEquals(DateUtil.isBefore(before, after, false), false);
        }
    }

    /**
     * {@linkplain DateUtil#isAfter}
     */
    @Test
    public void isAfterTest() {
        {
            Date before = DateUtil.toDate("2000/01/01 12:34:56");
            Date after = DateUtil.toDate("2000/01/01 12:34:57");
            assertEquals(DateUtil.isAfter(before, after, false), false);
        }
        {
            Date before = DateUtil.toDate("2000/01/01 12:34:56");
            Date after = DateUtil.toDate("2000/01/01 12:34:56");
            assertEquals(DateUtil.isAfter(before, after, true), true);
        }
        {
            Date before = DateUtil.toDate("2000/01/01 12:34:57");
            Date after = DateUtil.toDate("2000/01/01 12:34:56");
            assertEquals(DateUtil.isAfter(before, after, false), true);
        }
    }

    /**
     * {@linkplain DateUtil#isSameDate}
     */
    @Test
    public void isSameDateTest() {
        {
            Date before = DateUtil.toDate("2000/01/01 12:34:56");
            Date after = DateUtil.toDate("2000/01/01 12:34:56");
            assertEquals(DateUtil.isSameDate(before, after), true);
        }
        {
            Date before = DateUtil.toDate("2000/01/01 12:34:56");
            Date after = DateUtil.toDate("2000/01/01 12:34:57");
            assertEquals(DateUtil.isSameDate(before, after), false);
        }
    }

    /**
     * {@linkplain DateUtil#isBetWeenDate}
     */
    @Test
    public void isBetWeenDateTest() {
        {
            Date startDate = DateUtil.toDate("2000/01/01 12:34:55");
            Date target = DateUtil.toDate("2000/01/01 12:34:56");
            Date endDate = DateUtil.toDate("2000/01/01 12:34:57");
            assertEquals(DateUtil.isBetWeenDate(startDate, target, endDate), true);
        }
        {
            Date startDate = DateUtil.toDate("2000/01/01 12:34:55");
            Date target = DateUtil.toDate("2000/01/01 12:34:56");
            Date endDate = DateUtil.toDate("2000/01/01 12:34:57");
            assertEquals(DateUtil.isBetWeenDate(endDate, target, startDate), false);
        }

    }

    /**
     * {@linkplain DateUtil#toStartDate}
     */
    @Test
    public void toStartDateTest() {
        {
            Date date1 = DateUtil.toDate("2000/01/01 00:00:00");
            Date date2 = DateUtil.toStartDate(DateUtil.toDate("2000/01/01 12:34:56"));
            assertEquals(date1, date2);
        }
    }

    /**
     * {@linkplain DateUtil#toEndDate}
     */
    @Test
    public void toEndDateTest() {
        {
            Date date1 = DateUtil.toDate("2000/01/01 23:59:59");
            Date date2 = DateUtil.toEndDate(DateUtil.toDate("2000/01/01 12:34:56"));
            assertEquals(date1, date2);
        }
    }

    /**
     * {@linkplain DateUtil#isDate(String, DateFormatType)}
     */
    @Test
    public void testIsDate() {
        {
            String date = "2020101";
            assertFalse("日付形式の想定です", DateUtil.isDate(date, DateFormatType.YYYYMM_NOSEP));
        }
        {
            String date = "202009";
            assertTrue("日付形式の想定です", DateUtil.isDate(date, DateFormatType.YYYYMM_NOSEP));
        }
        {
            String date = "2020/09";
            assertTrue("日付形式の想定です", DateUtil.isDate(date, DateFormatType.YYYYMM));
        }
        {
            String date = "2020/09/01";
            assertTrue("日付形式の想定です", DateUtil.isDate(date, DateFormatType.YYYYMMDD));
        }
    }
}
