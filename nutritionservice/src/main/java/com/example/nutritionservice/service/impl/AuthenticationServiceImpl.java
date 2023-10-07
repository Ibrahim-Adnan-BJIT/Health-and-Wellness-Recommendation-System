package com.example.nutritionservice.service.impl;

import com.example.nutritionservice.exception.CustomException;
import com.example.nutritionservice.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public long getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return Long.parseLong(authentication.getName());
        } else {
            throw new CustomException(new Date(), "User not authenticated", HttpStatus.UNAUTHORIZED);
        }
    }

}
