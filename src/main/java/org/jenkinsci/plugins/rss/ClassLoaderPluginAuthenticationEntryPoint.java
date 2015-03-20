package org.jenkinsci.plugins.rss;

import hudson.Extension;
import hudson.security.PluginAuthenticationEntryPoint;

/**
 * Created by Felix on 06/02/15.
 */

@Extension
public class ClassLoaderPluginAuthenticationEntryPoint extends PluginAuthenticationEntryPoint {

    @Override
    public PluginInformation getPluginInformation() {
        PluginInformation pluginInformation = new PluginInformation();
        pluginInformation.setPluginName("rss");
        pluginInformation.setAuthenticationType("BASIC");
        return pluginInformation;
    }
}
