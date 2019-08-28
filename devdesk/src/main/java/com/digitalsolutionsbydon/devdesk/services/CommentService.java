package com.digitalsolutionsbydon.devdesk.services;

import com.digitalsolutionsbydon.devdesk.models.Comment;

public interface CommentService
{
    Comment saveByTicketId(Comment comment, long ticketid);

    Comment update(Comment comment, long id);
}
