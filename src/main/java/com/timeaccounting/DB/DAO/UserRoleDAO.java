package com.timeaccounting.DB.DAO;

import com.timeaccounting.DB.Entity.UserRole;

import java.util.List;

/**
 * Basic interface for all UserRoleDAO.
 *
 * @author V. Tkachov
 */
public interface UserRoleDAO {

    List<UserRole> getUserRoles();

}
