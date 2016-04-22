package one.thing.well.gymtimekeeper.util;

import android.test.AndroidTestCase;

import org.junit.Test;

import java.util.Date;

public class DateUtilsTest extends AndroidTestCase {

    @Test
    public void testAddMinutesToDate() throws Exception {
        Date date = DateUtils.addMinutesToDate(DateUtils.parseDateString("10/03 10:00:00"), 2);
        assertEquals("10/03 10:02:00", DateUtils.formatDate(date));
    }

    @Test
    public void testGetStartOfThisWeek() {
        System.out.println("Start of Week: " + DateUtils.getWeekStart());
    }
}