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
    private long id;

    @ApiModelProperty(name="title", value="Ticket Title", required = true, example = "HELP!")
    @Column(nullable = false)
    private String title;

    @ApiModelProperty(name="description", value="Description of Problem", required = true, example="My JAVA won't Compile")
    @Column(nullable=false)
    private String description;

    @ApiModelProperty(name="tried", value="Methods Tried", required = true, example="Restarted Computer")
    @Column(nullable=false)
    private String tried;

    @ApiModelProperty(name="Assigned ID", value="User ID of the assigned user", required = false, example="1")
    private long assignedid;

    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties("ticket")
    private User user;

    @ApiModelProperty(name="Status", value="Status of the Ticket", required = true)
    @ManyToOne
    @JoinColumn(name="statusid")
    @JsonIgnoreProperties("ticket")
    private Status status;

    @ApiModelProperty(name="Category", value="Category for Issue", required = true)
    @ManyToOne
    @JoinColumn(name="categoryid")
    @JsonIgnoreProperties("ticket")
    private Category category;

    public Ticket()
    {
    }

    public Ticket(String title, String description, String tried, User user, Status status, Category category)
    {
        this.title = title;
        this.description = description;
        this.tried = tried;
        this.user = user;
        this.status = status;
        this.category = category;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
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

    public long getAssignedid()
    {
        return assignedid;
    }

    public void setAssignedid(long assignedid)
    {
        this.assignedid = assignedid;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public Category getCategory()
    {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }
}
