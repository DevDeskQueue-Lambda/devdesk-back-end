package com.digitalsolutionsbydon.devdesk.services;

import com.digitalsolutionsbydon.devdesk.models.Role;
import com.digitalsolutionsbydon.devdesk.view.RoleIdsAndNames;

import java.util.List;

public interface RoleService
{
    List<Role> findAll();

    List<RoleIdsAndNames> findRoleIdsAndNames();

    Role findRoleByRoleId(long id);

    void deleteRoleByRoleId(long id);

    Role save(Role role);

    Role findRoleByRoleName(String name);
}
