package org.jenkinsci.plugins.rss.Computer;

import hudson.model.Action;
import hudson.model.Computer;
import hudson.model.Job;
import hudson.model.Run;
import hudson.util.RunList;
import jenkins.model.Jenkins;
import org.jenkinsci.plugins.rss.LogRecorderManager.RSS;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by Felix on 04/02/15.
 */
public class ComputerActionRssAll implements Action {

    private final transient Computer computer;

    public ComputerActionRssAll(Computer computer) {
        this.computer = computer;
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
        rss(req, rsp, " all builds", getBuilds());
    }

    private void rss(StaplerRequest req, StaplerResponse rsp, String suffix,
                     RunList runs) throws IOException, ServletException {
        RSS.forwardToRss(getDisplayName() + suffix, computer.getUrl(), runs.newBuilds(),
                Run.FEED_ADAPTER, req, rsp);
    }

    public RunList getBuilds() {
        return new RunList(Jenkins.getInstance().getAllItems(Job.class)).node(computer.getNode());
    }
}
