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
public class Status extends Auditable
{
    @ApiModelProperty(name="statusid", value="Primary Key for Status", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long statusid;

    @ApiModelProperty(name="Status Name", value="The Statuses", required = true, example = "Pending")
    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="status", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("status")
    private List<Ticket> ticket;

    public Status()
    {
    }

    public Status(String name)
    {
        this.name = name;
    }

    public long getStatusid()
    {
        return statusid;
    }

    public void setStatusid(long statusid)
    {
        this.statusid = statusid;
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
