package org.jenkinsci.plugins.rss.View;

import hudson.model.Action;
import hudson.model.RSS;
import hudson.model.Run;
import hudson.model.View;
import hudson.util.RunList;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by Felix on 04/02/15.
 */
public class ViewActionRssFailed implements Action {

    private final transient View view;

    public ViewActionRssFailed(View view) {
        this.view = view;
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

    public void doIndex( StaplerRequest req, StaplerResponse rsp ) throws IOException, ServletException {
        rss(req, rsp, " all builds", view.getBuilds());
    }

    private void rss(StaplerRequest req, StaplerResponse rsp, String suffix, RunList runs) throws IOException, ServletException {
        RSS.forwardToRss(getDisplayName()+ suffix, view.getUrl(),
                runs.newBuilds(), Run.FEED_ADAPTER, req, rsp );
    }
}
