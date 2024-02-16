package org.example.splitwise.controllers;

import org.example.splitwise.entities.Group;
import org.example.splitwise.entities.User;
import org.example.splitwise.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

//    getting all the user
    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    public List<User> findAllUser()
    {
        List<User> users = userService.findAllUser();
        return users;
    }

//    getting all group of particular id
    @RequestMapping(value = "/api/user/{id}/group", method = RequestMethod.GET)
    public List<Group> findGroupByUserId(@PathVariable Long id)
    {
        List<Group> group = userService.findGroupByUserId(id);
        return group;
    }

//    getting particular user
    @RequestMapping(value = "/api/user/{id}", method = RequestMethod.GET)
    public User findUserById(@PathVariable Long id)
    {
        User users = userService.findUserById(id);
        return users;
    }

    @RequestMapping(value = "/api/user/phone_number/{phone_number}", method = RequestMethod.GET)
    public User findUserByPhoneNumber(@PathVariable Long phone_number)
    {
        User users = userService.findUserByPhoneNumber(phone_number);
        return users;
    }

    @RequestMapping(value = "/api/user/email/{email}", method = RequestMethod.GET)
    public User findUserByEmail(@PathVariable String email)
    {
        User users = userService.findUserByEmail(email);
        return users;
    }

//    adding user
    @RequestMapping(value = "/api/user", method = RequestMethod.POST)
    public void addUser(@RequestBody User user)
    {
        userService.addUser(user);
    }

//    updating user
    @RequestMapping(value = "/api/user/{id}", method = RequestMethod.PUT)
    public void updateUser(@PathVariable Long id, @RequestBody User user)
    {
        userService.updateUser(id, user);
    }

//    deleting user
    @RequestMapping(value = "/api/user/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable Long id)
    {
        userService.deleteUser(id);
    }
}
