package com.digitalsolutionsbydon.devdesk.repositories;

import com.digitalsolutionsbydon.devdesk.models.Ticket;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TicketRepository extends PagingAndSortingRepository<Ticket, Long>
{
}
