package com.digitalsolutionsbydon.devdesk.services;

import com.digitalsolutionsbydon.devdesk.models.Status;
import com.digitalsolutionsbydon.devdesk.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "statusService")
public class StatusServiceImpl implements StatusService
{
    @Autowired
    StatusRepository statusRepo;
    @Override
    public List<Status> findAll()
    {
        List<Status> list = new ArrayList<>();
        statusRepo.findAll().iterator().forEachRemaining(list::add);
        return list;
    }
    @Transactional
    @Modifying
    @Override
    public Status save(Status status)
    {
        Status newStatus = new Status();
        newStatus.setName(status.getName());
        return statusRepo.save(newStatus);
    }
}
