package com.digitalsolutionsbydon.devdesk.services;

import com.digitalsolutionsbydon.devdesk.models.Ticket;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface TicketService
{
    List<Ticket> findAll(Pageable pageable);

    Ticket findTicketById(long id);

    Ticket assignTicket(long id);

    Ticket resolveTicket(long id);

    Ticket unAssignTicket(long id);

    Ticket archiveTicket(long id);

    Ticket save(Ticket ticket);

    Ticket update(Ticket ticket, long id);

    Ticket adminAssignTicket(long id, long userid);

    void deleteTicketById(long id);
}
