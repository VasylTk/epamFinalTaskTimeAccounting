package com.timeaccounting.web.command;

import com.timeaccounting.DB.DAO.mysql.MySQLUserActivitiesDAO;
import com.timeaccounting.DB.Entity.UserActivities;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UsersReportCommand extends Command {

    private static final Logger LOG = LogManager.getLogger(UsersReportCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.debug("UsersReportCommand execution");
        List<UserActivities> usersReport = new MySQLUserActivitiesDAO().getUsersReport();
        request.setAttribute("usersReport", usersReport);
        return "/WEB-INF/jsp/adminPages/usersReport.jsp";
    }
}
