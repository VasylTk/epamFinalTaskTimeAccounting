package com.timeaccounting.controller;

import com.timeaccounting.web.command.Command;
import com.timeaccounting.web.command.CommandContainer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main servlet controller.
 *
 * @author V.Tkachov
 *
 */

@WebServlet(name = "Controller")
public class Controller extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(Controller.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.trace("---GET---");
        process(req, resp, "get");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.trace("---POST---");
        process(req, resp, "post");
    }

    private void process(HttpServletRequest req, HttpServletResponse resp, String method) throws IOException, ServletException {
        LOG.debug("Controller starts");
        String commandName = req.getParameter("command");

        Command command = CommandContainer.get(commandName);
        LOG.trace("Obtained command --> " + command);
        String forward = "Controller?command=noCommand";

        try {
            forward = command.execute(req, resp);
        } catch (Exception ex) {
            req.setAttribute("errorMessage", ex.getMessage());
        }

        LOG.trace("Forward address --> " + forward);
        LOG.debug("Controller finished, now go to forward address --> " + forward);
        if (method.equals("post")) {
            resp.sendRedirect(forward);
        }

        if (method.equals("get")) {
            req.getRequestDispatcher(forward).forward(req, resp);
        }
    }
}
