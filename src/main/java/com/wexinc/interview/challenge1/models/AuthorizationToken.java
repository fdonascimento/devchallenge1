package com.wexinc.interview.challenge1.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class AuthorizationToken {
	@Getter private int userId;
	@Getter private String authToken;
	@Getter private AccessLevel access;
}
