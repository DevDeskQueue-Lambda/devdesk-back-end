package com.digitalsolutionsbydon.devdesk.repositories;

import com.digitalsolutionsbydon.devdesk.models.Category;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryRepository extends CrudRepository<Category, Long>
{


}
