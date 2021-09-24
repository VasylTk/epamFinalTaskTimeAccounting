package com.timeaccounting.web.command;

import com.timeaccounting.DB.DAO.mysql.MySQLUserActivitiesDAO;
import com.timeaccounting.DB.Entity.UserActivities;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class UserSetTimeCommand extends Command {

    private static final Logger LOG = LogManager.getLogger(UserSetTimeCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.debug("UserSetTimeCommand execution");
        HttpSession session = request.getSession();
        if (request.getParameter("id") != null) {
            session.setAttribute("id", request.getParameter("id"));
            return "/WEB-INF/jsp/userPages/userSetTime.jsp";
        }

        if (request.getParameter("setTime") != null && session.getAttribute("id") != null) {
            new MySQLUserActivitiesDAO()
                    .setUserTime(Integer.parseInt(session.getAttribute("id").toString()), request.getParameter("setTime"));
            return "Controller?command=userCurrentActivitiesCommand";
        }

        List<UserActivities> activities = new MySQLUserActivitiesDAO()
                .getAllUserActivitiesByUserId(Integer.parseInt(session.getAttribute("userId").toString()));
        request.setAttribute("activities", activities);

        return "/WEB-INF/jsp/userPages/user.jsp";
    }
}
