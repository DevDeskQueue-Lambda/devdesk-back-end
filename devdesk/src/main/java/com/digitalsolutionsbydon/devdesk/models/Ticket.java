package com.digitalsolutionsbydon.devdesk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value="Ticket", description = "The Ticket Entity")
@Entity
@Table(name = "tickets")
public class Ticket extends Auditable implements Serializable
{
    @ApiModelProperty(name="ticketid", value="Primary Key for Tickets", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ticketid;

    @ApiModelProperty(name="title", value="Ticket Title", required = true, example = "HELP!")
    @Column(nullable = false)
    private String title;

    @ApiModelProperty(name="description", value="Description of Problem", required = true, example="My JAVA won't Compile")
    @Column(nullable=false)
    private String description;

    @ApiModelProperty(name="tried", value="Methods Tried", required = true, example="Restarted Computer")
    @Column(nullable=false)
    private String tried;

    @ManyToOne
    @JoinColumn(name="userid")
    @JsonIgnoreProperties("ticket")
    private User user;

    @ManyToOne
    @JoinColumn(name="assignedid", referencedColumnName = "userid")
    @JsonIgnoreProperties("ticket")
    private User assigneduser;

    @ManyToOne
    @JoinColumn(name="statusid")
    @JsonIgnoreProperties("ticket")
    private Status status;

    @OneToMany(mappedBy="ticket", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("ticket")
    private List<TicketMapper> ticketMapper = new ArrayList<>();

    public Ticket()
    {
    }

    public Ticket(String title, String description, String tried, User user, Status status, List<TicketMapper> ticketMapper)
    {
        this.title = title;
        this.description = description;
        this.tried = tried;
        this.user = user;
        this.status = status;
        for (TicketMapper tm: ticketMapper)
        {
            tm.setTicket(this);
        }
        this.ticketMapper = ticketMapper;
    }

    public long getTicketid()
    {
        return ticketid;
    }

    public void setTicketid(long ticketid)
    {
        this.ticketid = ticketid;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getTried()
    {
        return tried;
    }

    public void setTried(String tried)
    {
        this.tried = tried;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public User getAssigneduser()
    {
        return assigneduser;
    }

    public void setAssigneduser(User assigneduser)
    {
        this.assigneduser = assigneduser;
    }

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public List<TicketMapper> getTicketMapper()
    {
        return ticketMapper;
    }

    public void setTicketMapper(List<TicketMapper> ticketMapper)
    {
        this.ticketMapper = ticketMapper;
    }
}
