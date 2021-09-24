package com.timeaccounting.DB.DAO;

import com.timeaccounting.DB.Entity.Category;

import java.util.List;

public interface CategoryDAO {

    List<Category> getAllCategories();

    void addCategory(String categoryName, int idParent);

    void deleteCategoryById(int id);

    void updateCategory(String categoryName, int id);

    List<Category> getCategoriesDividedByPage(int startBoundary, int endBoundary);

    int getCountOfRows();
}
