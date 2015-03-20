package org.jenkinsci.plugins.rss.User;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.TransientUserActionFactory;
import hudson.model.User;

import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Felix on 04/02/15.
 */
@Extension
public class UserActionFactoryRssAll extends TransientUserActionFactory {


    /**
     * Our logger.
     */
    private static final Logger LOG = Logger.getLogger(UserActionFactoryRssAll.class.getName());

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<? extends Action> createFor(User user) {
        final UserActionRssAll newAction = new UserActionRssAll(user);
        LOG.log(Level.FINE, "{0} adds {1} for {2}", new Object[]{this, newAction, user});
        return Collections.singleton(newAction);
    }
}