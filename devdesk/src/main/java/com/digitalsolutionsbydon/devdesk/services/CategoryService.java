package com.digitalsolutionsbydon.devdesk.services;

import com.digitalsolutionsbydon.devdesk.models.Category;
import com.digitalsolutionsbydon.devdesk.view.CategoryView;

import java.util.List;

public interface CategoryService
{
    List<Category> findAll();

    List<CategoryView> findAllByCustom();

    Category findByName(String name);

    Category save(Category category);
}
