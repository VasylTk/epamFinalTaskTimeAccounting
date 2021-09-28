package com.timeaccounting.DB.DAO.mysql;

import com.timeaccounting.DB.DAO.UserRoleDAO;
import com.timeaccounting.DB.DBManager;
import com.timeaccounting.DB.Entity.UserRole;
import com.timeaccounting.DB.Query;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access MySQLUserRoleDAO.
 *
 * @author V. Tkachov
 */
public class MySQLUserRoleDAO implements UserRoleDAO {

    public static final Logger LOG = Logger.getLogger(MySQLUserRoleDAO.class);

    @Override
    public List<UserRole> getUserRoles() {
        List<UserRole> userRoles = new ArrayList<>();
        LOG.trace("Start tracing MySQLUserRoleDAO#getUserRoles");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(Query.GET_USER_ROLES)) {
                connection.setAutoCommit(false);
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    UserRole userRole = new UserRole(resultSet.getInt("id"),
                            resultSet.getString("name_user_role"));
                    userRoles.add(userRole);
                }
                resultSet.close();
                connection.commit();
            } catch (SQLException ex) {
                LOG.error(ex.getLocalizedMessage());
                connection.rollback();
            }
        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }

        return userRoles;
    }

}

