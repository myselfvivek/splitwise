package org.example.splitwise.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true)
    private Long id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_email", unique = true)
    private String email;

    @Column(name = "user_phone_number", unique = true)
    private Long phoneNumber;

    @Column(name = "user_password")
    private String password;


//    @JsonIgnore
//    @ManyToMany(
//            mappedBy = "groupUsers",
//            cascade = CascadeType.ALL
//    )
//    private List<Group> groupList;
//
//
//    @ManyToMany
//    @JoinTable(
//            name = "person_friends",
//            joinColumns = @JoinColumn(name = "person_id"),
//            inverseJoinColumns = @JoinColumn(name = "friend_id")
//    )
//    private Set<User> friends = new HashSet<>();

}
