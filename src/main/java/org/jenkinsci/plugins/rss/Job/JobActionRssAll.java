package org.jenkinsci.plugins.rss;

import hudson.model.Action;
import hudson.model.Job;
import hudson.model.Run;
import hudson.util.RunList;
import org.jenkinsci.plugins.rss.LogRecorderManager.RSS;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by Felix on 03/02/15.
 */
public class JobActionRssAll implements Action {

    private final transient Job job;

    public JobActionRssAll(Job job) {
        this.job = job;
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
        return "rssAll";
    }

    public void doIndex(StaplerRequest req, StaplerResponse rsp)
            throws IOException, ServletException {
        rss(req, rsp, " all builds", job.getBuilds());
    }

    private void rss(StaplerRequest req, StaplerResponse rsp, String suffix, RunList runs) throws IOException, ServletException {
        RSS.forwardToRss(getDisplayName() + suffix, job.getUrl(), runs.newBuilds(),
                Run.FEED_ADAPTER, req, rsp);
    }
}
