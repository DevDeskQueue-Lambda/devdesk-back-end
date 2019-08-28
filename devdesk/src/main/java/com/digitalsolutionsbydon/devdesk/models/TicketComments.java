package com.digitalsolutionsbydon.devdesk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="ticketcomments")
public class TicketComments extends Auditable implements Serializable
{
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ticketid")
    @JsonIgnoreProperties({"ticketComments", "hibernateLazyInitializer"})
    private Ticket ticket;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="commentid")
    @JsonIgnoreProperties({"ticketComments", "hibernateLazyInitializer"})
    private Comment comment;

    public TicketComments()
    {
    }

    public TicketComments(Ticket ticket, Comment comment)
    {
        this.ticket = ticket;
        this.comment = comment;
    }

    public Ticket getTicket()
    {
        return ticket;
    }

    public void setTicket(Ticket ticket)
    {
        this.ticket = ticket;
    }

    public Comment getComment()
    {
        return comment;
    }

    public void setComment(Comment comment)
    {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof TicketComments))
        {
            return false;
        }
        TicketComments that = (TicketComments) o;
        return getTicket().equals(that.getTicket()) && getComment().equals(that.getComment());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getTicket(), getComment());
    }
}
