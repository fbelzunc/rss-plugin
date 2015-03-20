package org.jenkinsci.plugins.rss.Computer;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.Computer;
import hudson.model.TransientComputerActionFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Felix on 04/02/15.
 */
@Extension
public class ComputerActionFactoryRssAll extends TransientComputerActionFactory {


    /**
     * Our logger.
     */
    private static final Logger LOG = Logger.getLogger(ComputerActionFactoryRssAll.class.getName());

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<? extends Action> createFor(Computer computer) {
        final ComputerActionRssAll newAction = new ComputerActionRssAll(computer);
        LOG.log(Level.FINE, "{0} adds {1} for {2}", new Object[]{this, newAction, computer});
        return Collections.singleton(newAction);
    }
}
