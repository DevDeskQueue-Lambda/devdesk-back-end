package com.digitalsolutionsbydon.devdesk.controllers;

import com.digitalsolutionsbydon.devdesk.models.Comment;
import com.digitalsolutionsbydon.devdesk.models.Ticket;
import com.digitalsolutionsbydon.devdesk.services.CommentService;
import com.digitalsolutionsbydon.devdesk.services.TicketService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/comments")
public class CommentController
{
    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);
    @Autowired
    CommentService commentService;

    @Autowired
    TicketService ticketService;

    @ApiOperation(value = "Add a new comment based on ticket id",
            response = Ticket.class)
    @PreAuthorize("hasAuthority('ROLE_STAFF')")
    @PostMapping(value = "/comment/{id}",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> addNewComment(@Valid
                                           @RequestBody
                                                   Comment comment,
                                           @ApiParam(name = "ticketid",
                                                   value = "The ticket id that the comment will be applied to",
                                                   required = true,
                                                   example = "1")
                                           @PathVariable
                                                   long id, HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        commentService.saveByTicketId(comment, id);
        Ticket updatedTicket = ticketService.findTicketById(id);
        return new ResponseEntity<>(updatedTicket, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update Comment based on Comment Id",
            response = Ticket.class)
    @PreAuthorize("hasAuthority('ROLE_STAFF')")
    @PutMapping(value = "/comment/{id}/{ticketid}",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> updateComment(@Valid
                                           @RequestBody
                                                   Comment comment,
                                           @ApiParam(name = "commentid",
                                                   value = "The Id of the Comment that needs to be updated",
                                                   required = true,
                                                   example = "1")
                                           @PathVariable
                                                   long id,
                                           @ApiParam(name = "ticketid",
                                                   value = "Ticket ID that the comment is attached to",
                                                   required = true,
                                                   example = "1")
                                           @PathVariable
                                                   long ticketid, HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        commentService.update(comment, id);
        Ticket updatedTicket = ticketService.findTicketById(ticketid);
        return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
    }
}
