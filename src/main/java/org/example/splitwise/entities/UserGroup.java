package org.example.splitwise.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usergroup")
public class UserGroup {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usergroup_id")
    private Long Id;


    @Column(name = "user_id")
    private Long UserId;


    @Column(name = "group_id")
    private Long GroupId;


}
