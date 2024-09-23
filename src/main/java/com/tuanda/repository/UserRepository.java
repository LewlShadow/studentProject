package com.tuanda.repository;

import com.tuanda.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT username  FROM User WHERE email = :email")
    String findUsernameByEmail(String email);

    @Query("SELECT id FROM User WHERE username = :username")
    Long findUserIdByUserName(String username);

    User findUserByUsername(String username);

    @Query("SELECT id FROM User WHERE username = :username AND id != :id")
    Long findUserIdByUserNameExceptSelf(String username, Long id);

    @Query("SELECT username  FROM User WHERE email = :email AND id != :id")
    String findUsernameByEmailExceptSelf(String email, Long id);
}
