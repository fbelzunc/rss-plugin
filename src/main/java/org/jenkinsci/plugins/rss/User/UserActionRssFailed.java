package org.jenkinsci.plugins.rss.User;

import hudson.FeedAdapter;
import hudson.model.Action;
import hudson.model.Run;
import hudson.model.User;
import hudson.util.RunList;
import org.jenkinsci.plugins.rss.LogRecorderManager.RSS;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by Felix on 04/02/15.
 */

public class UserActionRssFailed implements Action {

    private final transient User user;

    public UserActionRssFailed(User user) {
        this.user = user;
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
        return "rssFailed";
    }

    public void doIndex(StaplerRequest req, StaplerResponse rsp) throws IOException, ServletException {
        rss(req, rsp, " regression builds", user.getBuilds().regressionOnly(), Run.FEED_ADAPTER);
    }

    private void rss(StaplerRequest req, StaplerResponse rsp, String suffix, RunList runs, FeedAdapter adapter)
            throws IOException, ServletException {
        RSS.forwardToRss(getDisplayName() + suffix, user.getUrl(), runs.newBuilds(), adapter, req, rsp);
    }
}