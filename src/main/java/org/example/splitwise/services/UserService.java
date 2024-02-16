package org.example.splitwise.services;

import org.example.splitwise.entities.Group;
import org.example.splitwise.entities.User;
import org.example.splitwise.reposetories.UserReposetory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserReposetory userReposetory;

    public List<User> findAllUser()
    {
//        return userReposetory.findAllById(id).stream().filter();
        List<User> users = new ArrayList<>();
        userReposetory.findAll().forEach(users::add);
//        if(users.isEmpty())
//        {
//            throw new UserNotFound("users are not there.");
//        }
        return users;
    }

    public User findUserById(Long id)
    {
//        if(topicRepository.findById(id).isEmpty())
//        {
//            throw new TopicNotFound("topic not Found");
//        }
        User user = userReposetory.findById(id).orElse(null);
        return user;
    }
    public User findUserByPhoneNumber(Long phone_number)
    {
        User user = userReposetory.findByPhoneNumber(phone_number);
        if(user == null) return null;
        return user;
    }

    public User findUserByEmail(String email)
    {
        User user = userReposetory.findByEmail(email);
        if(user == null) return null;
        return user;
    }
    public void addUser(User user)
    {
//        userReposetory.save(user);
        User ans = userReposetory.save(user);
//        if(ans == null) {
//            throw new TopicNotSaved("topic failed to save.");
//        }
    }

    public void updateUser(Long id, User user)
    {
        User existingUser = userReposetory.findById(id).orElse(null);

        if(existingUser == null) return;

        existingUser.setEmail(user.getEmail());
        existingUser.setName(user.getName());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setPassword(user.getPassword());
        existingUser.setGroupList(user.getGroupList());
        userReposetory.save(existingUser);
    }

    public void deleteUser(Long id)
    {
        userReposetory.deleteById(id);
    }

    public List<Group> findGroupByUserId(Long id)
    {
        User user = userReposetory.findById(id).orElse(null);
        if(user == null) return null;
        return user.getGroupList();
    }


}
