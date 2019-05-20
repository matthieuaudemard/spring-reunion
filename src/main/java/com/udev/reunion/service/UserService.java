package com.udev.reunion.service;

import com.udev.reunion.domain.User;
import com.udev.reunion.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByLogin(String login){return this.userRepository.findByLogin(login).orElse(null);}

    public User send(User user){ return userRepository.save(user);}
}
