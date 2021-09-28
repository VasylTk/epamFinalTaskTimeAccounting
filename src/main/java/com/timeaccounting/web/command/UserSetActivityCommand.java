package com.timeaccounting.web.command;

import com.timeaccounting.DB.DAO.mysql.MySQLActivityDAO;
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

/**
 * User set activity command
 * @author V. Tkachov
 */
public class UserSetActivityCommand extends Command {

    private static final Logger LOG = LogManager.getLogger(UserSetActivityCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.debug("UserSetActivityCommand execution");
        HttpSession session = request.getSession();
        int page = 1;
        int recordsPerPage = 5;
        int countOfRows = new MySQLUserActivitiesDAO()
                .getAllUnActiveActivitiesByUserId(Integer.parseInt(session.getAttribute("userId").toString()), 0, 0).size();
        int countOfPages = (int) Math.ceil(countOfRows * 1.0 / recordsPerPage);
        request.setAttribute("countOfPages", countOfPages);

        if (request.getParameter("setNewActivity") != null) {
            int idActivity = new MySQLActivityDAO().findActivityByName(request.getParameter("setNewActivity").toString());
            if (idActivity == 0) {
                new MySQLActivityDAO().addActivity(request.getParameter("setNewActivity").toString(), 0);
                int id = new MySQLActivityDAO().findActivityByName(request.getParameter("setNewActivity").toString());
                new MySQLUserActivitiesDAO()
                        .setUserActivityFromUserSide(Integer.parseInt(session.getAttribute("userId").toString()), id);
            }
            if (idActivity != 0) {
                new MySQLUserActivitiesDAO()
                        .setUserActivityFromUserSide(Integer.parseInt(session.getAttribute("userId").toString()), idActivity);
            }
            return "Controller?command=userSetActivityCommand";
        }

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
            List<UserActivities> userUnActiveActivities =
                    new MySQLUserActivitiesDAO()
                            .getAllUnActiveActivitiesByUserId(Integer.parseInt(session.getAttribute("userId").toString()),
                                    ((page - 1) * recordsPerPage), recordsPerPage);
            request.setAttribute("userUnActiveActivities", userUnActiveActivities);
            return "/WEB-INF/jsp/userPages/userSetActivity.jsp";
        }

        List<UserActivities> userUnActiveActivities =
                new MySQLUserActivitiesDAO()
                        .getAllUnActiveActivitiesByUserId(Integer.parseInt(session.getAttribute("userId").toString()),
                                ((page - 1) * recordsPerPage), recordsPerPage);

        request.setAttribute("userUnActiveActivities", userUnActiveActivities);
        return "/WEB-INF/jsp/userPages/userSetActivity.jsp";
    }
}
