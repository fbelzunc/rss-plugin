package org.jenkinsci.plugins.rss.AbstractProject;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.model.Action;
import jenkins.model.TransientActionFactory;
import org.jenkinsci.plugins.rss.AbstractProjectActionRssAll;

import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Felix on 08/02/15.
 */
@Extension
public class AbstractProjectActionFactoryRssAll extends TransientActionFactory<AbstractProject> {
    /**
     * Our logger.
     */
    private static final Logger LOG = Logger.getLogger(AbstractProjectActionFactoryRssChangeLog.class.getName());

    @Override
    public Class<AbstractProject> type() {
        return AbstractProject.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<? extends Action> createFor(AbstractProject abstractProject) {
        final AbstractProjectActionRssAll newAction = new AbstractProjectActionRssAll(abstractProject);
        LOG.log(Level.FINE, "{0} adds {1} for {2}", new Object[]{this, newAction, abstractProject});
        return Collections.singleton(newAction);
    }

}