package com.udev.reunion.repository;

import com.udev.reunion.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
