package com.digitalsolutionsbydon.devdesk.services;

import com.digitalsolutionsbydon.devdesk.models.Role;

import java.util.List;

public interface RoleService
{
    List<Role> findAll();

    Role findRoleByRoleId(long id);

    void deleteRoleByRoleId(long id);

    Role save(Role role);
}
