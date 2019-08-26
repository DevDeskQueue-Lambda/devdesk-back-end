package com.digitalsolutionsbydon.devdesk;

import com.digitalsolutionsbydon.devdesk.models.*;
import com.digitalsolutionsbydon.devdesk.repositories.TicketRepository;
import com.digitalsolutionsbydon.devdesk.services.CategoryService;
import com.digitalsolutionsbydon.devdesk.services.RoleService;
import com.digitalsolutionsbydon.devdesk.services.StatusService;
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

    @Autowired
    StatusService statusService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    TicketRepository ticketRepo;


    @Override
    public void run(String... args) throws Exception
    {
        Role r1 = new Role("admin");
        Role r2 = new Role("staff");
        Role r3 = new Role("student");

        r1=roleService.save(r1);
        r2=roleService.save(r2);
        r3=roleService.save(r3);

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

        u1 = userService.save(u1);
        u2 = userService.save(u2);
        u3 = userService.save(u3);


        Status s1 = new Status("Pending");
        Status s2 = new Status("Assigned");
        Status s3 = new Status("Resolved");
        Status s4 = new Status("Archived");
        s1=statusService.save(s1);
        s2=statusService.save(s2);
        s3=statusService.save(s3);
        s4=statusService.save(s4);

        Category c1 = new Category("React");
        Category c2 = new Category("Node.JS");
        Category c3 = new Category("Java");
        Category c4 = new Category("JavaScript");
        c1=categoryService.save(c1);
        c2=categoryService.save(c2);
        c3=categoryService.save(c3);
        c4=categoryService.save(c4);

        ArrayList<TicketMapper> tm1 = new ArrayList<>();
        tm1.add(new TicketMapper(new Ticket(), c1));
        System.out.println(u3.getUserid());
        Ticket t1 = new Ticket("Test", "Testing New Ticket Database", "Muliple Schemas", u3, s1, tm1);
        ticketRepo.save(t1);
        //        ArrayList<TicketMapper> tm1 = new ArrayList<>();
        //        tm1.add(new TicketMapper(new Ticket(), u3, s1, c1));
        //
        //        Ticket t1 = new Ticket("Test", "Testing New Ticket Database", "Multiple Schemas", tm1);
        //        ticketRepo.save(t1);

    }
}
