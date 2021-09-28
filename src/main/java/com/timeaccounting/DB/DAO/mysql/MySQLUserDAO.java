package com.timeaccounting.DB.DAO.mysql;

import com.timeaccounting.DB.DAO.UserDAO;
import com.timeaccounting.DB.DBManager;
import com.timeaccounting.DB.Entity.Account;
import com.timeaccounting.DB.Query;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access MySQLUserDAO.
 *
 * @author V. Tkachov
 */
public class MySQLUserDAO extends Account implements UserDAO {

    public static final Logger LOG = Logger.getLogger(MySQLUserDAO.class);

    @Override
    public Account findUserByLogin(String login) {
        Account user = null;
        LOG.trace("Start tracing MySQLUserDAO#findUserByLogin");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(Query.FIND_USER_BY_LOGIN)) {
                connection.setAutoCommit(false);
                statement.setString(1, login);
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    user = new Account(resultSet.getInt("id"), resultSet.getString("user_login"),
                            resultSet.getString("user_password"), resultSet.getInt("user_role")
                    );
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
        return user;
    }

    @Override
    public List<Account> getAllUsers() {
        List<Account> users = new ArrayList<>();
        LOG.trace("Start tracing MySQLUserDAO#getAllUsers");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(Query.GET_ALL_USERS)) {
                connection.setAutoCommit(false);
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    Account account = new Account(resultSet.getInt("id"), resultSet.getString("user_login"),
                            resultSet.getString("user_password"), resultSet.getInt("user_role"),
                            resultSet.getString("name_user_role"));
                    users.add(account);
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
        return users;
    }

    @Override
    public List<Account> getUsersDividedByPage(int startBoundary, int endBoundary) {
        List<Account> users = new ArrayList<>();
        LOG.trace("Start tracing MySQLUserDAO#getUsersDividedByPage");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(Query.GET_USERS_DIVIDED_BY_PAGE + startBoundary + "," + endBoundary)) {
                connection.setAutoCommit(false);
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    Account account = new Account(resultSet.getInt("id"), resultSet.getString("user_login"),
                            resultSet.getString("user_password"), resultSet.getInt("user_role"),
                            resultSet.getString("name_user_role"));
                    users.add(account);
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
        return users;
    }

    @Override
    public void registerUser(String userLogin, String userPassword, int userRole) {
        LOG.trace("Start tracing MySQLUserDAO#registerUser");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement
                         = connection.prepareStatement(Query.REGISTER_USER
                    , Statement.RETURN_GENERATED_KEYS)) {
                connection.setAutoCommit(false);
                statement.setString(1, userLogin);
                statement.setString(2, userPassword);
                statement.setInt(3, userRole);
                statement.executeUpdate();
                connection.commit();
            } catch (SQLException ex) {
                LOG.error(ex.getLocalizedMessage());
                connection.rollback();
            }
        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
    }

    @Override
    public void setNewPassword(int id, String password) {
        LOG.trace("Start tracing MySQLUserDAO#setNewPassword");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement
                         = connection.prepareStatement(Query.SET_NEW_PASSWORD
                    , Statement.RETURN_GENERATED_KEYS)) {
                connection.setAutoCommit(false);
                statement.setString(1, password);
                statement.setInt(2, id);
                statement.executeUpdate();
                connection.commit();
            } catch (SQLException ex) {
                LOG.error(ex.getLocalizedMessage());
                connection.rollback();
            }
        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
    }

    @Override
    public void deleteUserById(int id) {
        LOG.trace("Start tracing MySQLUserDAO#deleteUserById");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement
                         = connection.prepareStatement(Query.DELETE_USER_BY_ID, Statement.RETURN_GENERATED_KEYS)) {
                connection.setAutoCommit(false);
                statement.setInt(1, id);
                statement.executeUpdate();
                connection.commit();
            } catch (SQLException ex) {
                LOG.error(ex.getLocalizedMessage());
                connection.rollback();
            }
        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
    }

    @Override
    public int getCountOfRows() {
        int countOfRows = 0;
        LOG.trace("Start tracing MySQLUserDAO#getCountOfRows");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(Query.GET_COUNT_OF_ROWS_USER_DAO)) {
                connection.setAutoCommit(false);
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    countOfRows = resultSet.getInt("count");
                }
                resultSet.close();
                connection.commit();
                return countOfRows;
            } catch (SQLException ex) {
                LOG.error(ex.getLocalizedMessage());
                connection.rollback();
            }
        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return countOfRows;
    }

}
