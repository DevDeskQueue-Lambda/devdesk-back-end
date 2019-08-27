package com.digitalsolutionsbydon.devdesk.controllers;

import com.digitalsolutionsbydon.devdesk.models.Status;
import com.digitalsolutionsbydon.devdesk.services.StatusService;
import com.digitalsolutionsbydon.devdesk.view.StatusView;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController
{
    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);
    @Autowired
    StatusService statusService;

    @ApiOperation(value="Returns All Possible Statuses", response=StatusView.class, responseContainer = "List")
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @GetMapping(value="/statuses", produces = {"application/json"})
    public ResponseEntity<?> listAllStatuses(HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        List<StatusView> list = statusService.findAllStatus();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
