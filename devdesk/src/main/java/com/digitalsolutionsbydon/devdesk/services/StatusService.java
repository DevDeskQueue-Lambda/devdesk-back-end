package com.digitalsolutionsbydon.devdesk.services;

import com.digitalsolutionsbydon.devdesk.models.Status;

import java.util.List;

public interface StatusService
{
    List<Status> findAll();

    Status save(Status status);
}
