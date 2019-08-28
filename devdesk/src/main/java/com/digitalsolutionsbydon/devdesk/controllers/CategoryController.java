package com.digitalsolutionsbydon.devdesk.controllers;

import com.digitalsolutionsbydon.devdesk.models.Category;
import com.digitalsolutionsbydon.devdesk.services.CategoryService;
import com.digitalsolutionsbydon.devdesk.view.CategoryView;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController
{
    @Autowired
    CategoryService categoryService;
    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @ApiOperation(value="Returns all Categories in the Database", response= CategoryView.class, responseContainer = "List")
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @GetMapping(value="/categories", produces = {"application/json"})
    public ResponseEntity<?> listAllCategories(HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        List<CategoryView> list = categoryService.findAllByCustom();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @ApiOperation(value="Add new Category", response= Category.class)
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @PostMapping(value="/category", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addNewCategory(@Valid @RequestBody Category newCategory, HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        Category category = categoryService.save(newCategory);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }
}
