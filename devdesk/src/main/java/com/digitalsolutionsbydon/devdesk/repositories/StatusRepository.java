package com.digitalsolutionsbydon.devdesk.repositories;

import com.digitalsolutionsbydon.devdesk.models.Status;
import com.digitalsolutionsbydon.devdesk.view.StatusView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StatusRepository extends CrudRepository<Status, Long>
{
    @Query(value="SELECT statusid, name FROM status", nativeQuery = true)
    List<StatusView> findAllCustom();

    Status findByName(String name);
}
