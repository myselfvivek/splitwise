package org.example.splitwise.controllers;

import org.example.splitwise.entities.Group;
import org.example.splitwise.entities.User;
import org.example.splitwise.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class GroupController {

    @Autowired
    private GroupService groupService;


    @RequestMapping(value = "/api/group", method = RequestMethod.GET)
    public List<Group> findAllGroup()
    {
        List<Group> groups = groupService.findAllGroup();
        return groups;
    }

    @RequestMapping(value = "/api/group/{id}", method = RequestMethod.GET)
    public Group findGroupById(@PathVariable Long id)
    {
        Group group = groupService.findGroupById(id);
        return group;
    }

    @RequestMapping(value = "/api/group/{id}/user", method = RequestMethod.GET)
    public List<User> findUserByGroupId(@PathVariable Long id)
    {
        List<User> users = groupService.findUserByGroupId(id);
        return users;
    }

    @RequestMapping(value = "/api/group/{creator_id}", method= RequestMethod.POST)
    public void createGroup(@RequestBody Map<String, Object> mapObject, @PathVariable Long creator_id)
    {
        groupService.createGroup(mapObject, creator_id);
    }

    @RequestMapping(value = "/api/group/{id}/user/{username}", method = RequestMethod.POST)
    public void addUsersToGroup(@PathVariable Long id, @PathVariable String username)
    {
        groupService.addUserToGroup(id, username);
    }

    @RequestMapping(value = "/api/group/{group_id}/user/{user_id}", method = RequestMethod.POST)
    public void addUsersToGroup(@PathVariable Long group_id, @PathVariable Long user_id)
    {
        groupService.addUserToGroup(group_id, user_id);
    }
}
