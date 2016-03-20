package one.thing.well.gymtimekeeper.persist.sessionsummary;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import one.thing.well.gymtimekeeper.persist.FileWritingConstants;

public class SessionSummaryEntry {

    private Date sessionStartTime;

    private Date sessionEndTime;

    public SessionSummaryEntry(Date sessionStartTime, Date sessionEndTime) {
        this.sessionStartTime = sessionStartTime;
        this.sessionEndTime = sessionEndTime;
    }

    public SessionSummaryEntry(String sessionSummaryString) throws ParseException {
        parseSessionSummaryString(sessionSummaryString);
    }

    private void parseSessionSummaryString(String sessionSummaryString) throws ParseException {
        String[] fields = sessionSummaryString.split(FileWritingConstants.SPLITTER);
        DateFormat dateFormat = new SimpleDateFormat(FileWritingConstants.DATE_FORMAT);
        sessionStartTime = dateFormat.parse(fields[0]);
        sessionEndTime = dateFormat.parse(fields[1]);
    }

    public Date getSessionStartTime() {
        return sessionStartTime;
    }

    public Date getSessionEndTime() {
        return sessionEndTime;
    }

    @Override
    public String toString() {
        return sessionStartTime + FileWritingConstants.SPLITTER + sessionEndTime;
    }
}
