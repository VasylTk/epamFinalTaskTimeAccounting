package com.timeaccounting.web.command;

import com.timeaccounting.DB.DAO.mysql.*;
import com.timeaccounting.DB.Entity.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Login command
 * @author V. Tkachov
 */
@WebServlet(name = "LoginCommand")
public class LoginCommand extends Command {

    private static final Logger LOG = LogManager.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOG.debug("LoginCommand execution");
        HttpSession session = req.getSession();
        String forward = "";
        String login = "";
        String password = "";
        String role = "";
        session.setAttribute("passwordError", "");
        session.setAttribute("loginError", "");

        if ((session.getAttribute("email") != null) && (session.getAttribute("password") != null)) {
            login = String.valueOf(session.getAttribute("email"));
            password = String.valueOf(session.getAttribute("password"));
        }

        if ((req.getParameter("email") != null) && (req.getParameter("password") != null)) {
            login = String.valueOf(req.getParameter("email"));
            password = String.valueOf(req.getParameter("password"));
        }

        if ((login.isEmpty())) {
            session.setAttribute("loginError", "login field is empty");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }

        if (password.isEmpty()) {
            session.setAttribute("passwordError", "password field is empty");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }

        Account account = new MySQLUserDAO().findUserByLogin(login);
        if (account == null) {
            session.setAttribute("loginError", "Login is incorrect");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }

        if (!password.equals(account.getUserPassword())) {
            session.setAttribute("passwordError", "Password is incorrect");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }

        switch (account.getUserRole()) {
            case 0:
                role = "admin";
                break;
            case 1:
                role = "user";
                break;
        }

        session.setAttribute("userId", account.getId());
        session.setAttribute("user", account.getUserLogin());
        session.setAttribute("userRole", role);

        if (role.length() != 0) {
            switch (role) {
                case "admin":
                    forward = "Controller?command=userCommand";
                    break;
                case "user":
                    forward = "Controller?command=userCurrentActivitiesCommand";
                    break;
            }
        }

        if (role.equals("admin")) {
            List<Activity> activities = new MySQLActivityDAO().getAllActivities();
            List<Category> categories = new MySQLCategoryDAO().getAllCategories();
            List<Account> accounts = new MySQLUserDAO().getAllUsers();
            List<UserRole> userRoles = new MySQLUserRoleDAO().getUserRoles();
            req.setAttribute("activities", activities);
            session.setAttribute("categories", categories);
            req.setAttribute("accounts", accounts);
            req.setAttribute("userRoles", userRoles);
        }


        if (role.equals("user")) {
            List<UserActivities> activities = new MySQLUserActivitiesDAO()
                    .getAllUserActivitiesByUserId(Integer.parseInt(session.getAttribute("userId").toString()));
            req.setAttribute("activities", activities);
        }

        return forward;
    }
}
