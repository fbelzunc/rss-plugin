package org.jenkinsci.plugins.rss;

import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.model.Run;
import hudson.util.RunList;
import org.jenkinsci.plugins.rss.LogRecorderManager.RSS;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by Felix on 08/02/15.
 */
public class AbstractProjectActionRssAll implements Action {
    private transient AbstractProject abstractProject;

    public AbstractProjectActionRssAll(AbstractProject abstractProject) {
        this.abstractProject = abstractProject;
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
        rss(req, rsp, " all builds", abstractProject.getBuilds());
    }

    private void rss(StaplerRequest req, StaplerResponse rsp, String suffix, RunList runs) throws IOException, ServletException {
        RSS.forwardToRss(getDisplayName() + suffix, abstractProject.getUrl(), runs.newBuilds(),
                Run.FEED_ADAPTER, req, rsp);
    }
}
