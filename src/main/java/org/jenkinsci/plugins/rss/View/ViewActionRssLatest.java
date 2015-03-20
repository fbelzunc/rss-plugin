package org.jenkinsci.plugins.rss.View;

import hudson.model.Action;
import hudson.model.Job;
import hudson.model.RSS;
import hudson.model.Run;
import hudson.model.TopLevelItem;
import hudson.model.View;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felix on 04/02/15.
 */
public class ViewActionRssLatest implements Action {

    private final transient View view;

    public ViewActionRssLatest(View view) {
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
        return "rssLatest";
    }

    public void doIndex( StaplerRequest req, StaplerResponse rsp ) throws IOException, ServletException {
        List<Run> lastBuilds = new ArrayList<Run>();
        for (TopLevelItem item : view.getItems()) {
            if (item instanceof Job) {
                Job job = (Job) item;
                Run lb = job.getLastBuild();
                if(lb!=null)    lastBuilds.add(lb);
            }
        }
        RSS.forwardToRss(getDisplayName()+" last builds only", view.getUrl(),
                lastBuilds, Run.FEED_ADAPTER_LATEST, req, rsp );
    }
}
