package org.example.splitwise.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.splitwise.dto.groupDTO;
import org.example.splitwise.entities.Group;
import org.example.splitwise.entities.User;
import org.example.splitwise.reposetories.GroupRepository;
import org.example.splitwise.reposetories.UserReposetory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

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

//    getting all user of particular group
    public List<User> findUserByGroupId(Long id)
    {
        Group group = groupRepository.findById(id).orElse(null);
        if(group == null) return null;

        return group.getUsers().stream().toList();
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
    public void createGroup(Map<String, Object> mapObject, Long creator_id)
    {
        boolean right = inputFormatCheckGroupDTO(mapObject);

        if(!right) return;

        ObjectMapper mapper = new ObjectMapper();

        String groupName = mapper.convertValue(mapObject.get("group_name"), new TypeReference<>() {});
        List<Long> userId = mapper.convertValue(mapObject.get("user_ids"), new TypeReference<>() {});
        List<User> users = new ArrayList<>();
        for(Long id: userId)
        {
            User user = userReposetory.findById(id).orElse(null);
            if(user == null)
            {
                // user does not exist.
                return;
            }
            else
            {
                users.add(user);
            }
        }
        Group group = new Group();
        group.setName(groupName);
        group.setUsers(users);

        groupRepository.save(group);
    }

    public void addUserToGroup(Long group_id, String user_name)
    {
        User user = userReposetory.findByName(user_name);
        if(user == null) return;


        Group group = groupRepository.findById(group_id).orElse(null);

        if(group == null) return;

        List<User> users = group.getUsers();
        users.add(user);
        group.setUsers(users);

        groupRepository.save(group);
    }

    public void addUserToGroup(Long group_id, Long user_id)
    {
        User user = userReposetory.findById(user_id).orElse(null);
        if(user == null) return;

        Group group = groupRepository.findById(group_id).orElse(null);

        if(group == null) return;

        List<User> users = group.getUsers();
        users.add(user);
        group.setUsers(users);

        groupRepository.save(group);
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




}
