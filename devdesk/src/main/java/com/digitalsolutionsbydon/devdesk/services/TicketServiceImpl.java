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

    @Override
    public Ticket save(Ticket ticket)
    {
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        Ticket newTicket = new Ticket();
        if (ticket.getTitle() == null)
        {
            throw new BadRequestException("The field 'title' is required.");
        }
        newTicket.setTitle(ticket.getTitle());
        if (ticket.getDescription() == null)
        {
            throw new BadRequestException("The field 'description' is required.");
        }
        newTicket.setDescription(ticket.getDescription());
        if (ticket.getTried() == null)
        {
            throw new BadRequestException("The field 'tried' is required.");
        }
        newTicket.setTried(ticket.getTried());
        User currentUser = userRepo.findByUsername(authentication.getName());
        if (currentUser == null)
        {
            throw new NotAuthorizedException("You must be logged in to do that!");
        }
        newTicket.setUser(currentUser);
        Status pendingStatus = statusRepo.findByName("Pending");
        newTicket.setStatus(pendingStatus);
        List<Category> newCategories = new ArrayList<>();
        newCategories.addAll(ticket.getCategory());
        newTicket.setCategory(newCategories);

        return ticketRepo.save(newTicket);
    }

    @Override
    public Ticket update(Ticket ticket, long id) throws ResourceNotFoundException
    {
        Ticket updateTicket = ticketRepo.findById(id)
                                        .orElseThrow(() -> new ResourceNotFoundException("Ticket with id:" + id + " is not in the system."));
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
        if (ticket.getAssignedid() != updateTicket.getAssignedid())
        {
            updateTicket.setAssignedid(ticket.getAssignedid());
        }
        if (ticket.getStatus() != null)
        {
            updateTicket.setStatus(ticket.getStatus());
        }
        if (ticket.getCategory()
                  .size() > 0)
        {
            List<Category> updatedCategories = new ArrayList<>();
            for (Category c : ticket.getCategory())
            {
                updatedCategories.add(c);
            }
            updateTicket.setCategory(updatedCategories);
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
            throw new ResourceNotFoundException("Ticket with id:" + id + " is not in the system.")
        }
    }
}
