package com.digitalsolutionsbydon.devdesk.services;

import com.digitalsolutionsbydon.devdesk.exceptions.BadRequestException;
import com.digitalsolutionsbydon.devdesk.exceptions.NotAuthorizedException;
import com.digitalsolutionsbydon.devdesk.exceptions.ResourceNotFoundException;
import com.digitalsolutionsbydon.devdesk.models.Ticket;
import com.digitalsolutionsbydon.devdesk.models.TicketMapper;
import com.digitalsolutionsbydon.devdesk.repositories.CategoryRepository;
import com.digitalsolutionsbydon.devdesk.repositories.StatusRepository;
import com.digitalsolutionsbydon.devdesk.repositories.TicketRepository;
import com.digitalsolutionsbydon.devdesk.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.jmx.export.annotation.ManagedOperation;
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

    @Autowired
    CategoryRepository categoryRepo;

    @Override
    public List<Ticket> findAll(Pageable pageable)
    {
        List<Ticket> list = new ArrayList<>();
        ticketRepo.findAll(pageable)
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
        Ticket newTicket = new Ticket();
        newTicket.setTitle(ticket.getTitle());
        newTicket.setDescription(ticket.getDescription());
        newTicket.setTried(ticket.getTried());
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        newTicket.setUser(userRepo.findByUsername(authentication.getName()));
        newTicket.setStatus(statusRepo.findByName("Pending"));
        if (ticket.getTicketMapper()
                  .size() == 0)
        {
            throw new BadRequestException("The Category Array of Object(s) is required.");
        }
        ArrayList<TicketMapper> newTicketMapper = new ArrayList<>();
        for (TicketMapper tm : ticket.getTicketMapper())
        {
            newTicketMapper.add(new TicketMapper(newTicket, tm.getCategory()));
        }
        newTicket.setTicketMapper(newTicketMapper);
        return ticketRepo.save(newTicket);
    }

    @Transactional
    @Modifying
    @Override
    public Ticket update(Ticket ticket, long id)
    {
        Ticket updateTicket = ticketRepo.findById(id)
                                        .orElseThrow(() -> new ResourceNotFoundException("The ticket with id:" + id + " is not in the system."));
        if (updateTicket.getUser() != null)
        {
            if (updateTicket.getUser()
                            .getUserid() != ticket.getUser()
                                                  .getUserid())
            {
                throw new NotAuthorizedException("You cannot change the user on a ticket");
            }
        }
        if (ticket.getTitle() != null)
        {
            updateTicket.setTitle(ticket.getTitle());
        }
        if (ticket.getDescription() != null)
        {
            updateTicket.setDescription(ticket.getDescription());
        }
        if (ticket.getTried() != null)
        {
            updateTicket.setTried(ticket.getTried());
        }
        if (ticket.getStatus() != null)
        {
            updateTicket.setStatus(ticket.getStatus());
        }
        if (ticket.getTicketMapper()
                  .size() > 0)
        {
            categoryRepo.deleteTicketMapperByTicketId(ticket.getTicketid());
            for (TicketMapper tm : ticket.getTicketMapper())
            {
                categoryRepo.insertIntoTicketMapper(ticket.getTicketid(), tm.getCategory()
                                                                            .getCategoryid());
            }
        }
        return ticketRepo.save(updateTicket);
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

    @Transactional
    @Modifying
    @Override
    public Ticket assignTicket(long id)
    {
        Ticket assignTicket = ticketRepo.findById(id)
                                        .orElseThrow(() -> new ResourceNotFoundException("Ticket with id:" + id + " is not in the system."));
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        assignTicket.setAssigneduser(userRepo.findByUsername(authentication.getName()));
        assignTicket.setStatus(statusRepo.findByName("Assigned"));
        return ticketRepo.save(assignTicket);
    }

    @Transactional
    @Modifying
    @Override
    public Ticket resolveTicket(long id)
    {
        Ticket resolveTicket = ticketRepo.findById(id)
                                         .orElseThrow(() -> new ResourceNotFoundException("Ticket with id:" + id + " is not in the system."));
        resolveTicket.setStatus(statusRepo.findByName("Resolved"));
        return ticketRepo.save(resolveTicket);
    }

    @Transactional
    @Modifying
    @Override
    public Ticket unAssignTicket(long id)
    {
        Ticket unAssignTicket = ticketRepo.findById(id)
                                         .orElseThrow(() -> new ResourceNotFoundException("Ticket with id:" + id + " is not in the system."));
        unAssignTicket.setAssigneduser(null);
        unAssignTicket.setStatus(statusRepo.findByName("Pending"));
        return ticketRepo.save(unAssignTicket);
    }

    @Override
    public Ticket archiveTicket(long id)
    {
        Ticket archiveTicket = ticketRepo.findById(id)
                                         .orElseThrow(() -> new ResourceNotFoundException("Ticket with id:" + id + " is not in the system."));
        archiveTicket.setStatus(statusRepo.findByName("Archived"));
        return ticketRepo.save(archiveTicket);
    }
}
