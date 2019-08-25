package com.digitalsolutionsbydon.devdesk;

import com.digitalsolutionsbydon.devdesk.models.Role;
import com.digitalsolutionsbydon.devdesk.models.User;
import com.digitalsolutionsbydon.devdesk.models.UserRoles;
import com.digitalsolutionsbydon.devdesk.services.RoleService;
import com.digitalsolutionsbydon.devdesk.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @Override
    public void run(String... args) throws Exception
    {
        Role r1 = new Role("admin");
        Role r2 = new Role("staff");
        Role r3 = new Role("student");

        roleService.save(r1);
        roleService.save(r2);
        roleService.save(r3);

        ArrayList<UserRoles> admin = new ArrayList<>();
        admin.add(new UserRoles(new User(), r1));
        admin.add(new UserRoles(new User(), r2));
        admin.add(new UserRoles(new User(), r3));
        ArrayList<UserRoles> staff = new ArrayList<>();
        staff.add(new UserRoles(new User(), r2));
        staff.add(new UserRoles(new User(), r3));
        ArrayList<UserRoles> student = new ArrayList<>();
        student.add(new UserRoles(new User(), r3));

        User u1 = new User("admin", "password", "Ryan", "Hamblin", "ryan@gmail.com", admin);
        User u2 = new User("staff", "password", "Dustin", "Myers", "dustin@reactjs.org", staff);
        User u3 = new User("student", "password", "Nick", "Durbin", "nick@nickisawesome.org", student);

        userService.save(u1);
        userService.save(u2);
        userService.save(u3);
    }
}
