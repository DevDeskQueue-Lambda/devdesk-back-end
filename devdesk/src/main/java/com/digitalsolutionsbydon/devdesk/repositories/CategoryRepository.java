package com.digitalsolutionsbydon.devdesk.repositories;

import com.digitalsolutionsbydon.devdesk.models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long>
{
}
