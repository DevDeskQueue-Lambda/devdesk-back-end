package com.digitalsolutionsbydon.devdesk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value="Comments", description = "Comments for Admin / Staff")
@Entity
@Table(name="comments")
public class Comment extends Auditable
{
    @ApiModelProperty(name="commentid", value="Primary Key for Comment Table", required = true,example="1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long commentid;

    @ApiModelProperty(name="comment", value="Comments left by Admin and Staff", required = true, example = "I am a comment")
    @Column(nullable = false)
    @NotNull
    private String comment;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("comment")
    private List<TicketComments> ticketComments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userid")
    @JsonIgnoreProperties({"comment", "hibernateLazyInitializer"})
    private User user;

    public Comment()
    {
    }

    public Comment(@NotNull String comment, User user)
    {
        this.comment = comment;
        this.user = user;
    }

    public long getCommentid()
    {
        return commentid;
    }

    public void setCommentid(long commentid)
    {
        this.commentid = commentid;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public List<TicketComments> getTicketComments()
    {
        return ticketComments;
    }

    public void setTicketComments(List<TicketComments> ticketComments)
    {
        this.ticketComments = ticketComments;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
