package com.timeaccounting.web.command;

import com.timeaccounting.DB.DAO.mysql.MySQLActivityDAO;
import com.timeaccounting.DB.DAO.mysql.MySQLCategoryDAO;
import com.timeaccounting.DB.Entity.Activity;
import com.timeaccounting.DB.Entity.Category;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ActivityCommand extends Command {

    public static final Logger LOG = Logger.getLogger(ActivityCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.debug("ActivityCommand execution");
        HttpSession session = request.getSession();
        int page = 1;
        int recordsPerPage = 5;
        int countOfRows = new MySQLActivityDAO().getCountOfRows();
        int countOfPages = (int) Math.ceil(countOfRows * 1.0 / recordsPerPage);
        request.setAttribute("countOfPages", countOfPages);

        if (request.getParameter("activityName") != null && request.getParameter("categoryName") != null) {
            new MySQLActivityDAO().addActivity(request.getParameter("activityName"),
                    Integer.parseInt(request.getParameter("categoryName")));
            return "Controller?command=activityCommand";
        }

        if (request.getParameter("id") != null) {
            new MySQLActivityDAO().deleteActivity(Integer.parseInt(request.getParameter("id")));
        }

        if (request.getParameter("editId") != null) {
            session.setAttribute("editId", request.getParameter("editId"));
            return "/WEB-INF/jsp/adminPages/updateActivity.jsp";
        }

        if (request.getParameter("activityNameUpdate") != null && request.getParameter("categoryIdUpdate") != null) {
            new MySQLActivityDAO().updateActivityById(Integer.parseInt(session.getAttribute("editId").toString()),
                    request.getParameter("activityNameUpdate"),
                    Integer.parseInt(request.getParameter("categoryIdUpdate").toString()));
            return "Controller?command=activityCommand";
        }

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
            List<Activity> activities = new MySQLActivityDAO().getActivityDividedByPage((page - 1) * recordsPerPage,
                    recordsPerPage);
            request.setAttribute("activities", activities);
            return "/WEB-INF/jsp/adminPages/activity.jsp";
        }

        List<Activity> activities = new MySQLActivityDAO().getActivityDividedByPage(((page - 1) * recordsPerPage), recordsPerPage);
        List<Category> categories = new MySQLCategoryDAO().getAllCategories();

        request.setAttribute("categories", categories);
        request.setAttribute("activities", activities);
        return "/WEB-INF/jsp/adminPages/activity.jsp";
    }
}
