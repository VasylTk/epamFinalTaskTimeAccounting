package com.timeaccounting.web.listener;

import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener()
public class ContextListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(ContextListener.class);

    public ContextListener() {
    }

    public void contextInitialized(ServletContextEvent event) {
        LOG.trace("Servlet context initialization starts");
        initCommandContainer();
        LOG.trace("Servlet context initialization finished");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        LOG.trace("Servlet context destruction starts");
        LOG.trace("Servlet context destruction finished");
    }

    private void initCommandContainer() {
        try {
            Class.forName("com.timeaccounting.web.command.CommandContainer");
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("Cannot initialize Command Container");
        }
    }


}
