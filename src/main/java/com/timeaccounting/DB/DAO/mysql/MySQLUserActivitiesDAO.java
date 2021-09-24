package com.timeaccounting.DB.DAO.mysql;

import com.timeaccounting.DB.DAO.UserActivitiesDAO;
import com.timeaccounting.DB.DBManager;
import com.timeaccounting.DB.Entity.UserActivities;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLUserActivitiesDAO implements UserActivitiesDAO {

    public static final Logger LOG = Logger.getLogger(MySQLUserActivitiesDAO.class);

    @Override
    public List<UserActivities> getAllUserActivities() {
        List<UserActivities> userActivities = new ArrayList<>();
        LOG.trace("Start tracing MySQLUserActivitiesDAO#getAllUserActivities");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement =
                             connection.prepareStatement("select user_activities.id as userActivityId,\n" +
                                     "user_activities.id_user as userActivityIdUser,\n" +
                                     "user_activities.id_activity as userActivityActivityId,\n" +
                                     "account.user_login as userLogin,\n" +
                                     "activity.name_activity as nameActivity, user_activities.state as state, user_activities.on_delete as onDelete , user_activities.spent_time as spentTime \n" +
                                     "from user_activities join account on account.id = user_activities.id_user join activity on\n" +
                                     "activity.id = user_activities.id_activity")) {
                    connection.setAutoCommit(false);
                    statement.execute();
                    ResultSet resultSet = statement.getResultSet();
                    while (resultSet.next()) {
                        UserActivities userActivity = new UserActivities(resultSet.getInt("userActivityId"),
                                resultSet.getInt("userActivityIdUser"),
                                resultSet.getInt("userActivityActivityId"),
                                resultSet.getString("userLogin"),
                                resultSet.getString("nameActivity"),
                                resultSet.getInt("state"),
                                resultSet.getInt("onDelete"), resultSet.getString("spentTime"));
                        userActivities.add(userActivity);
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

        return userActivities;
    }

    @Override
    public List<UserActivities> getAllUserActivitiesByUserId(int id) {
        List<UserActivities> userActivities = new ArrayList<>();
        LOG.trace("Start tracing MySQLUserActivitiesDAO#getAllUserActivities");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement =
                             connection.prepareStatement("select user_activities.id as userActivityId, user_activities.id_user as userActivityIdUser,\n" +
                                     "user_activities.id_activity as userActivityActivityId, account.user_login as userLogin,\n" +
                                     "activity.name_activity as nameActivity, user_activities.state as state, user_activities.on_delete as onDelete , user_activities.spent_time as spentTime from user_activities join account on \n" +
                                     "account.id = user_activities.id_user join activity on activity.id = user_activities.id_activity\n" +
                                     "where user_activities.state = 1 and account.id = ? ")) {
                    connection.setAutoCommit(false);
                    statement.setInt(1, id);
                    statement.execute();
                    ResultSet resultSet = statement.getResultSet();
                    while (resultSet.next()) {
                        UserActivities userActivity = new UserActivities(resultSet.getInt("userActivityId"),
                                resultSet.getInt("userActivityIdUser"),
                                resultSet.getInt("userActivityActivityId"),
                                resultSet.getString("userLogin"),
                                resultSet.getString("nameActivity"),
                                resultSet.getInt("state"),
                                resultSet.getInt("onDelete"), resultSet.getString("spentTime"));
                        userActivities.add(userActivity);
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

        return userActivities;
    }

    @Override
    public List<UserActivities> getAllUserActivitiesByUserId(int id, int startBoundary, int endBoundary) {
        List<UserActivities> userActivities = new ArrayList<>();
        LOG.trace("Start tracing MySQLUserActivitiesDAO#getAllUserActivitiesByUserId");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement =
                             connection.prepareStatement("select user_activities.id as userActivityId, user_activities.id_user as userActivityIdUser,\n" +
                                     "user_activities.id_activity as userActivityActivityId, account.user_login as userLogin,\n" +
                                     "activity.name_activity as nameActivity, user_activities.state as state, user_activities.on_delete as onDelete , user_activities.spent_time as spentTime from user_activities join account on \n" +
                                     "account.id = user_activities.id_user join activity on activity.id = user_activities.id_activity\n" +
                                     "where user_activities.state = 1 and account.id = ? limit " + startBoundary + "," + endBoundary)) {
                    connection.setAutoCommit(false);
                    statement.setInt(1, id);
                    statement.execute();
                    ResultSet resultSet = statement.getResultSet();
                    while (resultSet.next()) {
                        UserActivities userActivity = new UserActivities(resultSet.getInt("userActivityId"),
                                resultSet.getInt("userActivityIdUser"),
                                resultSet.getInt("userActivityActivityId"),
                                resultSet.getString("userLogin"),
                                resultSet.getString("nameActivity"),
                                resultSet.getInt("state"),
                                resultSet.getInt("onDelete"), resultSet.getString("spentTime"));
                        userActivities.add(userActivity);
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

        return userActivities;
    }

    @Override
    public List<UserActivities> getAllUnActiveActivitiesByUserId(int id, int startBoundary, int endBoundary) {
        StringBuffer sql = new StringBuffer();
        if (startBoundary == 0 && endBoundary == 0) {
            sql.append("select user_activities.id as userActivityId, user_activities.id_user as userActivityIdUser,\n" +
                    "user_activities.id_activity as userActivityActivityId,  account.user_login as userLogin,\n" +
                    " activity.name_activity as nameActivity, user_activities.state\n" +
                    " as state, user_activities.on_delete as onDelete, user_activities.spent_time as spentTime\n" +
                    "from user_activities join account on account.id = user_activities.id_user join activity on\n" +
                    "activity.id = user_activities.id_activity where user_activities.state = 0 and account.id = ?");
        } else {
            sql.append("select user_activities.id as userActivityId, user_activities.id_user as userActivityIdUser,\n" +
                            "user_activities.id_activity as userActivityActivityId,  account.user_login as userLogin,\n" +
                            " activity.name_activity as nameActivity, user_activities.state\n" +
                            " as state, user_activities.on_delete as onDelete, user_activities.spent_time as spentTime\n" +
                            "from user_activities join account on account.id = user_activities.id_user join activity on\n" +
                            "activity.id = user_activities.id_activity where user_activities.state = 0 and account.id = ? limit ")
                    .append(startBoundary).append(",").append(endBoundary);
        }

        List<UserActivities> userActivities = new ArrayList<>();
        LOG.trace("Start tracing MySQLUserActivitiesDAO#getAllUnActiveActivitiesByUserId");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement =
                             connection.prepareStatement(sql.toString())) {
                    connection.setAutoCommit(false);
                    statement.setInt(1, id);
                    statement.execute();
                    ResultSet resultSet = statement.getResultSet();
                    while (resultSet.next()) {
                        UserActivities userActivity = new UserActivities(resultSet.getInt("userActivityId"),
                                resultSet.getInt("userActivityIdUser"),
                                resultSet.getInt("userActivityActivityId"),
                                resultSet.getString("userLogin"),
                                resultSet.getString("nameActivity"),
                                resultSet.getInt("state"),
                                resultSet.getInt("onDelete"), resultSet.getString("spentTime"));
                        userActivities.add(userActivity);
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

        return userActivities;
    }

    @Override
    public void setUserActivity(int userId, int activityId) {
        LOG.trace("Start tracing MySQLUserActivitiesDAO#setUserActivity");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement
                             = connection.prepareStatement("INSERT INTO user_activities VALUES(default, ? , ? , 0, 1, 0)"
                        , Statement.RETURN_GENERATED_KEYS)) {
                    connection.setAutoCommit(false);
                    statement.setInt(1, userId);
                    statement.setInt(2, activityId);
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
    public void setOnDelete(int idUser, int activityId) {
        LOG.trace("Start tracing MySQLUserActivitiesDAO#setOnDelete");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement
                             = connection.prepareStatement("UPDATE user_activities SET on_delete = 1 WHERE id_user = ? and  id = ? LIMIT 100;"
                        , Statement.RETURN_GENERATED_KEYS)) {
                    connection.setAutoCommit(false);
                    statement.setInt(1, idUser);
                    statement.setInt(2, activityId);
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
    public void setUserTime(int userActivityId, String time) {
        LOG.trace("Start tracing MySQLUserActivitiesDAO#setUserTime");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement
                             = connection.prepareStatement("UPDATE user_activities SET spent_time = ? WHERE id = ? LIMIT 100"
                        , Statement.RETURN_GENERATED_KEYS)) {
                    connection.setAutoCommit(false);
                    statement.setString(1, time);
                    statement.setInt(2, userActivityId);
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
    public void setUserActivityFromUserSide(int userId, int activityId) {
        LOG.trace("Start tracing MySQLUserActivitiesDAO#setUserActivityFromUserSide");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement
                             = connection.prepareStatement("INSERT INTO user_activities VALUES(default, ? , ? , 0, 0, 0)"
                        , Statement.RETURN_GENERATED_KEYS)) {
                    connection.setAutoCommit(false);
                    statement.setInt(1, userId);
                    statement.setInt(2, activityId);
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
    public void makeActivityActive(int activityId) {
        LOG.trace("Start tracing MySQLUserActivitiesDAO#makeActivityActive");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement
                             = connection.prepareStatement("UPDATE user_activities SET state = 1 WHERE id=?"
                        , Statement.RETURN_GENERATED_KEYS)) {
                    connection.setAutoCommit(false);
                    statement.setInt(1, activityId);
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
    public void deleteUserActivity(int id) {
        LOG.trace("Start tracing MySQLUserActivitiesDAO#deleteUserActivity");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement
                             = connection.prepareStatement("DELETE FROM user_activities WHERE id = ?", Statement.RETURN_GENERATED_KEYS)) {
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
    public void updateUserActivity(int id, int userId, int activityId) {
        LOG.trace("Start tracing MySQLUserActivitiesDAO#updateUserActivity");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement
                             = connection.prepareStatement("UPDATE user_activities SET id_user = ?, id_activity = ? WHERE id=?"
                        , Statement.RETURN_GENERATED_KEYS)) {
                    connection.setAutoCommit(false);
                    statement.setInt(1, userId);
                    statement.setInt(2, activityId);
                    statement.setInt(3, id);
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
    public int getActivityId(int userActivityId) {
        int id = 0;
        LOG.trace("Start tracing MySQLUserActivitiesDAO#getActivityId");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement("select id_activity from user_activities where id = ?")) {
                    connection.setAutoCommit(false);
                    statement.setInt(1, userActivityId);
                    statement.execute();
                    ResultSet resultSet = statement.getResultSet();
                    if (resultSet.next()) {
                        id = resultSet.getInt("id_activity");
                    }
                    resultSet.close();
                    connection.commit();
                    return id;
                } catch (SQLException ex) {
                    LOG.error(ex.getLocalizedMessage());
                    connection.rollback();
                }
            }
        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return id;
    }

    @Override
    public int getCountOfActivitiesByCategoryId(int activityId) {
        int id = 0;
        LOG.trace("Start tracing MySQLUserActivitiesDAO#getCountOfActivitiesByCategoryId");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement("select COUNT(id_user) as countOfActivities from user_activities where id_activity = ?")) {
                    connection.setAutoCommit(false);
                    statement.setInt(1, activityId);
                    statement.execute();
                    ResultSet resultSet = statement.getResultSet();
                    if (resultSet.next()) {
                        id = resultSet.getInt("countOfActivities");
                    }
                    resultSet.close();
                    connection.commit();
                    return id;
                } catch (SQLException ex) {
                    LOG.error(ex.getLocalizedMessage());
                    connection.rollback();
                }
            }
        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return id;
    }

    @Override
    public List<UserActivities> getUserActivitiesDividedByPage(int startBoundary, int endBoundary) {
        List<UserActivities> userActivities = new ArrayList<>();
        LOG.trace("Start tracing MySQLUserActivitiesDAO#getUserActivitiesDividedByPage");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement =
                             connection.prepareStatement("select user_activities.id as userActivityId,\n" +
                                     "user_activities.id_user as userActivityIdUser,\n" +
                                     "user_activities.id_activity as userActivityActivityId,\n" +
                                     "account.user_login as userLogin,\n" +
                                     "activity.name_activity as nameActivity, user_activities.state as state, user_activities.on_delete as onDelete , user_activities.spent_time as spentTime \n" +
                                     "from user_activities join account on account.id = user_activities.id_user join activity on\n" +
                                     "activity.id = user_activities.id_activity limit " + startBoundary + "," + endBoundary)) {
                    connection.setAutoCommit(false);
                    statement.execute();
                    ResultSet resultSet = statement.getResultSet();
                    while (resultSet.next()) {
                        UserActivities userActivity = new UserActivities(resultSet.getInt("userActivityId"),
                                resultSet.getInt("userActivityIdUser"),
                                resultSet.getInt("userActivityActivityId"),
                                resultSet.getString("userLogin"),
                                resultSet.getString("nameActivity"),
                                resultSet.getInt("state"),
                                resultSet.getInt("onDelete"), resultSet.getString("spentTime"));
                        userActivities.add(userActivity);
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

        return userActivities;
    }

    @Override
    public int getCountOfRows() {
        int countOfRows = 0;
        LOG.trace("Start tracing MySQLUserActivitiesDAO#getCountOfRows");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) as count FROM user_activities")) {
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

    @Override
    public int getCountOfRows(int id) {
        int countOfRows = 0;
        LOG.trace("Start tracing MySQLUserActivitiesDAO#getCountOfRows");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) as count FROM user_activities where id_user = ? and state = 1")) {
                    connection.setAutoCommit(false);
                    statement.setInt(1, id);
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

    @Override
    public List<UserActivities> getUsersReport() {
        List<UserActivities> userActivities = new ArrayList<>();
        LOG.trace("Start tracing MySQLUserActivitiesDAO#getUsersReport");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement =
                             connection.prepareStatement("select account.user_login as userLogin, count(user_activities.id_activity) as activityCount,\n" +
                                     "TIME(SUM(TIMEDIFF(user_activities.spent_time, '00:00:00'))) as spentTime from account left outer join \n" +
                                     "user_activities on account.id = user_activities.id_user \n" +
                                     "group by account.user_login")) {
                    connection.setAutoCommit(false);
                    statement.execute();
                    ResultSet resultSet = statement.getResultSet();
                    while (resultSet.next()) {
                        UserActivities userActivity = new UserActivities(resultSet.getString("userLogin"),
                                resultSet.getInt("activityCount"),
                                resultSet.getString("spentTime"));
                        userActivities.add(userActivity);
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

        return userActivities;
    }
}
