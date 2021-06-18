package com.proximity.technicaltest.service;

import com.proximity.technicaltest.common.Utility;
import com.proximity.technicaltest.entity.User;
import com.proximity.technicaltest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void save(final User user) {
        userRepository.save(user);
    }

    public User getUserByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public User getLoggedInUser(){
        UserDetails userDetails = Utility.getUserFromSecurityContext();
        User user = userRepository.findByUserName(userDetails.getUsername());
        return user;
    }
}
