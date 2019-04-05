package com.wexinc.interview.challenge1.services;

import com.wexinc.interview.challenge1.AuthorizationException;
import com.wexinc.interview.challenge1.models.AuthorizationToken;

public interface AuthManager {
	public AuthorizationToken login(int userId, String password) throws AuthorizationException;

	public AuthorizationToken verifyAuthToken(String authToken) throws AuthorizationException;

	public AuthorizationToken rotateAuthToken(AuthorizationToken token) throws AuthorizationException;

	public AuthorizationToken changePassword(AuthorizationToken token, String newPassword);

	public void authenticate(int userId, String password) throws AuthorizationException;
}
