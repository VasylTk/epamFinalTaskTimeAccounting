package com.timeaccounting.DB.Entity;

import java.io.Serializable;

public class Activity implements Serializable {

    private int id;
    private String nameActivity;
    private int idCategory;
    private String categoryName;
    private int userCount;

    public Activity() {
    }

    public Activity(int id, String nameActivity, String categoryName) {
        this.id = id;
        this.nameActivity = nameActivity;
        this.categoryName = categoryName;
    }

    public Activity(int id, String nameActivity, String categoryName, int userCount) {
        this.id = id;
        this.nameActivity = nameActivity;
        this.categoryName = categoryName;
        this.userCount = userCount;
    }

    public Activity(int id, String nameActivity, int idCategory, String categoryName) {
        this.id = id;
        this.nameActivity = nameActivity;
        this.idCategory = idCategory;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameActivity() {
        return nameActivity;
    }

    public void setNameActivity(String nameActivity) {
        this.nameActivity = nameActivity;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

}
