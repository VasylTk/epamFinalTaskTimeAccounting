package com.timeaccounting.DB.DAO.mysql;

import com.timeaccounting.DB.DAO.ActivityDAO;
import com.timeaccounting.DB.DBManager;
import com.timeaccounting.DB.Entity.Activity;
import com.timeaccounting.DB.Query;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Data access MySQLActivityDAO.
 *
 * @author V. Tkachov
 */
public class MySQLActivityDAO implements ActivityDAO {

    public static final Logger LOG = Logger.getLogger(MySQLActivityDAO.class);

    @Override
    public List<Activity> getAllActivities() {
        List<Activity> activities = new ArrayList<>();
        LOG.trace("Start tracing MySQLActivityDAO#getAllActivities");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(Query.SELECT_ALL_ACTIVITIES)) {
                connection.setAutoCommit(false);
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    Activity activity = new Activity(resultSet.getInt("id"),
                            resultSet.getString("name_activity"),
                            resultSet.getInt("id_category"),
                            resultSet.getString("name_category"));
                    activities.add(activity);
                }
                resultSet.close();
                connection.commit();
            } catch (SQLException ex) {
                LOG.error(ex.getLocalizedMessage());
                connection.rollback();
            }
        } catch (SQLException ex) {
            LOG.info(ex.getLocalizedMessage());
        }
        return activities;
    }

    @Override
    public void addActivity(String nameActivity, int idCategory) {
        LOG.trace("Start tracing MySQLActivityDAO#addActivity");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement
                         = connection.prepareStatement(Query.ADD_ACTIVITY
                    , Statement.RETURN_GENERATED_KEYS)) {
                connection.setAutoCommit(false);
                statement.setString(1, nameActivity);
                statement.setInt(2, idCategory);
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
    public void deleteActivity(int id) {
        LOG.trace("Start tracing MySQLActivityDAO#deleteActivity");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement
                         = connection.prepareStatement(Query.DELETE_ACTIVITY, Statement.RETURN_GENERATED_KEYS)) {
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
    public void updateActivityById(int id, String nameActivity, int idCategory) {
        LOG.trace("Start tracing MySQLActivityDAO#updateActivityById");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement
                         = connection.prepareStatement(Query.UPDATE_ACTIVITY_BY_ID
                    , Statement.RETURN_GENERATED_KEYS)) {
                connection.setAutoCommit(false);
                statement.setString(1, nameActivity);
                statement.setInt(2, idCategory);
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
    public int findActivityByName(String nameActivity) {
        int id = 0;
        LOG.trace("Start tracing MySQLActivityDAO#findActivityByName");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(Query.FIND_ACTIVITY_BY_NAME)) {
                connection.setAutoCommit(false);
                statement.setString(1, nameActivity);
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    id = resultSet.getInt("id");
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
        return id;
    }

    @Override
    public List<Activity> getActivityDividedByPage(int startBoundary, int endBoundary) {
        List<Activity> activities = new ArrayList<>();
        LOG.trace("Start tracing MySQLActivityDAO#getActivityDividedByPage");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(Query.GET_ACTIVITY_DIVIDED_BY_PAGE + startBoundary + "," + endBoundary)) {
                connection.setAutoCommit(false);
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    Activity activity = new Activity(resultSet.getInt("id"),
                            resultSet.getString("name_activity"),
                            resultSet.getInt("id_category"),
                            resultSet.getString("name_category"));
                    activities.add(activity);
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

        return activities;
    }

    @Override
    public int getCountOfRows() {
        int countOfRows = 0;
        LOG.trace("Start tracing MySQLActivityDAO#getCountOfRows");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(Query.GET_COUNT_OF_ROWS_ACTIVITY_DAO)) {
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
    public List<Activity> getAllActivitiesReport() {
        List<Activity> activities = new ArrayList<>();
        LOG.trace("Start tracing MySQLActivityDAO#getAllActivitiesReport");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(Query.GET_ALL_ACTIVITIES_REPORT)) {
                connection.setAutoCommit(false);
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    Activity activity = new Activity(resultSet.getInt("id"),
                            resultSet.getString("activity"),
                            resultSet.getString("category"),
                            resultSet.getInt("user_count"));
                    activities.add(activity);
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
        return activities;
    }

    @Override
    public List<Activity> getAllSortActivities(String sortingMethod, String idCategory) {
        List<Activity> activities = new ArrayList<>();
        String sqlQuery;
        if (idCategory.trim().length() == 0) {
            sqlQuery = Query.GET_ALL_SORT_ACTIVITIES + sortingMethod;
        } else {
            sqlQuery = Query.GET_SORT_ACTIVITIES_BY_CATEGORY_ID + sortingMethod;
        }
        LOG.trace("Start tracing MySQLActivityDAO#getAllSortActivities");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(sqlQuery)) {
                connection.setAutoCommit(false);
                if (!idCategory.isEmpty()) {
                    statement.setInt(1, Integer.parseInt(idCategory));
                }
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    Activity activity = new Activity(resultSet.getInt("id"),
                            resultSet.getString("activity"),
                            resultSet.getString("category"),
                            resultSet.getInt("user_count"));
                    activities.add(activity);
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
        return activities;
    }

    @Override
    public List<Activity> getAllSortCategories(String sortingMethod, String idCategory) {
        List<Activity> activities = new ArrayList<>();
        String sqlQuery;
        if (idCategory.trim().length() == 0) {
            sqlQuery = Query.GET_ALL_SORT_CATEGORIES + sortingMethod;
        } else {
            sqlQuery = Query.GET_ALL_SORT_CATEGORIES_BY_CATEGORY_ID + sortingMethod;
        }
        LOG.trace("Start tracing MySQLActivityDAO#getAllSortCategories");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(sqlQuery)) {
                connection.setAutoCommit(false);
                if (idCategory.trim().length() != 0) {
                    statement.setInt(1, Integer.parseInt(idCategory));
                }
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    Activity activity = new Activity(resultSet.getInt("id"),
                            resultSet.getString("activity"),
                            resultSet.getString("category"),
                            resultSet.getInt("user_count"));
                    activities.add(activity);
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
        return activities;
    }

    @Override
    public List<Activity> getAllSortUserCount(String sortingMethod, String idCategory) {
        List<Activity> activities = new ArrayList<>();
        String sqlQuery;
        if (idCategory.trim().length() == 0) {
            sqlQuery = Query.GET_ALL_SORT_USER_COUNT + sortingMethod;
        } else {
            sqlQuery = Query.GET_ALL_SORT_USER_COUNT_BY_CATEGORY_ID + sortingMethod;
        }
        LOG.trace("Start tracing MySQLActivityDAO#getAllSortUserCount");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(sqlQuery)) {
                connection.setAutoCommit(false);
                if (idCategory.trim().length() != 0) {
                    statement.setInt(1, Integer.parseInt(idCategory));
                }
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    Activity activity = new Activity(resultSet.getInt("id"),
                            resultSet.getString("activity"),
                            resultSet.getString("category"),
                            resultSet.getInt("user_count"));
                    activities.add(activity);
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
        return activities;
    }

    @Override
    public List<Activity> getAllFilterCategory(int idCategory) {
        List<Activity> activities = new ArrayList<>();
        LOG.trace("Start tracing MySQLActivityDAO#getAllFilterCategory");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(Query.GET_ALL_FILTER_CATEGORY)) {
                connection.setAutoCommit(false);
                statement.setInt(1, idCategory);
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    Activity activity = new Activity(resultSet.getInt("id"),
                            resultSet.getString("activity"),
                            resultSet.getString("category"),
                            resultSet.getInt("user_count"));
                    activities.add(activity);
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
        return activities;
    }

}
