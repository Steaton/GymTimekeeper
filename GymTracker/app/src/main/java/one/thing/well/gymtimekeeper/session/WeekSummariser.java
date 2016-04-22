package one.thing.well.gymtimekeeper.session;

import java.util.Date;
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

    private long getTotalDurationThisWeek(List<SessionSummaryEntry> sessions) {
        long durationThisWeek = 0;
        for (SessionSummaryEntry session  : sessions) {
            durationThisWeek += durationThisWeek(session);
        }
        return durationThisWeek;
    }

    private long durationThisWeek(SessionSummaryEntry session) {
        Date sessionStartTime = session.getSessionStartTime();
        Date weekStartDate = DateUtils.getWeekStart();
        // starting monday date
        //if ()
        return 0;
    }
}
