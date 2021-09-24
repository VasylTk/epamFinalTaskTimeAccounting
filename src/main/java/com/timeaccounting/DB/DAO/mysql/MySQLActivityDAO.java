package com.timeaccounting.DB.DAO.mysql;

import com.timeaccounting.DB.DAO.ActivityDAO;
import com.timeaccounting.DB.DBManager;
import com.timeaccounting.DB.Entity.Activity;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLActivityDAO implements ActivityDAO {

    public static final Logger LOG = Logger.getLogger(MySQLActivityDAO.class);

    @Override
    public List<Activity> getAllActivities() {
        List<Activity> activities = new ArrayList<>();
        LOG.trace("Start tracing MySQLActivityDAO#getAllActivities");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement =
                             connection.prepareStatement("SELECT activity.id, activity.name_activity," +
                                     " activity.id_category, category.name_category\n" +
                                     " FROM activity join category on category.id = activity.id_category")) {
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
            if (connection != null) {
                try (PreparedStatement statement
                             = connection.prepareStatement("INSERT INTO activity VALUES(default, ?, ?)"
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
            }
        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
    }

    @Override
    public void deleteActivity(int id) {
        LOG.trace("Start tracing MySQLActivityDAO#deleteActivity");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement
                             = connection.prepareStatement("DELETE FROM activity WHERE id = ?", Statement.RETURN_GENERATED_KEYS)) {
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
    public void updateActivityById(int id, String nameActivity, int idCategory) {
        LOG.trace("Start tracing MySQLActivityDAO#updateActivityById");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement
                             = connection.prepareStatement("UPDATE activity SET name_activity = ?, id_category=? WHERE id=?"
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
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement("select * from activity where name_activity = ?")) {
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
            if (connection != null) {
                try (PreparedStatement statement =
                             connection.prepareStatement("SELECT activity.id, activity.name_activity, activity.id_category, category.name_category\n" +
                                     "FROM activity left outer join category on category.id = activity.id_category limit "
                                     + startBoundary + "," + endBoundary)) {
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
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) as count FROM activity")) {
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
    public List<Activity> getAllActivitiesReport() {
        List<Activity> activities = new ArrayList<>();
        LOG.trace("Start tracing MySQLActivityDAO#getAllActivitiesReport");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement =
                             connection.prepareStatement("select activity.id, activity.name_activity as activity, category.name_category as category, \n" +
                                     "count(user_activities.id_user) as user_count from activity\n" +
                                     "left outer join category on activity.id_category = category.id\n" +
                                     "join user_activities on user_activities.id_activity = activity.id \n" +
                                     "group by activity.name_activity, category.name_category")) {
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
            sqlQuery = "select activity.id, activity.name_activity as activity, category.name_category as category, \n" +
                    "count(user_activities.id_user) as user_count from activity\n" +
                    "left outer join category on activity.id_category = category.id \n" +
                    "join user_activities on user_activities.id_activity = activity.id\n" +
                    "group by activity.name_activity, category.name_category order by activity.name_activity " + sortingMethod;
        } else {
            sqlQuery = "select activity.id, activity.name_activity as activity, category.name_category as category, \n" +
                    "count(user_activities.id_user) as user_count from activity\n" +
                    "left outer join category on activity.id_category = category.id \n" +
                    "join user_activities on user_activities.id_activity = activity.id where category.id = ?\n" +
                    "group by activity.name_activity, category.name_category order by activity.name_activity " + sortingMethod;
        }
        LOG.trace("Start tracing MySQLActivityDAO#getAllSortActivities");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
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
            }

        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return activities;
    }

    @Override
    public List<Activity> getAllSortCategoriesASC() {
        List<Activity> activities = new ArrayList<>();
        LOG.trace("Start tracing MySQLActivityDAO#getAllSortCategoriesASC");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement =
                             connection.prepareStatement("select activity.id, activity.name_activity as activity, category.name_category as category, \n" +
                                     "count(user_activities.id_user) as user_count from activity\n" +
                                     "left outer join category on activity.id_category = category.id\n" +
                                     "join user_activities on user_activities.id_activity = activity.id \n" +
                                     "group by activity.name_activity, category.name_category order by category.name_category ASC")) {
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
            }

        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return activities;
    }

    @Override
    public List<Activity> getAllSortCategoriesDESC() {
        List<Activity> activities = new ArrayList<>();
        LOG.trace("Start tracing MySQLActivityDAO#getAllSortCategoriesDESC");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement =
                             connection.prepareStatement("select activity.id, activity.name_activity as activity, category.name_category as category, \n" +
                                     "count(user_activities.id_user) as user_count from activity\n" +
                                     "left outer join category on activity.id_category = category.id\n" +
                                     "join user_activities on user_activities.id_activity = activity.id \n" +
                                     "group by activity.name_activity, category.name_category order by category.name_category DESC")) {
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
            sqlQuery = "select activity.id, activity.name_activity as activity, category.name_category as category, \n" +
                    "                                     count(user_activities.id_user) as user_count from activity\n" +
                    "                                     left outer join category on activity.id_category = category.id\n" +
                    "                                     join user_activities on user_activities.id_activity = activity.id \n" +
                    "                                     group by activity.name_activity, category.name_category order by category.name_category " + sortingMethod;
        } else {
            sqlQuery = "select activity.id, activity.name_activity as activity, category.name_category as category, \n" +
                    "                                     count(user_activities.id_user) as user_count from activity\n" +
                    "                                     left outer join category on activity.id_category = category.id\n" +
                    "                                     join user_activities on user_activities.id_activity = activity.id where category.id = ?\n" +
                    "                                     group by activity.name_activity, category.name_category order by category.name_category " + sortingMethod;
        }
        LOG.trace("Start tracing MySQLActivityDAO#getAllSortCategories");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
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
            sqlQuery = "select activity.id, activity.name_activity as activity, category.name_category as category, \n" +
                    "                                     count(user_activities.id_user) as user_count from activity\n" +
                    "                                     left outer join category on activity.id_category = category.id\n" +
                    "                                     join user_activities on user_activities.id_activity = activity.id \n" +
                    "group by activity.name_activity, category.name_category order by user_count " + sortingMethod;
        } else {
            sqlQuery = "select activity.id, activity.name_activity as activity, category.name_category as category, \n" +
                    "                                     count(user_activities.id_user) as user_count from activity\n" +
                    "                                     left outer join category on activity.id_category = category.id\n" +
                    "                                     join user_activities on user_activities.id_activity = activity.id where category.id = ?\n" +
                    "  group by activity.name_activity, category.name_category order by user_count " + sortingMethod;
        }
        LOG.trace("Start tracing MySQLActivityDAO#getAllSortUserCount");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
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
            }

        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return activities;
    }

    @Override
    public List<Activity> getAllSortUserCountASC() {
        List<Activity> activities = new ArrayList<>();
        LOG.trace("Start tracing MySQLActivityDAO#getAllSortUserCountASC");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement =
                             connection.prepareStatement("select activity.id, activity.name_activity as activity, category.name_category as category, \n" +
                                     "count(user_activities.id_user) as user_count from activity\n" +
                                     "left outer join category on activity.id_category = category.id\n" +
                                     "join user_activities on user_activities.id_activity = activity.id \n" +
                                     "group by activity.name_activity, category.name_category order by user_count ASC")) {
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
            }

        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return activities;
    }

    @Override
    public List<Activity> getAllSortUserCountDESC() {
        List<Activity> activities = new ArrayList<>();
        LOG.trace("Start tracing MySQLActivityDAO#getAllSortUserCountDESC");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement =
                             connection.prepareStatement("select activity.id, activity.name_activity as activity, category.name_category as category, \n" +
                                     "count(user_activities.id_user) as user_count from activity\n" +
                                     "left outer join category on activity.id_category = category.id\n" +
                                     "join user_activities on user_activities.id_activity = activity.id \n" +
                                     "group by activity.name_activity, category.name_category order by user_count DESC")) {
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
            if (connection != null) {
                try (PreparedStatement statement =
                             connection.prepareStatement("select activity.id, activity.name_activity as activity, category.name_category as category, \n" +
                                     "count(user_activities.id_user) as user_count from activity\n" +
                                     "left outer join category on activity.id_category = category.id \n" +
                                     "join user_activities on user_activities.id_activity = activity.id where category.id = ?\n" +
                                     "group by activity.name_activity, category.name_category")) {
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
            }

        } catch (SQLException ex) {
            LOG.error(ex.getLocalizedMessage());
        }
        return activities;
    }

}
