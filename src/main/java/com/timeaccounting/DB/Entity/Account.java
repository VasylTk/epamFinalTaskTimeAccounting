package com.timeaccounting.DB.Entity;

import java.io.Serializable;

/**
 * Account entity.
 * Fields: id, userLogin, userPassword, userRole, userRoleName
 */
public class Account implements Serializable {

    private int id;
    private String userLogin;
    private String userPassword;
    private int userRole;
    private String userRoleName;

    public Account() {
    }

    public Account(int id, String userLogin, String userPassword, int userRole) {
        this.id = id;
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.userRole = userRole;
    }

    public Account(int id, String userLogin, String userPassword, int userRole, String userRoleName) {
        this.id = id;
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.userRole = userRole;
        this.userRoleName = userRoleName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public String getUserRoleName() {
        return userRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
    }

}
