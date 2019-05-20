package com.udev.reunion.repository;

import com.udev.reunion.domain.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {

}
