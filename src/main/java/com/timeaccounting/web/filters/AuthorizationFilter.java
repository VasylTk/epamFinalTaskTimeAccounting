package com.timeaccounting.web.filters;

import com.timeaccounting.web.command.CommandContainer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;


/**
 * Authorization filter
 * @author V. Tkachov
 */
public class AuthorizationFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger(AuthorizationFilter.class);

    HashSet<String> userCommands;
    HashSet<String> adminCommands;
    HashSet<String> accessibleCommands;

    public AuthorizationFilter() {
        adminCommands = new HashSet<>();
        userCommands = new HashSet<>();
        accessibleCommands = new HashSet<>();
        accessibleCommands.add("loginCommand");
        accessibleCommands.add("noCommand");
        accessibleCommands.add("logoutCommand");
        adminCommands.add("categoryCommand");
        adminCommands.add("activityCommand");
        adminCommands.add("pinActivityCommand");
        adminCommands.add("userCommand");
        adminCommands.add("activityReportCommand");
        adminCommands.add("usersReportCommand");
        userCommands.add("userSetActivityCommand");
        userCommands.add("userDeleteActivityCommand");
        userCommands.add("userSetTimeCommand");
        userCommands.add("userCurrentActivitiesCommand");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String command = req.getParameter("command");

        if (!CommandContainer.getAllCommands(command)) {
            LOG.debug("Invalid command: {}" + command);
            res.sendRedirect("Controller?command=noCommand");
            return;
        }

        if (accessibleCommands.contains(command)) {
            LOG.debug("This command can be accessed by all users: {}" + command);
            chain.doFilter(req, res);
            return;
        }

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            LOG.debug("Unauthorized access to resource. User is not logged-in.");
            res.sendRedirect("Controller?command=loginPage");
            return;
        }

        LOG.debug("User is logged-in. Check common commands to logged in users.");
        String role = session.getAttribute("userRole").toString().trim();

        if (userCommandByUser(role, command) || adminCommandByAdmin(role, command)) {
            LOG.debug("Command can be executed by this user: {}" + command);
            chain.doFilter(req, res);
            return;
        }

        res.sendRedirect("Controller?command=loginPage");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private boolean userCommandByUser(String role, String command) {
        return "user".equals(role) && userCommands.contains(command);
    }

    private boolean adminCommandByAdmin(String role, String command) {
        return "admin".equals(role) && adminCommands.contains(command);
    }

}
