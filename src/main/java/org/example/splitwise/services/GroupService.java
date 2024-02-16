package org.example.splitwise.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.example.splitwise.dto.groupDTO;
import org.example.splitwise.entities.Group;
import org.example.splitwise.entities.User;
import org.example.splitwise.reposetories.GroupRepository;
import org.example.splitwise.reposetories.UserReposetory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserService userService;

    private EntityManager entityManager;

    @Autowired
    private UserReposetory userReposetory;
    // getting all group
    public List<Group>  findAllGroup()
    {
        List<Group> groups = new ArrayList<>();
        groupRepository.findAll().forEach(groups::add);
//        if(users.isEmpty())
//        {
//            throw new UserNotFound("users are not there.");
//        }
        return groups;
    }

    // getting group of particular id
    public Group findGroupById(Long id)
    {
        Group group = groupRepository.findById(id).orElse(null);
        return group;
    }

    public List<Group> findAllGroupByUserId(Long user_id)
    {
        User user = userService.findUserById(user_id);
        if(user == null) return null;

        return user.getGroupList();
//        return groups;

//        return groupRepository.findGroupIdUserId(user_id);
    }
    public Group findGroupByUserIdGroupId(Long user_id, Long group_id)
    {
        User user = userService.findUserById(user_id);
        if(user == null) return null;

        List<Group> groups = user.getGroupList();

        for(Group group: groups)
        {
            Long current_group_id = group.getId();
            if(current_group_id == group_id) return group;
        }
        return null;
//        User user = userService.findUserById(user_id);
//        if(user == null) return null;
//        return groupRepository.findGroupByUserIdGroupId(user_id, group_id);
    }

//    getting all user of particular group
    public List<User> findUserByGroupId(Long id)
    {
        Group group = groupRepository.findById(id).orElse(null);
        if(group == null) return null;

        return group.getGroupUsers().stream().toList();
    }


    private boolean fieldExist(Class<?> clazz, String fieldName)
    {
        try{
            Field field = clazz.getDeclaredField(fieldName);
            return true;
        } catch(NoSuchFieldException e)
        {
            return false;
        }
    }
    public boolean inputFormatCheckGroupDTO(Map<String, Object> map)
    {
        for(Map.Entry<String, Object> entry: map.entrySet())
        {
            if(!fieldExist(groupDTO.class, entry.getKey()))
            {
                return false;
            }
        }
        return true;
    }
    public boolean createGroup(Map<String, Object> mapObject, Long creator_id)
    {

        User creator = userReposetory.findById(creator_id).orElse(null);
        if(creator == null)
        {
            return false;
        }

        boolean right = inputFormatCheckGroupDTO(mapObject);

        if(!right) return false;

        ObjectMapper mapper = new ObjectMapper();

        String groupName = mapper.convertValue(mapObject.get("group_name"), new TypeReference<>() {});
        List<Long> userId = mapper.convertValue(mapObject.get("user_ids"), new TypeReference<>() {});
        List<User> users = new ArrayList<>();
        userId.add(creator_id);
        for(Long id: userId)
        {
            User user = userReposetory.findById(id).orElse(null);
            if(user == null)
            {
                // user does not exist.
                return false;
            }
            else
            {
                users.add(user);
            }
        }
        Group group = new Group();
        group.setName(groupName);
        group.setGroupUsers(users);

        for(User user: users)
        {
            if(user == creator) continue;

            List<Group> groupList = user.getGroupList();
            if(groupList == null)
            {
//                System.out.println("he");
                groupList = new ArrayList<>();
                groupList.add(group);
            }
            else
            {
                groupList.add(group);
            }
            user.setGroupList(groupList);
//            userReposetory.save(user);
            userService.updateUser(user.getId(), user);
        }

        groupRepository.save(group);

        return true;
    }

    public boolean addUserToGroup(Long group_id, String user_name)
    {
        User user = userReposetory.findByName(user_name);
        if(user == null) return false;


        Group group = groupRepository.findById(group_id).orElse(null);

        if(group == null) return false;

        List<User> users = group.getGroupUsers();
        users.add(user);
        group.setGroupUsers(users);

        groupRepository.save(group);

        return true;
    }

    @Transactional
    public boolean addUserToGroup(Long group_id, Long user_id)
    {
        User user = userReposetory.findById(user_id).orElse(null);
        if(user == null) return false;

        Group group = groupRepository.findById(group_id).orElse(null);

        if(group == null) return false;

        List<User> groupUsers = group.getGroupUsers();
        groupUsers.add(user);

        System.out.println("hello");

        groupRepository.save(group);
        return true;
    }


//    public boolean deleteUserFromGroup(Long group_id, Long user_id)
//    {
////        if its money settle ment is zero than delete user
//        if(!moneysettled(user_id, group_id))
//        {
//            return false;
//        }
////        other wise delete it

//    }


//public boolean deleteGroup(Group id)
//{
////        chekc the settlement and then delete the group
//}


    public boolean deleteGroupById(Long group_id)
    {
        groupRepository.deleteById(group_id);
        return true;
    }



}
