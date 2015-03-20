package org.jenkinsci.plugins.rss.Job;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.Job;
import jenkins.model.TransientActionFactory;
import org.jenkinsci.plugins.rss.JobActionRssFailed;

import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Felix on 03/02/15.
 */
@Extension
public class JobActionFactoryRssFailed extends TransientActionFactory<Job> {

    /**
     * Our logger.
     */
    private static final Logger LOG = Logger.getLogger(JobActionFactoryRssFailed.class.getName());

    @Override
    public Class<Job> type() {
        return Job.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<? extends Action> createFor(Job job) {
        final JobActionRssFailed newAction = new JobActionRssFailed(job);
        LOG.log(Level.FINE, "{0} adds {1} for {2}", new Object[]{this, newAction, job});
        return Collections.singleton(newAction);
    }
}