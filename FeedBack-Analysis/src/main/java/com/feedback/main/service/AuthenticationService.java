package com.feedback.main.service;

import com.feedback.main.exception.AuthenticationException;

public interface AuthenticationService {
	public long getAuthenticatedUser() throws AuthenticationException;
}
