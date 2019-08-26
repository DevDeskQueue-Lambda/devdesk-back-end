package com.digitalsolutionsbydon.devdesk.repositories;

import com.digitalsolutionsbydon.devdesk.models.Status;
import org.springframework.data.repository.CrudRepository;

public interface StatusRepository extends CrudRepository<Status, Long>
{
    Status findByName(String name);
}
