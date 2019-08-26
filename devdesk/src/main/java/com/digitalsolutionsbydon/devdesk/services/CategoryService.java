package com.digitalsolutionsbydon.devdesk.services;

import com.digitalsolutionsbydon.devdesk.models.Category;

import java.util.List;

public interface CategoryService
{
    List<Category> findAll();

    Category save(Category category);
}
