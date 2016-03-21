package one.thing.well.gymtimekeeper.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static final String DATE_FORMAT = "dd/MM HH:mm:ss";

    private static DateFormat dateFormat= new SimpleDateFormat(DateUtils.DATE_FORMAT);;

    public static String formatTime(Date date) {
        return dateFormat.format(date);
    }

    public static final Date parseDateString(String dateString) throws ParseException {
        return dateFormat.parse(dateString);
    }
}
