package com.digitalsolutionsbydon.devdesk.services;


import com.digitalsolutionsbydon.devdesk.exceptions.ResourceNotFoundException;
import com.digitalsolutionsbydon.devdesk.models.Role;
import com.digitalsolutionsbydon.devdesk.repositories.RoleRepository;
import com.digitalsolutionsbydon.devdesk.view.RoleIdsAndNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "roleService")
public class RoleServiceImpl implements RoleService
{
    @Autowired
    RoleRepository roleRepo;

    @Override
    public List<RoleIdsAndNames> findRoleIdsAndNames()
    {
        List<RoleIdsAndNames> list = new ArrayList<>();
        roleRepo.getRoleIdsAndNames().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Role findRoleByRoleName(String name)
    {
        Role rr = roleRepo.findByName(name);

        if (rr != null)
        {
            return rr;
        } else
        {
            throw new ResourceNotFoundException(name);
        }
    }

    @Override
    public List<Role> findAll()
    {
        List<Role> list = new ArrayList<>();
        roleRepo.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Override
    public Role findRoleByRoleId(long id) throws ResourceNotFoundException
    {
        return roleRepo.findById(id)
                       .orElseThrow(() -> new ResourceNotFoundException("The role with id:" + id + " was not found in the system."));
    }

    @Transactional
    @Modifying
    @Override
    public void deleteRoleByRoleId(long id)
    {
        if (roleRepo.findById(id)
                    .isPresent())
        {
            roleRepo.deleteById(id);
        } else
        {
            throw new ResourceNotFoundException("The role with id:" + id + " was not found in the system.");
        }
    }

    @Transactional
    @Override
    public Role save(Role role)
    {
        return roleRepo.save(role);
    }
}
