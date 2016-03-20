package one.thing.well.gymtimekeeper.persist.sessionsummary;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GymSessionSummary {

    private static final String SPLITTER = "#";

    public static final String DATE_FORMAT = "dd/MM HH:mm:ss";

    private Date sessionStartDate;

    private Date sessionEndDate;

    public GymSessionSummary(Date sessionStartDate, Date sessionEndDate) {
        this.sessionStartDate = sessionStartDate;
        this.sessionEndDate = sessionEndDate;
    }

    public GymSessionSummary(String sessionSummaryString) throws ParseException {
        parseSessionSummaryString(sessionSummaryString);
    }

    private void parseSessionSummaryString(String sessionSummaryString) throws ParseException {
        String[] fields = sessionSummaryString.split(SPLITTER);
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        sessionStartDate = dateFormat.parse(fields[0]);
        sessionEndDate = dateFormat.parse(fields[1]);
    }

    public Date getSessionStartDate() {
        return sessionStartDate;
    }

    public Date getSessionEndDate() {
        return sessionEndDate;
    }

    @Override
    public String toString() {
        return sessionStartDate + SPLITTER + sessionEndDate;
    }
}
