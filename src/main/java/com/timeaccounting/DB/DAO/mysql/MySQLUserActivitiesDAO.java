package com.timeaccounting.DB.DAO.mysql;

import com.timeaccounting.DB.DAO.UserActivitiesDAO;
import com.timeaccounting.DB.DBManager;
import com.timeaccounting.DB.Entity.UserActivities;
import com.timeaccounting.DB.Query;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access MySQLUserActivitiesDAO.
 *
 * @author V. Tkachov
 */
public class MySQLUserActivitiesDAO implements UserActivitiesDAO {

    public static final Logger LOG = Logger.getLogger(MySQLUserActivitiesDAO.class);

    @Override
    public List<UserActivities> getAllUserActivities() {
        List<UserActivities> userActivities = new ArrayList<>();
        LOG.trace("Start tracing MySQLUserActivitiesDAO#getAllUserActivities");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(Query.GET_ALL_USER_ACTIVITIES)) {
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
            try (PreparedStatement statement =
                         connection.prepareStatement(Query.GET_ALL_USER_ACTIVITIES_BY_USER_ID)) {
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
            try (PreparedStatement statement =
                         connection.prepareStatement(Query.GET_ALL_USER_ACTIVITIES_BY_USER_ID_WITH_LIMIT + startBoundary + "," + endBoundary)) {
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

        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }

        return userActivities;
    }

    @Override
    public List<UserActivities> getAllUnActiveActivitiesByUserId(int id, int startBoundary, int endBoundary) {
        StringBuffer sql = new StringBuffer();
        if (startBoundary == 0 && endBoundary == 0) {
            sql.append(Query.GET_ALL_UN_ACTIVE_ACTIVITIES_BY_USER_ID);
        } else {
            sql.append(Query.GET_ALL_UN_ACTIVE_ACTIVITIES_BY_USER_ID_LIMIT)
                    .append(startBoundary)
                    .append(",")
                    .append(endBoundary);
        }

        List<UserActivities> userActivities = new ArrayList<>();
        LOG.trace("Start tracing MySQLUserActivitiesDAO#getAllUnActiveActivitiesByUserId");
        try (Connection connection = DBManager.getInstance().getConnection()) {
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

        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }

        return userActivities;
    }

    @Override
    public void setUserActivity(int userId, int activityId) {
        LOG.trace("Start tracing MySQLUserActivitiesDAO#setUserActivity");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement
                         = connection.prepareStatement(Query.SET_USER_ACTIVITY
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

        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
    }

    @Override
    public void setOnDelete(int idUser, int activityId) {
        LOG.trace("Start tracing MySQLUserActivitiesDAO#setOnDelete");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement
                         = connection.prepareStatement(Query.SET_ON_DELETE,
                    Statement.RETURN_GENERATED_KEYS)) {
                connection.setAutoCommit(false);
                statement.setInt(1, idUser);
                statement.setInt(2, activityId);
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
    public void setUserTime(int userActivityId, String time) {
        LOG.trace("Start tracing MySQLUserActivitiesDAO#setUserTime");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement
                         = connection.prepareStatement(Query.SET_USER_TIME,
                    Statement.RETURN_GENERATED_KEYS)) {
                connection.setAutoCommit(false);
                statement.setString(1, time);
                statement.setInt(2, userActivityId);
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
    public void setUserActivityFromUserSide(int userId, int activityId) {
        LOG.trace("Start tracing MySQLUserActivitiesDAO#setUserActivityFromUserSide");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement
                         = connection.prepareStatement(Query.SET_USER_ACTIVITY_FROM_USER_SIDE,
                    Statement.RETURN_GENERATED_KEYS)) {
                connection.setAutoCommit(false);
                statement.setInt(1, userId);
                statement.setInt(2, activityId);
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
    public void makeActivityActive(int activityId) {
        LOG.trace("Start tracing MySQLUserActivitiesDAO#makeActivityActive");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement
                         = connection.prepareStatement(Query.MAKE_ACTIVITY_ACTIVE,
                    Statement.RETURN_GENERATED_KEYS)) {
                connection.setAutoCommit(false);
                statement.setInt(1, activityId);
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
    public void deleteUserActivity(int id) {
        LOG.trace("Start tracing MySQLUserActivitiesDAO#deleteUserActivity");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement
                         = connection.prepareStatement(Query.DELETE_USER_ACTIVITY, Statement.RETURN_GENERATED_KEYS)) {
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
    public void updateUserActivity(int id, int userId, int activityId) {
        LOG.trace("Start tracing MySQLUserActivitiesDAO#updateUserActivity");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement
                         = connection.prepareStatement(Query.UPDATE_USER_ACTIVITY,
                    Statement.RETURN_GENERATED_KEYS)) {
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
        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
    }

    @Override
    public int getActivityId(int userActivityId) {
        int id = 0;
        LOG.trace("Start tracing MySQLUserActivitiesDAO#getActivityId");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(Query.GET_ACTIVITY_ID)) {
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
            try (PreparedStatement statement = connection.prepareStatement(Query.GET_COUNT_OF_ACTIVITIES_BY_CATEGORY_ID)) {
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
            try (PreparedStatement statement =
                         connection.prepareStatement(Query.GET_USER_ACTIVITIES_DIVIDED_BY_PAGE + startBoundary + "," + endBoundary)) {
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
            try (PreparedStatement statement = connection.prepareStatement(Query.GET_COUNT_OF_ROWS_USER_ACTIVITIES)) {
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

    @Override
    public int getCountOfRows(int id) {
        int countOfRows = 0;
        LOG.trace("Start tracing MySQLUserActivitiesDAO#getCountOfRows");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(Query.GET_COUNT_OF_ROWS_USER_ACTIVITIES_BY_USER_ID)) {
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
            try (PreparedStatement statement =
                         connection.prepareStatement(Query.GET_USERS_REPORT)) {
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
        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }

        return userActivities;
    }
}
