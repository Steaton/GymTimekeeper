package one.thing.well.gymtimekeeper.session;

import java.util.List;

import one.thing.well.gymtimekeeper.datastore.sessionsummary.SessionSummaryEntry;
import one.thing.well.gymtimekeeper.datastore.sessionsummary.SessionSummaryFile;
import one.thing.well.gymtimekeeper.util.DateUtils;

public class WeekSummariser {

    private SessionSummaryFile sessionSummaryFile = new SessionSummaryFile();

    public WeekSummariser(SessionSummaryFile sessionSummaryFile) {
        this.sessionSummaryFile = sessionSummaryFile;
    }

    public String thisWeek() {
        List<SessionSummaryEntry> sessions = sessionSummaryFile.getSessionsList();
        long durationThisWeek = getTotalDurationThisWeek(sessions);
        return DateUtils.convertToDuration(durationThisWeek);
    }

    public String today() {
        List<SessionSummaryEntry> sessions = sessionSummaryFile.getSessionsList();
        long durationThisWeek = getTotalDurationToday(sessions);
        return DateUtils.convertToDuration(durationThisWeek);
    }

    private long getTotalDurationToday(List<SessionSummaryEntry> sessions) {
        long durationThisWeek = 0;
        for (SessionSummaryEntry session  : sessions) {
            if (isSessionToday(session)) {
                durationThisWeek += session.durationInMilliseconds();
            }
        }
        return durationThisWeek;
    }

    private boolean isSessionToday(SessionSummaryEntry session) {
        return session.getSessionStartTime().after(DateUtils.getDayStart());
    }

    private long getTotalDurationThisWeek(List<SessionSummaryEntry> sessions) {
        long durationThisWeek = 0;
        for (SessionSummaryEntry session  : sessions) {
            if (isSessionThisWeek(session)) {
                durationThisWeek += session.durationInMilliseconds();
            }
        }
        return durationThisWeek;
    }

    private boolean isSessionThisWeek(SessionSummaryEntry session) {
        return session.getSessionStartTime().after(DateUtils.getWeekStart());
    }
}
