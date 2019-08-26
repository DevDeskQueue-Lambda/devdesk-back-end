package com.digitalsolutionsbydon.devdesk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "Users",
        description = "The Users Entity")
@Entity
@Table(name = "users")
public class User extends Auditable
{
    @ApiModelProperty(name = "userid",
            value = "Primary Key for Users Field",
            required = true,
            example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userid;

    @ApiModelProperty(name = "username",
            value = "User's username",
            required = true,
            example = "admin")
    @Column(unique = true,
            nullable = false)
    @Size(min = 5,
            max = 20)
    private String username;

    @ApiModelProperty(name = "password",
            value = "User's password",
            required = true,
            example = "password")
    @Column(unique = true,
            nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ApiModelProperty(name = "fname",
            value = "User's First Name",
            required = true,
            example = "Ryan")
    @Column(unique = true,
            nullable = false)
    @Size(min = 2,
            max = 20)
    private String fname;

    @ApiModelProperty(name = "lname",
            value = "User's Last Name",
            required = true,
            example = "Hamblin")
    @Column(unique = true,
            nullable = false)
    @Size(min = 2,
            max = 20)
    private String lname;

    @ApiModelProperty(name = "useremail",
            value = "User's Email",
            required = true,
            example = "rhamblin@gmail.com")
    @Column(unique = true,
            nullable = false)
    @Email
    private String useremail;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    private List<UserRoles> userRoles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade=CascadeType.ALL)
    @JsonIgnoreProperties("user")
    private List<Ticket> tickets = new ArrayList<>();

    public User()
    {
    }

    public User(@Size(min = 5,
            max = 20) String username, @Size(min = 5,
            max = 20) String password, @Size(min = 2,
            max = 20) String fname, @Size(min = 2,
            max = 20) String lname, @Email String useremail, List<UserRoles> userRoles)
    {
        setUsername(username);
        setPassword(password);
        this.fname = fname;
        this.lname = lname;
        this.useremail = useremail;
        for (UserRoles ur : userRoles)
        {
            ur.setUser(this);
        }
        this.userRoles = userRoles;
    }

    public long getUserid()
    {
        return userid;
    }

    public void setUserid(long userid)
    {
        this.userid = userid;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public void setPasswordNoEncrypt(String password)
    {
        this.password = password;
    }

    public String getFname()
    {
        return fname;
    }

    public void setFname(String fname)
    {
        this.fname = fname;
    }

    public String getLname()
    {
        return lname;
    }

    public void setLname(String lname)
    {
        this.lname = lname;
    }

    public String getUseremail()
    {
        return useremail;
    }

    public void setUseremail(String useremail)
    {
        this.useremail = useremail;
    }

    public List<UserRoles> getUserRoles()
    {
        return userRoles;
    }

    public void setUserRoles(List<UserRoles> userRoles)
    {
        this.userRoles = userRoles;
    }

    public List<Ticket> getTickets()
    {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets)
    {
        this.tickets = tickets;
    }

    public List<SimpleGrantedAuthority> getAuthority()
    {
        List<SimpleGrantedAuthority> rtnList = new ArrayList<>();

        for (UserRoles ur : this.userRoles)
        {
            String myRole = "ROLE_" + ur.getRole()
                                        .getName()
                                        .toUpperCase();
            rtnList.add(new SimpleGrantedAuthority(myRole));
        }
        return rtnList;
    }
}
