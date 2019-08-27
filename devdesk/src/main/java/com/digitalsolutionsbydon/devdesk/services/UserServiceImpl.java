package com.digitalsolutionsbydon.devdesk.services;

import com.digitalsolutionsbydon.devdesk.exceptions.BadRequestException;
import com.digitalsolutionsbydon.devdesk.exceptions.NotAuthorizedException;
import com.digitalsolutionsbydon.devdesk.exceptions.ResourceNotFoundException;
import com.digitalsolutionsbydon.devdesk.models.User;
import com.digitalsolutionsbydon.devdesk.models.UserRoles;
import com.digitalsolutionsbydon.devdesk.repositories.RoleRepository;
import com.digitalsolutionsbydon.devdesk.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserService, UserDetailsService
{

    @Autowired
    UserRepository userRepo;

    @Autowired
    RoleRepository roleRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepo.findByUsername(username);
        if (user == null)
        {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthority());
    }

    @Override
    public List<User> findAll(Pageable pageable)
    {
        List<User> list = new ArrayList<>();
        userRepo.findAll(pageable)
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Override
    public User findUserByUserId(long id) throws ResourceNotFoundException
    {
        return userRepo.findById(id)
                       .orElseThrow(() -> new ResourceNotFoundException("The " + id + " is not in the system."));
    }

    @Override
    public User findUserByUsername(String username)
    {
        User currentUser = userRepo.findByUsername(username);

        if (currentUser != null)
        {
            return currentUser;
        } else
        {
            throw new ResourceNotFoundException("The " + username + " is not in the system");
        }
    }

    @Transactional
    @Modifying
    @Override
    public User save(User user)
    {
        if (userRepo.findByUsername(user.getUsername()) != null)
        {
            throw new BadRequestException("Username has already been taken");
        }
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPasswordNoEncrypt(user.getPassword());
        newUser.setFname(user.getFname());
        newUser.setLname(user.getLname());
        newUser.setUseremail(user.getUseremail());
        ArrayList<UserRoles> newRoles = new ArrayList<>();
        for (UserRoles ur : user.getUserRoles())
        {
            newRoles.add(new UserRoles(newUser, ur.getRole()));
        }
        newUser.setUserRoles(newRoles);
        return userRepo.save(newUser);
    }

    @Transactional
    @Modifying
    @Override
    public User update(User user, long id)
    {
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        User currentUser = userRepo.findByUsername(authentication.getName());
        if (currentUser != null)
        {
            if (id == currentUser.getUserid())
            {
                if (user.getUsername() != null)
                {
                    currentUser.setUsername(user.getUsername());
                }
                if (user.getPassword() != null)
                {
                    currentUser.setPasswordNoEncrypt(user.getPassword());
                }
                if (user.getFname() != null)
                {
                    currentUser.setFname(user.getFname());
                }
                if (user.getLname() != null)
                {
                    currentUser.setLname(user.getLname());
                }
                if (user.getUseremail() != null)
                {
                    currentUser.setUseremail(user.getUseremail());
                }
                if (user.getUserRoles()
                        .size() > 0)
                {
                    roleRepo.deleteUserRolesByUserId(currentUser.getUserid());
                    for (UserRoles ur : user.getUserRoles())
                    {
                        roleRepo.insertUserRoles(id, ur.getRole()
                                                       .getRoleid());
                    }
                }
                return userRepo.save(currentUser);
            } else
            {
                throw new NotAuthorizedException("You may only update your own account.");
            }
        } else
        {
            throw new ResourceNotFoundException("The " + id + " is not in the system.");
        }
    }

    @Transactional
    @Modifying
    @Override
    public User adminUpdate(User user, long id)
    {
        User currentUser = userRepo.findById(id)
                                   .orElseThrow(() -> new ResourceNotFoundException("The User with id:" + id + " cannot be found in our system"));


        if (user.getUsername() != null)
        {
            currentUser.setUsername(user.getUsername());
        }
        if (user.getPassword() != null)
        {
            currentUser.setPasswordNoEncrypt(user.getPassword());
        }
        if (user.getFname() != null)
        {
            currentUser.setFname(user.getFname());
        }
        if (user.getLname() != null)
        {
            currentUser.setLname(user.getLname());
        }
        if (user.getUseremail() != null)
        {
            currentUser.setUseremail(user.getUseremail());
        }
        if (user.getUserRoles()
                .size() > 0)
        {
            roleRepo.deleteUserRolesByUserId(currentUser.getUserid());
            for (UserRoles ur : user.getUserRoles())
            {
                roleRepo.insertUserRoles(id, ur.getRole()
                                               .getRoleid());
            }
        }
        return userRepo.save(currentUser);

    }

    @Override
    public void deleteUserById(long id)
    {
        if (userRepo.findById(id)
                    .isPresent())
        {
            userRepo.deleteById(id);
        } else
        {
            throw new ResourceNotFoundException("User " + id + " is not in the system.");
        }
    }

}
