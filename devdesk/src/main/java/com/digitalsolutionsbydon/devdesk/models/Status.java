package com.digitalsolutionsbydon.devdesk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "Status", description = "Available Status for Tickets")
@Entity
@Table(name = "status")
public class Status extends Auditable implements Serializable
{
    @ApiModelProperty(name="statusid", value="Primary Key for Status", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ApiModelProperty(name="Status Name", value="The Statuses", required = true, example = "Pending")
    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("status")
    private List<Ticket> ticket = new ArrayList<>();

    public Status()
    {
    }

    public Status(String name)
    {
        this.name = name;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Ticket> getTicket()
    {
        return ticket;
    }

    public void setTicket(List<Ticket> ticket)
    {
        this.ticket = ticket;
    }
}
