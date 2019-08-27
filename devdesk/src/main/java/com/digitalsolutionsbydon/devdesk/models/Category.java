package com.digitalsolutionsbydon.devdesk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "Categories",
        description = "Where the Categories are Stored")
@Entity
@Table(name = "categories")
public class Category extends Auditable
{
    @ApiModelProperty(name = "categoryid",
            value = "The Primary Key for the Categories",
            required = true,
            example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long categoryid;

    @ApiModelProperty(name = "name",
            value = "Category",
            required = true,
            example = "React")
    @Column(nullable = false,
            unique = true)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("category")
    private List<TicketMapper> ticketMapper = new ArrayList<>();

    public Category()
    {
    }

    public Category(String name)
    {
        this.name = name;
    }

    public long getCategoryid()
    {
        return categoryid;
    }

    public void setCategoryid(long categoryid)
    {
        this.categoryid = categoryid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<TicketMapper> getTicketMapper()
    {
        return ticketMapper;
    }

    public void setTicketMappers(List<TicketMapper> ticketMapper)
    {
        this.ticketMapper = ticketMapper;
    }
}
