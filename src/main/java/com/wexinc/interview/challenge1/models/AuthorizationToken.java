package com.wexinc.interview.challenge1.models;

public class AuthorizationToken {
	private int userId;
	private String authToken;
	private AccessLevel access;

	public AuthorizationToken(int userId, String authToken, AccessLevel access) {
		super();
		this.userId = userId;
		this.authToken = authToken;
		this.access = access;
	}

	public int getUserId() {
		return userId;
	}

	public String getAuthToken() {
		return authToken;
	}

	public AccessLevel getAccess() {
		return access;
	}

}
