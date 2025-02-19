package com.example.AuthService.repository;

import com.example.AuthService.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserInfo, String> {

    public UserInfo findByUsername(String username);
}
