package com.timeaccounting.web.command;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * NoCommand
 * @author V. Tkachov
 */
public class NoCommand extends Command {

    private static final Logger LOG = LogManager.getLogger(NoCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.debug("LoginCommand execution");
        return "/WEB-INF/jsp/errorPage.jsp";
    }
}
