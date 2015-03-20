package org.jenkinsci.plugins.rss.User;

import hudson.FeedAdapter;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.model.Cause;
import hudson.model.Run;
import hudson.model.User;
import hudson.util.RunList;
import jenkins.model.Jenkins;
import org.jenkinsci.plugins.rss.LogRecorderManager.RSS;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import javax.annotation.Nonnull;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felix on 04/02/15.
 */

public class UserActionRssLatest implements Action {

    private final transient User user;

    public UserActionRssLatest(User user) {
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
        return "rssLatest";
    }

    public void doIndex(StaplerRequest req, StaplerResponse rsp) throws IOException, ServletException {
        final List<Run> lastBuilds = new ArrayList<Run>();
        for (AbstractProject<?,?> p : Jenkins.getInstance().getAllItems(AbstractProject.class)) {
            for (AbstractBuild<?,?> b = p.getLastBuild(); b != null; b = b.getPreviousBuild()) {
                if (relatedTo(b)) {
                    lastBuilds.add(b);
                    break;
                }
            }
        }
        rss(req, rsp, " latest build", RunList.fromRuns(lastBuilds), Run.FEED_ADAPTER_LATEST);
    }

    /** true if {@link AbstractBuild#hasParticipant} or {@link hudson.model.Cause.UserIdCause} */
    private boolean relatedTo(@Nonnull AbstractBuild<?,?> b) {
        if (b.hasParticipant(user)) {
            return true;
        }
        for (Cause cause : b.getCauses()) {
            if (cause instanceof Cause.UserIdCause) {
                String userId = ((Cause.UserIdCause) cause).getUserId();
                if (userId != null && user.idStrategy().equals(userId, user.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void rss(StaplerRequest req, StaplerResponse rsp, String suffix, RunList runs, FeedAdapter adapter)
            throws IOException, ServletException {
        RSS.forwardToRss(getDisplayName() + suffix, user.getUrl(), runs.newBuilds(), adapter, req, rsp);
    }
}