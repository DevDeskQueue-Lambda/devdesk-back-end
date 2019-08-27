package com.digitalsolutionsbydon.devdesk.controllers;

import com.digitalsolutionsbydon.devdesk.models.Ticket;
import com.digitalsolutionsbydon.devdesk.services.StatusService;
import com.digitalsolutionsbydon.devdesk.services.TicketService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController
{
    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);
    @Autowired
    StatusService statusService;

    @Autowired
    TicketService ticketService;

    //    @ApiOperation(value = "Returns All Possible Statuses",
    //            response = StatusView.class,
    //            responseContainer = "List")
    //    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    //    @GetMapping(value = "/statuses",
    //            produces = {"application/json"})
    //    public ResponseEntity<?> listAllStatuses(HttpServletRequest request)
    //    {
    //        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
    //        List<StatusView> list = statusService.findAllStatus();
    //        return new ResponseEntity<>(list, HttpStatus.OK);
    //    }

    @ApiOperation(value = "Returns all tickets in the system",
            response = Ticket.class,
            responseContainer = "List")
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @GetMapping(value = "/alltickets",
            produces = {"application/json"})
    public ResponseEntity<?> listAllTickets(Pageable pageable, HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        List<Ticket> list = ticketService.findAll(pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns all tickets in the system based on Paging and Sorting",
            response = Ticket.class,
            responseContainer = "List")
    @ApiImplicitParams({@ApiImplicitParam(name = "page",
            dataType = "integer",
            paramType = "query",
            value = "Results page you want to retrieve(0..N)"), @ApiImplicitParam(name = "size",
            dataType = "integer",
            paramType = "query",
            value = "Number of records per page"), @ApiImplicitParam(name = "sort",
            allowMultiple = true,
            dataType = "string",
            paramType = "query",
            value = "Sorting criteria in the format: property(,asc,desc.  Default sort order is ascending.  Multiple sort criteria are supported.")})
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @GetMapping(value = "/tickets",
            produces = {"application/json"})
    public ResponseEntity<?> listAllTicketsPageAndSorted(
            @PageableDefault(page = 0,
                    size = 5)
                    Pageable pageable, HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        List<Ticket> list = ticketService.findAll(pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrives a ticket by provided Id",
            response = Ticket.class)
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @GetMapping(value = "/ticket/{id}",
            produces = {"application/json"})
    public ResponseEntity<?> retrieveTicketById(
            @ApiParam(name = "id",
                    value = "Ticket Id",
                    required = true,
                    example = "1")
            @PathVariable
                    long id, HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        return new ResponseEntity<>(ticketService.findTicketById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Add a New Ticket",
            response = Ticket.class)
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @PostMapping(value = "/ticket",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> addNewTicket(@Valid
                                          @RequestBody
                                                  Ticket ticket, HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        Ticket newTicket = ticketService.save(ticket);
        return new ResponseEntity<>(newTicket, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Assign a Ticket",
            response = Ticket.class)
    @PreAuthorize("hasAuthority('ROLE_STAFF')")
    @PutMapping(value = "/ticket/assign/{id}",
            produces = {"application/json"})
    public ResponseEntity<?> assignTicket(
            @ApiParam(name = "id",
                    value = "Ticket Id",
                    required = true,
                    example = "1")
            @PathVariable
                    long id, HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        Ticket assignTicket = ticketService.assignTicket(id);
        return new ResponseEntity<>(assignTicket, HttpStatus.OK);
    }

    @ApiOperation(value = "Resolves a Ticket",
            response = Ticket.class)
    @PreAuthorize("hasAuthority('ROLE_STAFF')")
    @PutMapping(value = "/ticket/resolve/{id}",
            produces = {"application/json"})
    public ResponseEntity<?> resolveTicket(
            @ApiParam(name = "id",
                    value = "Ticket Id",
                    required = true,
                    example = "1")
            @PathVariable
                    long id, HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        Ticket resolveTicket = ticketService.resolveTicket(id);
        return new ResponseEntity<>(resolveTicket, HttpStatus.OK);
    }

    @ApiOperation(value = "Removes Assigned User From A Ticket",
            response = Ticket.class)
    @PreAuthorize("hasAuthority('ROLE_STAFF')")
    @PutMapping(value = "/ticket/unassign/{id}",
            produces = {"application/json"})
    public ResponseEntity<?> unAssignTicket(
            @ApiParam(name = "id",
                    value = "Ticket Id",
                    required = true,
                    example = "1")
            @PathVariable
                    long id, HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        Ticket unAssignTicket = ticketService.unAssignTicket(id);
        return new ResponseEntity<>(unAssignTicket, HttpStatus.OK);
    }

    @ApiOperation(value = "Archives a Ticket",
            response = Ticket.class)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping(value = "/ticket/archive/{id}",
            produces = {"application/json"})
    public ResponseEntity<?> archiveTicket(
            @ApiParam(name = "id",
                    value = "Ticket Id",
                    required = true,
                    example = "1")
            @PathVariable
                    long id, HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        Ticket archiveTicket = ticketService.archiveTicket(id);
        return new ResponseEntity<>(archiveTicket, HttpStatus.OK);
    }

    @ApiOperation(value = "Update a Ticket",
            response = Ticket.class)
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @PutMapping(value = "/ticket/{id}",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> updateTicket(@Valid
                                          @RequestBody
                                                  Ticket ticket,
                                          @ApiParam(name = "id",
                                                  value = "Ticket Id",
                                                  required = true,
                                                  example = "1")
                                          @PathVariable
                                                  long id, HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        Ticket updateTicket = ticketService.update(ticket, id);
        return new ResponseEntity<>(updateTicket, HttpStatus.OK);
    }

    @ApiOperation(value="Delete a Ticket", response = Long.class)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping(value = "/ticket/{id}")
    public ResponseEntity<?> deleteTicket( @ApiParam(name = "id",
            value = "Ticket Id",
            required = true,
            example = "1") @PathVariable long id, HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        ticketService.deleteTicketById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
