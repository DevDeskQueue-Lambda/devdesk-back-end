package com.digitalsolutionsbydon.devdesk.services;

import com.digitalsolutionsbydon.devdesk.exceptions.BadRequestException;
import com.digitalsolutionsbydon.devdesk.exceptions.ResourceNotFoundException;
import com.digitalsolutionsbydon.devdesk.models.Category;
import com.digitalsolutionsbydon.devdesk.repositories.CategoryRepository;
import com.digitalsolutionsbydon.devdesk.view.CategoryView;
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
        categoryRepo.findAll()
                    .iterator()
                    .forEachRemaining(list::add);
        return list;
    }

    @Override
    public Category save(Category category)
    {
        if (categoryRepo.findByName(category.getName()) != null)
        {
            throw new BadRequestException(category.getName() + " is already defined.");
        }
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        return categoryRepo.save(newCategory);
    }

    @Override
    public List<CategoryView> findAllByCustom()
    {
        List<CategoryView> list = new ArrayList<>();
        categoryRepo.findAllByCustom()
                    .iterator()
                    .forEachRemaining(list::add);
        return list;
    }

    @Override
    public Category findByName(String name)
    {
        Category category = categoryRepo.findByName(name);

        if (category != null)
        {
            return category;
        } else
        {
            throw new ResourceNotFoundException("The category with '" + name + "' does not exist in the system.");
        }
    }
}
