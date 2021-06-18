package com.proximity.technicaltest.common;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class Utility {

    public static UserDetails getUserFromSecurityContext(){
        UserDetails userDetails =(UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal() ;
        return userDetails;
    }
}
