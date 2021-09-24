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

public class UserCurrentActivitiesCommand extends Command {

    private static final Logger LOG = LogManager.getLogger(UserCurrentActivitiesCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.debug("UserCurrentActivitiesCommand execution");
        HttpSession session = request.getSession();
        int page = 1;
        int recordsPerPage = 5;
        int countOfRows = new MySQLUserActivitiesDAO()
                .getCountOfRows(Integer.parseInt(session.getAttribute("userId").toString()));
        int countOfPages = (int) Math.ceil(countOfRows * 1.0 / recordsPerPage);
        request.setAttribute("countOfPages", countOfPages);

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
            List<UserActivities> activities = new MySQLUserActivitiesDAO()
                    .getAllUserActivitiesByUserId(Integer.parseInt(session.getAttribute("userId").toString()),
                    ((page - 1) * recordsPerPage), recordsPerPage);
            request.setAttribute("activities", activities);
            return "/WEB-INF/jsp/userPages/user.jsp";
        }

        List<UserActivities> activities = new MySQLUserActivitiesDAO()
                .getAllUserActivitiesByUserId(Integer.parseInt(session.getAttribute("userId").toString()),
                        ((page - 1) * recordsPerPage), recordsPerPage);
        request.setAttribute("activities", activities);
        return "/WEB-INF/jsp/userPages/user.jsp";
    }
}
