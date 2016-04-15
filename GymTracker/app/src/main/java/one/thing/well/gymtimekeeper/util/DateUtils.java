package one.thing.well.gymtimekeeper.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static final String DATE_FORMAT = "dd/MM HH:mm:ss";

    private static DateFormat dateFormat= new SimpleDateFormat(DateUtils.DATE_FORMAT);;

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
        durationInMillis -= durationInMillis - (hours * (60 * 60 * 1000));
        long minutes = durationInMillis / (60 * 1000) % 60;;
        return hours + "h " + minutes + "m";
    }
}
