package com.digitalsolutionsbydon.devdesk.controllers;

import com.digitalsolutionsbydon.devdesk.models.ErrorDetail;
import com.digitalsolutionsbydon.devdesk.models.User;
import com.digitalsolutionsbydon.devdesk.services.UserService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController
{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @ApiOperation(value = "Returns all Users",
            response = User.class,
            responseContainer = "List")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/allusers",
            produces = {"application/json"})
    public ResponseEntity<?> listAllUsers(Pageable pageable, HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        List<User> list = userService.findAll(pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns all Users based on Paging and Sorting",
            response = User.class,
            responseContainer = "List")
    @ApiImplicitParams({@ApiImplicitParam(name = "page",
            dataType = "integer",
            paramType = "query",
            value = "Results page you want to retrieve(0..N)"), @ApiImplicitParam(name = "size",
            dataType = "integer",
            paramType = "query",
            value = "Number of records per page"), @ApiImplicitParam(name = "sort",
            allowMultiple = true,
            dataType = "string",
            paramType = "query",
            value = "Sorting criteria in the format: property(,asc,desc.  Default sort order is ascending.  Multiple sort criteria are supported.")})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/users",
            produces = {"application/json"})
    public ResponseEntity<?> listAllUsersPagedAndSorted(
            @PageableDefault(page = 0,
                    size = 5)
                    Pageable pageable, HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        List<User> list = userService.findAll(pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves the current logged in user",
            response = User.class)
    @ApiResponses({@ApiResponse(code = 200,
            message = "User found",
            response = User.class), @ApiResponse(code = 404,
            message = "User not found",
            response = ErrorDetail.class)})
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @GetMapping(value = "/user",
            produces = {"application/json"})
    public ResponseEntity<?> retrieveCurrentUser(HttpServletRequest request, Authentication authentication)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        User currentUser = userService.findUserByUsername(authentication.getName());
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @ApiOperation(value = "Deletes the User with the given Id",
            notes = "The Successfully Deleted ID will be returned",
            response = Long.class)
    @ApiResponses({@ApiResponse(code = 200,
            message = "User deleted",
            response = Long.class), @ApiResponse(code = 404,
            message = "User not found",
            response = ErrorDetail.class)})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping(value = "/user/{id}", produces = {"application/json"})
    public ResponseEntity<?> deleteUserById(HttpServletRequest request,
                                            @ApiParam(value = "User Id",
                                                    required = true,
                                                    example = "1")
                                            @PathVariable
                                                    long id)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        userService.deleteUserById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @ApiOperation(value = "Updates the Current User Information",
            response = User.class)

    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @PutMapping(value = "/user/{id}",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> updateUserById(HttpServletRequest request, @Valid
    @RequestBody
            User user,
                                            @ApiParam(value = "User Id",
                                                    required = true,
                                                    example = "1")
                                            @PathVariable
                                                    long id)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        User updatedUser = userService.update(user, id);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
