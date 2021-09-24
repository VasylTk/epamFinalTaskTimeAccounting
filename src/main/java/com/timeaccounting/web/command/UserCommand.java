package com.timeaccounting.web.command;

import com.timeaccounting.DB.DAO.mysql.MySQLUserDAO;
import com.timeaccounting.DB.DAO.mysql.MySQLUserRoleDAO;
import com.timeaccounting.DB.Entity.Account;
import com.timeaccounting.DB.Entity.UserRole;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class UserCommand extends Command {

    private static final Logger LOG = LogManager.getLogger(UserCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOG.debug("UserCommand execution");
        HttpSession session = req.getSession();
        int page = 1;
        int recordsPerPage = 5;
        int countOfRows = new MySQLUserDAO().getCountOfRows();
        int countOfPages = (int) Math.ceil(countOfRows * 1.0 / recordsPerPage);

        if (req.getParameter("id") != null) {
            new MySQLUserDAO().deleteUserById(Integer.parseInt(req.getParameter("id")));
        }
        if (req.getParameter("email") != null && req.getParameter("password") != null
                && req.getParameter("role") != null) {
            new MySQLUserDAO().registerUser(req.getParameter("email"), req.getParameter("password"),
                    Integer.parseInt(req.getParameter("role")));
            return "Controller?command=userCommand";
        }

        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
            List<Account> accounts = new MySQLUserDAO()
                    .getUsersDividedByPage(((page - 1) * recordsPerPage), recordsPerPage);
            req.setAttribute("accounts", accounts);
            return "/WEB-INF/jsp/adminPages/admin.jsp";
        }

        List<Account> accounts = new MySQLUserDAO()
                .getUsersDividedByPage(((page - 1) * recordsPerPage), recordsPerPage);
        List<UserRole> userRoles = new MySQLUserRoleDAO().getUserRoles();
        session.setAttribute("userRoles", userRoles);
        req.setAttribute("accounts", accounts);
        session.setAttribute("countOfPages", countOfPages);
        return "/WEB-INF/jsp/adminPages/admin.jsp";
    }
}
