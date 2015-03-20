package org.jenkinsci.plugins.rss.LogRecorderManager;

import hudson.Extension;
import hudson.logging.LogRecorderManager;
import hudson.model.Action;
import hudson.model.TransientLogRecorderManagerActionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Felix on 04/02/15.
 */
@Extension
public class LogRecorderActionFactoryRss extends TransientLogRecorderManagerActionFactory {
    /**
     * Our logger.
     */
    private static final Logger LOG = Logger.getLogger(LogRecorderActionFactoryRss.class.getName());

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Action> createFor(LogRecorderManager logRecorderManager) {
        List<Action> result = new ArrayList<Action>();
        final LogRecorderActionRss newAction = new LogRecorderActionRss(logRecorderManager);
        LOG.log(Level.FINE, "{0} adds {1} for {2}", new Object[]{this, newAction, logRecorderManager});
        result.add(newAction);
        return result;
    }
}

