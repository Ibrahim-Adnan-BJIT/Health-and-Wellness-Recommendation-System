package com.feedback.main.service.impl;


import com.feedback.main.exception.CustomException;
import com.feedback.main.service.AuthenticationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Override
	public long getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return Long.parseLong(authentication.getName());
        } else {
            throw new CustomException("User not authenticated");
        }
	}

}
