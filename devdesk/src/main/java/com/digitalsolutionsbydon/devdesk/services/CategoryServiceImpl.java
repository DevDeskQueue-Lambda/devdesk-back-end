package com.digitalsolutionsbydon.devdesk.services;

import com.digitalsolutionsbydon.devdesk.models.Category;
import com.digitalsolutionsbydon.devdesk.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "categoryService")
public class CategoryServiceImpl implements CategoryService
{
    @Autowired
    CategoryRepository categoryRepo;

    @Override
    public List<Category> findAll()
    {
        List<Category> list = new ArrayList<>();
        categoryRepo.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Category save(Category category)
    {
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        return categoryRepo.save(newCategory);
    }
}
