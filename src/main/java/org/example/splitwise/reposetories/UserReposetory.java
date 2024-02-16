package org.example.splitwise.reposetories;

import org.example.splitwise.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface UserReposetory extends JpaRepository<User, Long> {

    public User findByName(String name);

    public User findByPhoneNumber(Long phone_number);
    public User findByEmail(String findByEmail);

}
