package org.jenkinsci.plugins.rss.Job;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.Job;
import jenkins.model.TransientActionFactory;
import org.jenkinsci.plugins.rss.JobActionRssAll;

import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Felix on 03/02/15.
 */
@Extension
public class JobActionFactoryRssAll extends TransientActionFactory<Job> {

    /**
     * Our logger.
     */
    private static final Logger LOG = Logger.getLogger(JobActionFactoryRssAll.class.getName());

    @Override
    public Class<Job> type() {
        return Job.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<? extends Action> createFor(Job job) {
        final JobActionRssAll newAction = new JobActionRssAll(job);
        LOG.log(Level.FINE, "{0} adds {1} for {2}", new Object[]{this, newAction, job});
        return Collections.singleton(newAction);
    }
}