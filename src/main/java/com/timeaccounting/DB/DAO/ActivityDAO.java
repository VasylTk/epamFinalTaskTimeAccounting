package com.timeaccounting.DB.DAO;

import com.timeaccounting.DB.Entity.Activity;

import java.util.List;

/**
 * Basic interface for all ActivityDAO.
 *
 * @author V. Tkachov
 */
public interface ActivityDAO {

    List<Activity> getAllActivities();

    void addActivity(String nameActivity, int idCategory);

    void deleteActivity(int id);

    void updateActivityById(int id, String nameActivity, int idCategory);

    int findActivityByName(String nameActivity);

    List<Activity> getActivityDividedByPage(int startBoundary, int endBoundary);

    int getCountOfRows();

    //report
    List<Activity> getAllActivitiesReport();

    List<Activity> getAllSortActivities(String sortingMethod, String idCategory);

    List<Activity> getAllSortCategories(String sortingMethod, String idCategory);

    List<Activity> getAllSortUserCount(String sortingMethod, String idCategory);

    List<Activity> getAllFilterCategory(int idCategory);

}
