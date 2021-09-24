package com.timeaccounting.DB.DAO.mysql;

import com.timeaccounting.DB.DAO.UserDAO;
import com.timeaccounting.DB.DBManager;
import com.timeaccounting.DB.Entity.Account;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLUserDAO extends Account implements UserDAO {

    public static final Logger LOG = Logger.getLogger(MySQLUserDAO.class);

    @Override
    public Account findUserByLogin(String login) {
        Account user = null;
        LOG.trace("Start tracing MySQLUserDAO#findUserByLogin");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM account where user_login = ?")) {
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
            if (connection != null) {
                try (PreparedStatement statement =
                             connection.prepareStatement("select account.id,  account.user_login, user_role.name_user_role, account.user_role, account.user_password\n" +
                                     "from account join user_role on user_role.id = account.user_role;")) {
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
            if (connection != null) {
                try (PreparedStatement statement =
                             connection.prepareStatement("select account.id,  account.user_login, user_role.name_user_role,\n" +
                                     "account.user_role, account.user_password from account join \n" +
                                     "user_role on user_role.id = account.user_role limit " + startBoundary + "," + endBoundary)) {
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
            if (connection != null) {
                try (PreparedStatement statement
                             = connection.prepareStatement("INSERT INTO account VALUES(default, ?, ?, ?)"
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
            }
        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
    }

    @Override
    public void setNewPassword(int id, String password) {
        LOG.trace("Start tracing MySQLUserDAO#setNewPassword");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement
                             = connection.prepareStatement("UPDATE account SET user_password = ? WHERE id=?"
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
            }
        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
    }

    @Override
    public void deleteUserById(int id) {
        LOG.trace("Start tracing MySQLUserDAO#deleteUserById");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement
                             = connection.prepareStatement("DELETE FROM account WHERE id = ?", Statement.RETURN_GENERATED_KEYS)) {
                    connection.setAutoCommit(false);
                    statement.setInt(1, id);
                    statement.executeUpdate();
                    connection.commit();
                } catch (SQLException ex) {
                    LOG.error(ex.getLocalizedMessage());
                    connection.rollback();
                }
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
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) as count FROM account")) {
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
            }
        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return countOfRows;
    }

}
