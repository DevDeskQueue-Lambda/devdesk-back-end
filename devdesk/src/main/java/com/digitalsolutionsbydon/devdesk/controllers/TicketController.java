package com.digitalsolutionsbydon.devdesk.controllers;

import com.digitalsolutionsbydon.devdesk.models.Status;
import com.digitalsolutionsbydon.devdesk.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController
{
    @Autowired
    StatusService statusService;

    @GetMapping(value="/statuses", produces = {"application/json"})
    public ResponseEntity<?> listAllStatuses(HttpServletRequest request)
    {
        List<Status> list = statusService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
