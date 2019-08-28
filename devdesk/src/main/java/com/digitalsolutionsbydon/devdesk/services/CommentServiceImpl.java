package com.digitalsolutionsbydon.devdesk.services;

import com.digitalsolutionsbydon.devdesk.exceptions.ResourceNotFoundException;
import com.digitalsolutionsbydon.devdesk.models.Comment;
import com.digitalsolutionsbydon.devdesk.models.Ticket;
import com.digitalsolutionsbydon.devdesk.models.TicketComments;
import com.digitalsolutionsbydon.devdesk.models.User;
import com.digitalsolutionsbydon.devdesk.repositories.CommentRepository;
import com.digitalsolutionsbydon.devdesk.repositories.TicketRepository;
import com.digitalsolutionsbydon.devdesk.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service(value="commentService")
public class CommentServiceImpl implements CommentService
{
    @Autowired
    UserRepository userRepo;

    @Autowired
    TicketRepository ticketRepo;

    @Autowired
    CommentRepository commentRepo;

    @Override
    public Comment saveByTicketId(Comment comment, long userid, long ticketid)
    {
        Ticket currentTicket = ticketRepo.findById(ticketid).orElseThrow(()-> new ResourceNotFoundException("Ticket with ID:" + ticketid + " was not found in the system"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Comment newComment = new Comment();
        newComment.setComment(comment.getComment());
        if (authentication.getName() == null)
        {
            User currentUser = userRepo.findByUserid(userid);
            newComment.setUser(currentUser);
        } else
        {
            User currentUser = userRepo.findByUsername(authentication.getName());
            newComment.setUser(currentUser);
        }
//        commentRepo.deleteTicketCommentsByTicketId(ticketid);
//        for (TicketComments tc : currentTicket.getTicketComments())
//        {
//            commentRepo.insertIntoTicketComments(currentTicket.getTicketid(), tc.getComment().getCommentid());
//        }
        newComment = commentRepo.save(newComment);
        commentRepo.insertIntoTicketComments(currentTicket.getTicketid(), newComment.getCommentid());
        return newComment;
    }

    @Override
    public Comment update(Comment comment, long id)
    {
        return null;
    }


}
