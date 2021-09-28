package com.timeaccounting.DB.DAO.mysql;

import com.timeaccounting.DB.DAO.CategoryDAO;
import com.timeaccounting.DB.DBManager;
import com.timeaccounting.DB.Entity.Category;
import com.timeaccounting.DB.Query;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access MySQLCategoryDAO.
 *
 * @author V. Tkachov
 */
public class MySQLCategoryDAO implements CategoryDAO {

    public static final Logger LOG = Logger.getLogger(MySQLCategoryDAO.class);

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        LOG.trace("Start tracing MySQLCategoryDAO#getAllCategories");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(Query.GET_ALL_CATEGORIES)) {
                connection.setAutoCommit(false);
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    Category category = new Category(resultSet.getInt("id"),
                            resultSet.getString("name_category"),
                            resultSet.getInt("id_parent"));
                    categories.add(category);
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

        return categories;
    }

    @Override
    public void addCategory(String categoryName, int idParent) {
        LOG.trace("Start tracing MySQLCategoryDAO#addCategory");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement
                         = connection.prepareStatement(Query.ADD_CATEGORY,
                    Statement.RETURN_GENERATED_KEYS)) {
                connection.setAutoCommit(false);
                statement.setString(1, categoryName);
                statement.setInt(2, idParent);
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
    public void deleteCategoryById(int id) {
        LOG.trace("Start tracing MySQLCategoryDAO#deleteCategoryById");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement
                         = connection.prepareStatement(Query.DELETE_CATEGORY_BY_ID, Statement.RETURN_GENERATED_KEYS)) {
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
    public void updateCategory(String categoryName, int id) {
        LOG.trace("Start tracing MySQLCategoryDAO#updateCategory");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement
                         = connection.prepareStatement(Query.UPDATE_CATEGORY,
                    Statement.RETURN_GENERATED_KEYS)) {
                connection.setAutoCommit(false);
                statement.setString(1, categoryName);
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
    public List<Category> getCategoriesDividedByPage(int startBoundary, int endBoundary) {
        List<Category> categories = new ArrayList<>();
        LOG.trace("Start tracing MySQLCategoryDAO#getCategoriesDividedByPage");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(Query.GET_CATEGORIES_DIVIDED_BY_PAGE + startBoundary + "," + endBoundary)) {
                connection.setAutoCommit(false);
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    Category category = new Category(resultSet.getInt("id"),
                            resultSet.getString("name_category"),
                            resultSet.getInt("id_parent"));
                    categories.add(category);
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
        return categories;
    }

    @Override
    public int getCountOfRows() {
        int countOfRows = 0;
        LOG.trace("Start tracing MySQLCategoryDAO#getCountOfRows");
        try (Connection connection = DBManager.getInstance().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(Query.GET_COUNT_OF_ROWS_CATEGORY)) {
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
