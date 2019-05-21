package com.udev.reunion.repository;

import com.udev.reunion.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login);

    @Query("SELECT u FROM User u WHERE UPPER(login) LIKE UPPER(CONCAT('%',:pattern,'%'))" +
            "OR UPPER(firstname) LIKE UPPER(CONCAT('%',:pattern,'%'))" +
            "OR UPPER(lastname) LIKE UPPER(CONCAT('%',:pattern,'%'))" +
            "OR UPPER(login) LIKE UPPER(CONCAT('%',:pattern,'%'))" +
            "OR UPPER(about) LIKE UPPER(CONCAT('%',:pattern,'%'))")
    List<User> findAllUserMatchinPattern(@Param("pattern") String pattern);

}
