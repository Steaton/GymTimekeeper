package one.thing.well.gymtimekeeper.datastore.sessionsummary;

import java.util.ArrayList;
import java.util.List;

import one.thing.well.gymtimekeeper.util.DateUtils;

public class SessionSummaryFile {

    private List<SessionSummaryEntry> summaryEntries;

    public SessionSummaryFile() {
        summaryEntries = new ArrayList<SessionSummaryEntry>();
    }

    public void add(SessionSummaryEntry sessionSummaryEntry) {
        summaryEntries.add(sessionSummaryEntry);
    }

    public String buildStartTimeList() {
        StringBuilder dateList = new StringBuilder();
        for (SessionSummaryEntry sessionSummaryEntry : summaryEntries) {
            dateList.append(DateUtils.formatDate(sessionSummaryEntry.getSessionStartTime()) + "\n");
        }
        return dateList.toString();
    }

    public String buildEndTimeList() {
        StringBuilder dateList = new StringBuilder();
        for (SessionSummaryEntry sessionSummaryEntry : summaryEntries) {
            dateList.append(DateUtils.formatDate(sessionSummaryEntry.getSessionEndTime()) + "\n");
        }
        return dateList.toString();
    }

    public String buildSessionDurationList() {
        StringBuilder durationlist = new StringBuilder();
        for (SessionSummaryEntry sessionSummaryEntry : summaryEntries) {
            long durationInSconds = (sessionSummaryEntry.getSessionEndTime().getTime()
                    - sessionSummaryEntry.getSessionEndTime().getTime()) / 1000;
            durationlist.append(durationInSconds + "\n");
        }
        return durationlist.toString();
    }

    public List<SessionSummaryEntry> getSessionsList() {
        return summaryEntries;
    }
}

