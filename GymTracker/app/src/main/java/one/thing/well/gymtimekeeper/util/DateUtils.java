package one.thing.well.gymtimekeeper.util;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static final String DATE_FORMAT_STRING = "dd/MM HH:mm";

    public static final String DISPLAY_DATE_FORMAT_STRING = "dd/MM";

    public static final String TIME_FORMAT_STRING = "HH:mm";

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat(DateUtils.DATE_FORMAT_STRING);

    private static final DateFormat TIME_FORMAT = new SimpleDateFormat(DateUtils.TIME_FORMAT_STRING);

    private static final DateFormat DISPLAY_DATE = new SimpleDateFormat(DateUtils.DISPLAY_DATE_FORMAT_STRING);

    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }

    public static String formatTime(Date date) {
        return TIME_FORMAT.format(date);
    }

    public  static  String formatDisplayDate(Date date){
        return  DISPLAY_DATE.format(date);
    }


    public static Date parseDateString(String dateString) throws ParseException {
        return DATE_FORMAT.parse(dateString);

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

    public static Date getWeekStart() {
        DateTime monday = LocalDate.now().withDayOfWeek(DateTimeConstants.MONDAY).toDateTimeAtStartOfDay();
        return monday.toDate();
    }
}
