package com.timeaccounting.web.command;

import com.timeaccounting.DB.DAO.mysql.MySQLCategoryDAO;
import com.timeaccounting.DB.Entity.Category;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryCommand")
public class CategoryCommand extends Command {

    public static final Logger LOG = Logger.getLogger(CategoryCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.debug("CategoryCommand execution");
        HttpSession session = request.getSession();
        int page = 1;
        int recordsPerPage = 5;
        int countOfRows = new MySQLCategoryDAO().getCountOfRows();
        int countOfPages = (int) Math.ceil(countOfRows * 1.0 / recordsPerPage);

        if (request.getParameter("categoryName") != null) {
            new MySQLCategoryDAO().addCategory(request.getParameter("categoryName"), 0);
            return "Controller?command=categoryCommand";
        }

        if (request.getParameter("id") != null) {
            new MySQLCategoryDAO().deleteCategoryById(Integer.parseInt(request.getParameter("id")));
        }

        if (request.getParameter("updateId") != null) {
            session.setAttribute("updateId", request.getParameter("updateId"));
            return "/WEB-INF/jsp/adminPages/updateCategory.jsp";
        }

        if (request.getParameter("updateCategoryName") != null) {
            new MySQLCategoryDAO().updateCategory(request.getParameter("updateCategoryName"),
                    Integer.parseInt(session.getAttribute("updateId").toString()));
            return "Controller?command=categoryCommand";
        }

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
            List<Category> categories = new MySQLCategoryDAO()
                    .getCategoriesDividedByPage(((page - 1) * recordsPerPage), recordsPerPage);
            request.setAttribute("categories", categories);
            return "/WEB-INF/jsp/adminPages/category.jsp";
        }


        List<Category> categories = new MySQLCategoryDAO()
                .getCategoriesDividedByPage(((page - 1) * recordsPerPage), recordsPerPage);
        request.setAttribute("categories", categories);
        session.setAttribute("countOfPages", countOfPages);
        return "/WEB-INF/jsp/adminPages/category.jsp";
    }
}
