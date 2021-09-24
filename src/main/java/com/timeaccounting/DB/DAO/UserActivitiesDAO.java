package com.timeaccounting.DB.DAO;

import com.timeaccounting.DB.Entity.UserActivities;

import java.util.List;

public interface UserActivitiesDAO {

    List<UserActivities> getAllUserActivities();

    List<UserActivities> getAllUserActivitiesByUserId(int id);

    List<UserActivities> getAllUserActivitiesByUserId(int id, int startBoundary, int endBoundary);

    List<UserActivities> getAllUnActiveActivitiesByUserId(int id, int startBoundary, int endBoundary);

    void setUserActivity(int userId, int activityId);

    void setOnDelete(int userId, int activityId);

    void setUserTime(int userActivityId, String time);

    void setUserActivityFromUserSide(int userId, int activityId);

    void makeActivityActive(int activityId);

    void deleteUserActivity(int id);

    void updateUserActivity(int id, int userId, int activityId);

    int getActivityId(int userActivityId);

    int getCountOfActivitiesByCategoryId(int activityId);

    List<UserActivities> getUserActivitiesDividedByPage(int startBoundary, int endBoundary);

    int getCountOfRows();

    int getCountOfRows(int id);

    //Report
    List<UserActivities> getUsersReport();

}
