package com.timeaccounting.DB.Entity;

import java.io.Serializable;

public class Category implements Serializable {

    private int id;
    private String categoryName;
    private int idParent;

    public Category(int id, String categoryName, int idParent) {
        this.id = id;
        this.categoryName = categoryName;
        this.idParent = idParent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getIdParent() {
        return idParent;
    }

    public void setIdParent(int idParent) {
        this.idParent = idParent;
    }
}
