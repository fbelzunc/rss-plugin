package org.jenkinsci.plugins.rss.LogRecorderManager;

import hudson.FeedAdapter;
import hudson.Functions;
import hudson.logging.LogRecorderManager;
import hudson.model.Action;
import jenkins.model.Jenkins;
import jenkins.model.JenkinsLocationConfiguration;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Created by Felix on 10/02/15.
 */
public class LogRecorderActionRss implements Action {

    private final transient LogRecorderManager logRecorderManager;

    public LogRecorderActionRss(LogRecorderManager logRecorderManager) {
        this.logRecorderManager = logRecorderManager;
    }

    @Override
    public String getIconFileName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public String getUrlName() {
        return "rss";
    }

    /**
     * RSS feed for log entries.
     */
    /*public void doRss(StaplerRequest req, StaplerResponse rsp) throws IOException, ServletException {
        LogRecorderManager.doRss(req, rsp, logRecorder.getLogRecords());
    }*/

    public void doIndex( StaplerRequest req, StaplerResponse rsp ) throws IOException, ServletException {
        doRssLog(req, rsp, Jenkins.logRecords);
    }

    /**
     * Renders the given log recorders as RSS.
     */
    static void doRssLog(StaplerRequest req, StaplerResponse rsp, List<LogRecord> logs) throws IOException, ServletException {
        // filter log records based on the log level
        String level = req.getParameter("level");
        if(level!=null) {
            Level threshold = Level.parse(level);
            List<LogRecord> filtered = new ArrayList<LogRecord>();
            for (LogRecord r : logs) {
                if(r.getLevel().intValue() >= threshold.intValue())
                    filtered.add(r);
            }
            logs = filtered;
        }

        RSS.forwardToRss("Hudson log","", logs, new FeedAdapter<LogRecord>() {
            public String getEntryTitle(LogRecord entry) {
                return entry.getMessage();
            }

            public String getEntryUrl(LogRecord entry) {
                return "log";   // TODO: one URL for one log entry?
            }

            public String getEntryID(LogRecord entry) {
                return String.valueOf(entry.getSequenceNumber());
            }

            public String getEntryDescription(LogRecord entry) {
                return Functions.printLogRecord(entry);
            }

            public Calendar getEntryTimestamp(LogRecord entry) {
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTimeInMillis(entry.getMillis());
                return cal;
            }

            public String getEntryAuthor(LogRecord entry) {
                return JenkinsLocationConfiguration.get().getAdminAddress();
            }
        },req,rsp);
    }

}