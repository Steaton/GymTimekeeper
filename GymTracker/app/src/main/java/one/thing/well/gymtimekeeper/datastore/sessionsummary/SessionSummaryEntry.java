package one.thing.well.gymtimekeeper.datastore.sessionsummary;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import one.thing.well.gymtimekeeper.datastore.FileConstants;
import one.thing.well.gymtimekeeper.util.DateUtils;

public class SessionSummaryEntry {

    private Date sessionStartTime;

    private Date sessionEndTime;

    public SessionSummaryEntry() {
    }

    public SessionSummaryEntry(Date sessionStartTime, Date sessionEndTime) {
        this.sessionStartTime = sessionStartTime;
        this.sessionEndTime = sessionEndTime;
    }

    public SessionSummaryEntry(String sessionSummaryString) throws ParseException {
        parseSessionSummaryString(sessionSummaryString);
    }

    private void parseSessionSummaryString(String sessionSummaryString) throws ParseException {
        String[] fields = sessionSummaryString.split(FileConstants.SPLITTER);
        DateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_FORMAT_STRING);
        sessionStartTime = dateFormat.parse(fields[0]);
        sessionEndTime = dateFormat.parse(fields[1]);
    }

    public Date getSessionStartTime() {
        return sessionStartTime;
    }

    public Date getSessionEndTime() {
        return sessionEndTime;
    }

    public void setSessionStartTime(Date sessionStartTime) {
        this.sessionStartTime = sessionStartTime;
    }

    public void setSessionEndTime(Date sessionEndTime) {
        this.sessionEndTime = sessionEndTime;
    }

    @Override
    public String toString() {
        return sessionStartTime + FileConstants.SPLITTER + sessionEndTime;
    }

    public String displayString() {
        return DateUtils.formatDisplayDate(sessionStartTime) + ","
                + DateUtils.formatTime(sessionStartTime) + ","
                + DateUtils.formatTime(sessionEndTime) + ","
                + DateUtils.calculateDuration(sessionStartTime, sessionEndTime);
    }

    public long durationInMilliseconds() {
        return sessionEndTime.getTime() - sessionStartTime.getTime();
    }
}
