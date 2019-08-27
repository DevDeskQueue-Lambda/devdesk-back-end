package com.digitalsolutionsbydon.devdesk.repositories;

import com.digitalsolutionsbydon.devdesk.models.Category;
import com.digitalsolutionsbydon.devdesk.view.CategoryView;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long>
{
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM TicketCategories WHERE ticketid = :ticketid")
    void deleteTicketCategoriesByTicketId(long ticketid);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO TicketCategories(ticketid, categoryid) values(:ticketid, :categoryid)", nativeQuery = true)
    void insertIntoTicketCategories(long ticketid, long categoryid);

    @Query(value = "SELECT categoryid, name FROM categories", nativeQuery = true)
    List<CategoryView> findAllByCustom();

    Category findByName(String name);
}
