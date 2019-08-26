package com.digitalsolutionsbydon.devdesk.services;

import com.digitalsolutionsbydon.devdesk.models.Ticket;
import com.digitalsolutionsbydon.devdesk.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service(value="ticketService")
public class TicketServiceImpl implements TicketService
{
    @Autowired
    TicketRepository ticketRepo;

    @Override
    public List<Ticket> findAll(Pageable pageable)
    {
        return null;
    }

    @Override
    public Ticket findTicketById(long id)
    {
        return null;
    }

    @Override
    public Ticket save(Ticket ticket)
    {
        return null;
    }

    @Override
    public Ticket update(Ticket ticket, long id)
    {
        return null;
    }

    @Override
    public void deleteTicketById(long id)
    {

    }
}
