package com.digitalsolutionsbydon.devdesk.services;

import com.digitalsolutionsbydon.devdesk.models.Comment;

import java.util.List;

public interface CommentService
{
    Comment saveByTicketId(Comment comment, long userid, long ticketid);

    Comment update(Comment comment, long id);
}
