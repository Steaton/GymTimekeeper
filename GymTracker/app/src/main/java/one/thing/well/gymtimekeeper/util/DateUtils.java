package one.thing.well.gymtimekeeper.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static final String DATE_FORMAT = "dd/MM HH:mm";

    private static DateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_FORMAT);;

    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }

    public static Date parseDateString(String dateString) throws ParseException {
        return dateFormat.parse(dateString);
    }

    public static Date addMinutesToDate(Date date, int minutes) {
        long dateInMillis = date.getTime();
        long milliseconds = dateInMillis + (minutes * 60 * 1000);
        Date dateWithAddedMinutes = new Date(milliseconds);
        return dateWithAddedMinutes;
    }

    public static String calculateDuration(Date start, Date end) {
        long durationInMillis = end.getTime() - start.getTime();
        long hours = durationInMillis / (60 * 60 * 1000) % 24;
        long minutes = durationInMillis / (60 * 1000) % 60;;
        return hours + "h " + minutes + "m";
    }

    public static String convertToDuration(long durationThisWeek) {
        return "";
    }

    public static Date getWeekStart() {
        Calendar c = Calendar.getInstance();
        c.set(c.get(Calendar.YEAR) - 1900, c.get(Calendar.MONTH), c.getFirstDayOfWeek(), 0, 0, 0);
        return c.getTime();
    }
}
