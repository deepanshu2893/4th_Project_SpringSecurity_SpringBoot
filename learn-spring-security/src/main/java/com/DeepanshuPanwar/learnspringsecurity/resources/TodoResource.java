package com.DeepanshuPanwar.learnspringsecurity.resources;


import jakarta.annotation.security.RolesAllowed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoResource {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public static final List<Todo> TODO_LIST =
            List.of(new Todo("DeepanshuPanwar", "Learn AWS"),
                    new Todo("DeepanshuPanwar", "Get AWS Certified"));

    @GetMapping("/todos")
    public List<Todo> retrieveAllTodos() {
        return TODO_LIST;
    }

    @GetMapping("/users/{username}/todos")
    @PreAuthorize("hasRole('USER') and #username == authentication.name")
    @PostAuthorize("returnObject.username == 'DeepanshuPanwar'")
    @RolesAllowed({"ADMIN", "USER"})
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public Todo retrieveTodosForASpecificUser(@PathVariable String username) {
        return TODO_LIST.get(0);
    }

    @PostMapping("/users/{username}/todos")
    public void createTodoForASpecificUser(@PathVariable String username, @RequestBody Todo todo) {
    logger.info("Creating {} for {}", todo, username);
    }


}

record Todo (String username, String description){}
