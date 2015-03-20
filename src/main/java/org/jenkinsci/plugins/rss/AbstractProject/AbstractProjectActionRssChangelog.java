package org.jenkinsci.plugins.rss;

import hudson.FeedAdapter;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.scm.ChangeLogSet;
import hudson.scm.ChangeLogSet.Entry;
import jenkins.model.JenkinsLocationConfiguration;
import org.jenkinsci.plugins.rss.LogRecorderManager.RSS;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.*;

/**
 * Created by Felix on 08/02/15.
 */
public class AbstractProjectActionRssChangelog implements Action {
    private transient AbstractProject abstractProject;

    public AbstractProjectActionRssChangelog(AbstractProject abstractProject) {
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
        return "rssChangelog";
    }

    /**
     * RSS feed for changes in this project.
     */
    public void doIndex(StaplerRequest req, StaplerResponse rsp) throws IOException, ServletException {
        class FeedItem {
            ChangeLogSet.Entry e;
            int idx;

            public FeedItem(Entry e, int idx) {
                this.e = e;
                this.idx = idx;
            }

            AbstractBuild<?,?> getBuild() {
                return e.getParent().build;
            }
        }

        List<FeedItem> entries = new ArrayList<FeedItem>();

        RSS.forwardToRss(
                getDisplayName() + ' ' + abstractProject.getScm().getDescriptor().getDisplayName() + " changes",
                abstractProject.getUrl() + "changes",
                entries, new FeedAdapter<FeedItem>() {
                    public String getEntryTitle(FeedItem item) {
                        return "#" + item.getBuild().number + ' ' + item.e.getMsg() + " (" + item.e.getAuthor() + ")";
                    }

                    public String getEntryUrl(FeedItem item) {
                        return item.getBuild().getUrl() + "changes#detail" + item.idx;
                    }

                    public String getEntryID(FeedItem item) {
                        return getEntryUrl(item);
                    }

                    public String getEntryDescription(FeedItem item) {
                        StringBuilder buf = new StringBuilder();
                        for (String path : item.e.getAffectedPaths())
                            buf.append(path).append('\n');
                        return buf.toString();
                    }

                    public Calendar getEntryTimestamp(FeedItem item) {
                        return item.getBuild().getTimestamp();
                    }

                    public String getEntryAuthor(FeedItem entry) {
                        return JenkinsLocationConfiguration.get().getAdminAddress();
                    }
                },
                req, rsp);
    }

}
