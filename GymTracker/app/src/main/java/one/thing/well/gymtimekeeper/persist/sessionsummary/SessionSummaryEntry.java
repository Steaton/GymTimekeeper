package one.thing.well.gymtimekeeper.persist.sessionsummary;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import one.thing.well.gymtimekeeper.persist.FileWritingConstants;

public class SessionSummaryEntry {

    private Date sessionStartDate;

    private Date sessionEndDate;

    public SessionSummaryEntry(Date sessionStartDate, Date sessionEndDate) {
        this.sessionStartDate = sessionStartDate;
        this.sessionEndDate = sessionEndDate;
    }

    public SessionSummaryEntry(String sessionSummaryString) throws ParseException {
        parseSessionSummaryString(sessionSummaryString);
    }

    private void parseSessionSummaryString(String sessionSummaryString) throws ParseException {
        String[] fields = sessionSummaryString.split(FileWritingConstants.SPLITTER);
        DateFormat dateFormat = new SimpleDateFormat(FileWritingConstants.DATE_FORMAT);
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
        return sessionStartDate + FileWritingConstants.SPLITTER + sessionEndDate;
    }
}
