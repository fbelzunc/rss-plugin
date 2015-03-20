package org.jenkinsci.plugins.rss.View;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.View;
import jenkins.model.TransientActionFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Felix on 04/02/15.
 */
@Extension
public class ViewActionFactoryRssLatest extends TransientActionFactory<View> {
    /**
     * Our logger.
     */
    private static final Logger LOG = Logger.getLogger(ViewActionFactoryRssLatest.class.getName());

    @Override
    public Class<View> type() {
        return View.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<? extends Action> createFor(View view) {
        final ViewActionRssLatest newAction = new ViewActionRssLatest(view);
        LOG.log(Level.FINE, "{0} adds {1} for {2}", new Object[]{this, newAction, view});
        return Collections.singleton(newAction);
    }
}