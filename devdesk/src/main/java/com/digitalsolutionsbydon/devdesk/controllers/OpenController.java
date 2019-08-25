package com.digitalsolutionsbydon.devdesk.controllers;

import com.digitalsolutionsbydon.devdesk.models.User;
import com.digitalsolutionsbydon.devdesk.models.UserRoles;
import com.digitalsolutionsbydon.devdesk.services.RoleService;
import com.digitalsolutionsbydon.devdesk.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;

@RestController
public class OpenController
{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "Adds a new User", notes="Anyone added will be of the default Student Role, need an admin account to upgrade roles", response= User.class)
    @ApiResponses({@ApiResponse(code=201, message="User successfully created", response=User.class),@ApiResponse(code=500, message="Something went wrong")})
    @PostMapping(value="/register", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addNewUser(HttpServletRequest request, @Valid @RequestBody User newUser)
    {
        logger.info(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
        ArrayList<UserRoles> newRoles = new ArrayList<>();
        newRoles.add(new UserRoles(newUser,roleService.findRoleByRoleName("student")));
        newUser.setUserRoles(newRoles);
        User user = userService.save(newUser);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

}
