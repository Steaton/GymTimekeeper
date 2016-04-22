package one.thing.well.gymtimekeeper.util;

import android.test.AndroidTestCase;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class DateUtilsTest extends AndroidTestCase {

    @Test
    public void testAddMinutesToDate() throws Exception {
        Date date = DateUtils.addMinutesToDate(DateUtils.parseDateString("10/03 10:00:00"), 2);
        assertEquals("10/03 10:02:00", DateUtils.formatDate(date));
    }

    @Test
    public void testGetStartOfThisWeek() {
        Date weekStart = DateUtils.getWeekStart();
        System.out.println("Start of Week: " + weekStart);
        assertTrue(weekStart.before(new Date()));
        assertTrue(Calendar.MONDAY == weekStart.getDay() + 1);
        assertTrue(0 == weekStart.getHours());
        assertTrue(0 == weekStart.getMinutes());
        assertTrue(0 == weekStart.getSeconds());
    }
}