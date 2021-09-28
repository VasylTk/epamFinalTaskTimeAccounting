package com.timeaccounting.DB.Entity;

import java.io.Serializable;

/**
 * UserActivities entity.
 * Fields: id, userId, activityId, userLogin, activityName,
 * activityCount, activityCount, spentTime, state, onDelete
 */
public class UserActivities implements Serializable {

    private int id;
    private int userId;
    private int activityId;
    private String userLogin;
    private String activityName;
    private int activityCount;
    private String spentTime;
    private int state;
    private int onDelete;

    public UserActivities() {
    }

    public UserActivities(String userLogin, int activityCount, String spentTime) {
        this.userLogin = userLogin;
        this.activityCount = activityCount;
        this.spentTime = spentTime;
    }

    public UserActivities(int id, int userId, int activityId, String userLogin, String activityName, int state, int onDelete, String spentTime) {
        this.id = id;
        this.userId = userId;
        this.activityId = activityId;
        this.userLogin = userLogin;
        this.activityName = activityName;
        this.state = state;
        this.onDelete = onDelete;
        this.spentTime = spentTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getActivityCount() {
        return activityCount;
    }

    public void setActivityCount(int activityCount) {
        this.activityCount = activityCount;
    }

    public String getSpentTime() {
        return spentTime;
    }

    public void setSpentTime(String spentTime) {
        this.spentTime = spentTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getOnDelete() {
        return onDelete;
    }

    public void setOnDelete(int onDelete) {
        this.onDelete = onDelete;
    }

}
