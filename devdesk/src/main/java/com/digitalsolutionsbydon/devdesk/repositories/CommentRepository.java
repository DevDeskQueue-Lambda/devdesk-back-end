package com.digitalsolutionsbydon.devdesk.repositories;

import com.digitalsolutionsbydon.devdesk.models.Comment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CommentRepository extends CrudRepository<Comment, Long>
{
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM TicketComments WHERE ticketid = :ticketid")
    void deleteTicketCommentsByTicketId(long ticketid);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO TicketComments(ticketid, commentid) values(:ticketid, :commentid)", nativeQuery = true)
    void insertIntoTicketComments(long ticketid, long commentid);

    Comment findCommentByCommentid(long id);
}
