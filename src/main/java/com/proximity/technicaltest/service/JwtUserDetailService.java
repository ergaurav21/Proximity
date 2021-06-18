package com.proximity.technicaltest.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JwtUserDetailService  implements UserDetailsService {
    private final UserService userService;

    public  JwtUserDetailService(UserService userService){
        this.userService = userService;
    }
    @Override
    public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {
       com.proximity.technicaltest.entity.User user = userService.getUserByUserName(userName);
       String role = user.isInstructor() ? "ROLE_INSTRUCTOR" : "ROLE_STUDENT";
    return new User(user.getUserName(), user.getPassword(), List.of(new SimpleGrantedAuthority(role)));
    }
}
