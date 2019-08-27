package com.digitalsolutionsbydon.devdesk.repositories;

import com.digitalsolutionsbydon.devdesk.models.Category;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryRepository extends CrudRepository<Category, Long>
{
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM TicketMapper WHERE ticketid = :ticketid")
    void deleteTicketMapperByTicketId(long ticketid);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO TicketMapper(ticketid, categoryid) values(:ticketid, :categoryid)", nativeQuery = true)
    void insertIntoTicketMapper(long ticketid, long categoryid);
}
