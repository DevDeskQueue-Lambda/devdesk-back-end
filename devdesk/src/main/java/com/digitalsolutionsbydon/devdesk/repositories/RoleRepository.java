package com.digitalsolutionsbydon.devdesk.repositories;

import com.digitalsolutionsbydon.devdesk.models.Role;
import com.digitalsolutionsbydon.devdesk.view.RoleIdsAndNames;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, Long>
{
    @Transactional
    @Modifying
    @Query(value = "DELETE from UserRoles where userid = :userid")
    void deleteUserRolesByUserId(long userid);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO UserRoles(userid, roleid) values (:userid, :roleid)",
            nativeQuery = true)
    void insertUserRoles(long userid, long roleid);

    @Query(value = "SELECT ROLEID, NAME FROM roles",
            nativeQuery = true)
    List<RoleIdsAndNames> getRoleIdsAndNames();

    Role findByName(String name);
}
