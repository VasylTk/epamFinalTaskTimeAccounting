package com.timeaccounting.web.command;

import com.timeaccounting.DB.DAO.mysql.MySQLActivityDAO;
import com.timeaccounting.DB.DAO.mysql.MySQLUserActivitiesDAO;
import com.timeaccounting.DB.DAO.mysql.MySQLUserDAO;
import com.timeaccounting.DB.Entity.Account;
import com.timeaccounting.DB.Entity.Activity;
import com.timeaccounting.DB.Entity.UserActivities;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class PinActivityCommand extends Command {

    public static final Logger LOG = Logger.getLogger(PinActivityCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.debug("PinActivityCommand execution");
        HttpSession session = request.getSession();
        int page = 1;
        int recordsPerPage = 5;
        int countOfRows = new MySQLUserActivitiesDAO().getCountOfRows();
        int countOfPages = (int) Math.ceil(countOfRows * 1.0 / recordsPerPage);

        request.setAttribute("countOfPages", countOfPages);

        if (request.getParameter("id") != null) {
            int activityId = new MySQLUserActivitiesDAO().getActivityId(Integer.parseInt(request.getParameter("id")));
            new MySQLUserActivitiesDAO().deleteUserActivity(Integer.parseInt(request.getParameter("id")));
            int countOfActivities = new MySQLUserActivitiesDAO().getCountOfActivitiesByCategoryId(activityId);
            if (countOfActivities == 0) {
                new MySQLActivityDAO().deleteActivity(activityId);
            }
        }

        if (request.getParameter("userLogin") != null && request.getParameter("nameActivity") != null) {
            new MySQLUserActivitiesDAO().setUserActivity(Integer.parseInt(request.getParameter("userLogin")),
                    Integer.parseInt(request.getParameter("nameActivity")));
            return "Controller?command=pinActivityCommand";
        }

        if (request.getParameter("updateId") != null) {
            session.setAttribute("updateId", request.getParameter("updateId"));
            return "/WEB-INF/jsp/adminPages/updatePinActivity.jsp";
        }

        if (request.getParameter("userLoginUpdate") != null && request.getParameter("nameActivityUpdate") != null) {
            new MySQLUserActivitiesDAO().updateUserActivity(Integer.parseInt(session.getAttribute("updateId").toString()),
                    Integer.parseInt(request.getParameter("userLoginUpdate").toString()),
                    Integer.parseInt(request.getParameter("nameActivityUpdate").toString()));
            return "Controller?command=pinActivityCommand";
        }

        if (request.getParameter("makeActiveId") != null) {
            new MySQLUserActivitiesDAO().makeActivityActive(Integer.parseInt(request.getParameter("makeActiveId").toString()));
        }

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
            List<UserActivities> userActivities = new MySQLUserActivitiesDAO()
                    .getUserActivitiesDividedByPage(((page - 1) * recordsPerPage), recordsPerPage);
            request.setAttribute("userActivities", userActivities);
            return "/WEB-INF/jsp/adminPages/pinActivity.jsp";

        }

        List<UserActivities> userActivities = new MySQLUserActivitiesDAO()
                .getUserActivitiesDividedByPage(((page - 1) * recordsPerPage), recordsPerPage);
        List<Account> users = new MySQLUserDAO().getAllUsers();
        List<Activity> activities = new MySQLActivityDAO().getAllActivities();
        session.setAttribute("users", users);
        request.setAttribute("userActivities", userActivities);
        session.setAttribute("activities", activities);
        return "/WEB-INF/jsp/adminPages/pinActivity.jsp";
    }
}
