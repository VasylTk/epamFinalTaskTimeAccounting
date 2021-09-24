package com.timeaccounting.DB.DAO;

import com.timeaccounting.DB.Entity.Account;

import java.util.List;

public interface UserDAO {

    Account findUserByLogin(String login);

    List<Account> getAllUsers();

    List<Account> getUsersDividedByPage(int startBoundary, int endBoundary);

    void registerUser(String userLogin, String userPassword, int userRole);

    void setNewPassword(int id, String password);

    void deleteUserById(int id);

    int getCountOfRows();

}
