package com.digitalsolutionsbydon.devdesk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ApiModel(value="UserRoles", description = "Mapping for Users and Roles")
@Entity
@Table(name="userroles")
public class UserRoles extends Auditable
{
    @ApiModelProperty(name="userid", value="ForeignKey for User's Table", required = true, example="1")
    @Id
    @ManyToOne
    @JoinColumn(name="userid")
    @JsonIgnoreProperties("userRoles")
    private User user;

    @ApiModelProperty(name="roleid", value="ForeignKey for Role's Table", required = true, example="1")
    @Id
    @ManyToOne
    @JoinColumn(name="roleid")
    @JsonIgnoreProperties("userRoles")
    private Role role;

    public UserRoles()
    {
    }

    public UserRoles(User user, Role role)
    {
        this.user = user;
        this.role = role;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Role getRole()
    {
        return role;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof UserRoles))
        {
            return false;
        }
        UserRoles userRoles = (UserRoles) o;
        return getUser().equals(userRoles.getUser()) && getRole().equals(userRoles.getRole());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getUser(), getRole());
    }

}
