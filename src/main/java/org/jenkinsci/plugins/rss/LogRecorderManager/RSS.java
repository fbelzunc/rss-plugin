package org.jenkinsci.plugins.rss.LogRecorderManager;

/**
 * Created by Felix on 20/01/15.
 */

import hudson.FeedAdapter;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * RSS related code.
 *
 * @author Kohsuke Kawaguchi
 */
public final class RSS {

    /**
     * Parses trackback ping.
     */
    public static void doTrackback( Object it, StaplerRequest req, StaplerResponse rsp ) throws IOException, ServletException {

        String url = req.getParameter("url");

        rsp.setStatus(HttpServletResponse.SC_OK);
        rsp.setContentType("application/xml; charset=UTF-8");
        PrintWriter pw = rsp.getWriter();
        pw.println("<response>");
        pw.println("<error>"+(url!=null?0:1)+"</error>");
        if(url==null) {
            pw.println("<message>url must be specified</message>");
        }
        pw.println("</response>");
        pw.close();
    }

    /**
     * Sends the RSS feed to the client.
     *
     * @param title
     *      Title of the feed.
     * @param url
     *      URL of the model object that owns this feed. Relative to the context root.
     * @param entries
     *      Entries to be listed in the RSS feed.
     * @param adapter
     *      Controls how to render entries to RSS.
     */
    public static <E> void forwardToRss(String title, String url, Collection<? extends E> entries, FeedAdapter<E> adapter, StaplerRequest req, HttpServletResponse rsp) throws IOException, ServletException {
        req.setAttribute("adapter",adapter);
        req.setAttribute("title",title);
        req.setAttribute("url",url);
        req.setAttribute("entries",entries);
        req.setAttribute("rootURL", Jenkins.getInstance().getRootUrl());

        String flavor = req.getParameter("flavor");
        if(flavor==null)    flavor="atom";
        flavor = flavor.replace('/', '_'); // Don't allow path to any jelly

        req.getView(Jenkins.getInstance(),"/hudson/"+flavor+".jelly").forward(req,rsp);
    }
}