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

/**
 * Activity report command
 * @author V. Tkachov
 */
public class ActivityReportCommand extends Command {

    public static final Logger LOG = Logger.getLogger(ActivityReportCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.debug("ActivityReportCommand execution");
        HttpSession session = request.getSession();

        if (request.getParameter("sort") != null && request.getParameter("sort").equals("nameActivityDESC")) {
            if (!request.getParameter("categoryId").isEmpty()) {
                List<Activity> activitiesReport = new MySQLActivityDAO().getAllSortActivities("DESC",
                        request.getParameter("categoryId"));
                request.setAttribute("activitiesReport", activitiesReport);
                return "/WEB-INF/jsp/adminPages/activityReport.jsp";
            }
            List<Activity> activitiesReport = new MySQLActivityDAO().getAllSortActivities("DESC", "");
            request.setAttribute("activitiesReport", activitiesReport);
            return "/WEB-INF/jsp/adminPages/activityReport.jsp";
        }

        if (request.getParameter("sort") != null && request.getParameter("sort").equals("nameActivityASC")) {
            if (!request.getParameter("categoryId").isEmpty()) {
                List<Activity> activitiesReport = new MySQLActivityDAO().getAllSortActivities("ASC",
                        request.getParameter("categoryId"));
                request.setAttribute("activitiesReport", activitiesReport);
                return "/WEB-INF/jsp/adminPages/activityReport.jsp";
            }
            List<Activity> activitiesReport = new MySQLActivityDAO().getAllSortActivities("ASC", "");
            request.setAttribute("activitiesReport", activitiesReport);
            return "/WEB-INF/jsp/adminPages/activityReport.jsp";
        }

        if (request.getParameter("sort") != null && request.getParameter("sort").equals("categoryNameDESC")) {
            if (!request.getParameter("categoryId").isEmpty()) {
                List<Activity> activitiesReport = new MySQLActivityDAO().getAllSortCategories("DESC",
                        request.getParameter("categoryId"));
                request.setAttribute("activitiesReport", activitiesReport);
                return "/WEB-INF/jsp/adminPages/activityReport.jsp";
            }
            List<Activity> activitiesReport = new MySQLActivityDAO().getAllSortCategories("DESC", "");
            request.setAttribute("activitiesReport", activitiesReport);
            return "/WEB-INF/jsp/adminPages/activityReport.jsp";
        }

        if (request.getParameter("sort") != null && request.getParameter("sort").equals("categoryNameASC")) {
            if (!request.getParameter("categoryId").isEmpty()) {
                List<Activity> activitiesReport = new MySQLActivityDAO().getAllSortCategories("ASC",
                        request.getParameter("categoryId"));
                request.setAttribute("activitiesReport", activitiesReport);
                return "/WEB-INF/jsp/adminPages/activityReport.jsp";
            }
            List<Activity> activitiesReport = new MySQLActivityDAO().getAllSortCategories("ASC", "");
            request.setAttribute("activitiesReport", activitiesReport);
            return "/WEB-INF/jsp/adminPages/activityReport.jsp";
        }

        if (request.getParameter("sort") != null && request.getParameter("sort").equals("userCountDESC")) {
            if (!request.getParameter("categoryId").isEmpty()) {
                List<Activity> activitiesReport = new MySQLActivityDAO().getAllSortUserCount("DESC",
                        request.getParameter("categoryId"));
                request.setAttribute("activitiesReport", activitiesReport);
                return "/WEB-INF/jsp/adminPages/activityReport.jsp";
            }
            List<Activity> activitiesReport = new MySQLActivityDAO().getAllSortUserCount("DESC", "");
            request.setAttribute("activitiesReport", activitiesReport);
            return "/WEB-INF/jsp/adminPages/activityReport.jsp";
        }

        if (request.getParameter("sort") != null && request.getParameter("sort").equals("userCountASC")) {
            if (!request.getParameter("categoryId").isEmpty()) {
                List<Activity> activitiesReport = new MySQLActivityDAO().getAllSortUserCount("ASC",
                        request.getParameter("categoryId"));
                request.setAttribute("activitiesReport", activitiesReport);
                return "/WEB-INF/jsp/adminPages/activityReport.jsp";
            }
            List<Activity> activitiesReport = new MySQLActivityDAO().getAllSortUserCount("ASC", "");
            request.setAttribute("activitiesReport", activitiesReport);
            return "/WEB-INF/jsp/adminPages/activityReport.jsp";
        }

        if (request.getParameter("categoryName") != null) {
            List<Activity> activitiesReport = new MySQLActivityDAO()
                    .getAllFilterCategory(Integer.parseInt(request.getParameter("categoryName")));
            session.setAttribute("categoryId", Integer.parseInt(request.getParameter("categoryName")));
            request.setAttribute("activitiesReport", activitiesReport);
            return "/WEB-INF/jsp/adminPages/activityReport.jsp";
        }

        List<Activity> activitiesReport = new MySQLActivityDAO().getAllActivitiesReport();
        List<Category> categories = new MySQLCategoryDAO().getAllCategories();
        request.setAttribute("activitiesReport", activitiesReport);
        request.setAttribute("categories", categories);
        return "/WEB-INF/jsp/adminPages/activityReport.jsp";
    }
}
