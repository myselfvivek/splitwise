package org.example.splitwise.reposetories;

import org.example.splitwise.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface GroupRepository extends JpaRepository<Group, Long> {


//    @Query(
//            value = "SELECT group_id\n" +
//                    "from group_users as guser\n" +
//                    "where guser.user_id = ?1",
//            nativeQuery = true
//    )
//    List<Group> findGroupIdUserId(Long user_id);
//
//
//
//
//    @Query(
//            value = "SELECT group_id\n" +
//                    "from group_users as guser\n" +
//                    "where guser.user_id = ?1 AND guser.group_id = ?2"
//    )
//    Group findGroupByUserIdGroupId(Long user_id, Long group_id);

}
