package com.digitalsolutionsbydon.devdesk.services;

import com.digitalsolutionsbydon.devdesk.models.Ticket;

import java.awt.print.Pageable;
import java.util.List;

public interface TicketService
{
    List<Ticket> findAll(Pageable pageable);

    Ticket findTicketById(long id);

    Ticket save(Ticket ticket);

    Ticket update(Ticket ticket, long id);

    void deleteTicketById(long id);
}
