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
public class UserActionFactoryRssLatest extends TransientUserActionFactory {


    /**
     * Our logger.
     */
    private static final Logger LOG = Logger.getLogger(UserActionFactoryRssLatest.class.getName());

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<? extends Action> createFor(User user) {
        final UserActionRssLatest newAction = new UserActionRssLatest(user);
        LOG.log(Level.FINE, "{0} adds {1} for {2}", new Object[]{this, newAction, user});
        return Collections.singleton(newAction);
    }
}