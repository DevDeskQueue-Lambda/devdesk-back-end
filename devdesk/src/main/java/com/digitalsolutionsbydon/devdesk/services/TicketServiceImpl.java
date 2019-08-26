package com.digitalsolutionsbydon.devdesk.services;

import com.digitalsolutionsbydon.devdesk.exceptions.BadRequestException;
import com.digitalsolutionsbydon.devdesk.exceptions.NotAuthorizedException;
import com.digitalsolutionsbydon.devdesk.exceptions.ResourceNotFoundException;
import com.digitalsolutionsbydon.devdesk.models.Category;
import com.digitalsolutionsbydon.devdesk.models.Status;
import com.digitalsolutionsbydon.devdesk.models.Ticket;
import com.digitalsolutionsbydon.devdesk.models.User;
import com.digitalsolutionsbydon.devdesk.repositories.StatusRepository;
import com.digitalsolutionsbydon.devdesk.repositories.TicketRepository;
import com.digitalsolutionsbydon.devdesk.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "ticketService")
public class TicketServiceImpl implements TicketService
{
    @Autowired
    TicketRepository ticketRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    StatusRepository statusRepo;

    @Override
    public List<Ticket> findAll()
    {
        List<Ticket> list = new ArrayList<>();
        ticketRepo.findAll()
                  .iterator()
                  .forEachRemaining(list::add);
        return list;
    }

    @Override
    public Ticket findTicketById(long id) throws ResourceNotFoundException
    {
        return ticketRepo.findById(id)
                         .orElseThrow(() -> new ResourceNotFoundException("Ticket with id:" + id + " is not in the system."));
    }
    @Transactional
    @Modifying
    @Override
    public Ticket save(Ticket ticket)
    {
        return null;
    }
@Transactional
@Modifying
    @Override
    public Ticket update(Ticket ticket, long id)
    {
        return null;
    }

    @Override
    public void deleteTicketById(long id) throws ResourceNotFoundException
    {
        if (ticketRepo.findById(id)
                      .isPresent())
        {
            ticketRepo.deleteById(id);
        } else
        {
            throw new ResourceNotFoundException("Ticket with id:" + id + " is not in the system.");
        }
    }
}
