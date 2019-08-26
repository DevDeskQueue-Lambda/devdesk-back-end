package com.digitalsolutionsbydon.devdesk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@ApiModel(value="Ticket Mapper", description = "This will keep track of all the Tickets")
@Entity
@Table(name = "ticketmapper")
public class TicketMapper extends Auditable implements Serializable
{
    @ApiModelProperty(name="ticketid", value="Foreign Key for Ticket Table", required = true, example = "1")
    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="ticketid")
    @JsonIgnoreProperties("ticketMapper")
    private Ticket ticket;

    @ApiModelProperty(name="categoryid", value="Foreign Key for Category Table", required = true, example="1")
    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="categoryid")
    @JsonIgnoreProperties("ticketMapper")
    private Category category;

    public TicketMapper()
    {
    }

    public TicketMapper(Ticket ticket, Category category)
    {
        this.ticket = ticket;
        this.category = category;
    }

    public Ticket getTicket()
    {
        return ticket;
    }

    public void setTicket(Ticket ticket)
    {
        this.ticket = ticket;
    }

    public Category getCategory()
    {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof TicketMapper))
        {
            return false;
        }
        TicketMapper that = (TicketMapper) o;
        return getTicket().equals(that.getTicket()) && getCategory().equals(that.getCategory());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getTicket(), getCategory());
    }
}
