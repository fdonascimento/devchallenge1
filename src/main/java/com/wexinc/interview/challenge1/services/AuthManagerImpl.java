package com.wexinc.interview.challenge1.services;

import java.util.Date;
import java.util.HashMap;

import com.google.inject.Inject;
import com.wexinc.interview.challenge1.AuthorizationException;
import com.wexinc.interview.challenge1.models.AuthorizationToken;
import com.wexinc.interview.challenge1.models.User;
import com.wexinc.interview.challenge1.repositories.UserRepo;

public class AuthManagerImpl implements AuthManager {
	private static final String tokenFormat = "%1$d:%2$s:%3$d";
	private static final HashMap<String, AuthorizationToken> tokens = new HashMap<String, AuthorizationToken>();
	private PasswordHasher hasher;
	private UserRepo userRepo;
	private User loggedUser;
	
	@Inject
	public AuthManagerImpl(PasswordHasher hasher, UserRepo userRepo) {
		this.hasher = hasher;
		this.userRepo = userRepo;
	}
	
	@Override
	public AuthorizationToken login(int userId, String password) throws AuthorizationException {
		User user = userRepo.loadUser(userId);
		
		if (user == null) throw new AuthorizationException();
		
		String verifyHash = hasher.hash(password, "salt");
		
		if (!verifyHash.equals(user.getPassHash())) throw new AuthorizationException();
		
		AuthorizationToken token = generateToken(user);

		tokens.put(token.getAuthToken(), token);
		loggedUser = user;
		return token;
	}

	@Override
	public void authenticate(String password) throws AuthorizationException {
		String verifyHash = hasher.hash(password, "salt");
		if (!verifyHash.equals(loggedUser.getPassHash())) throw new AuthorizationException();
	}

	private AuthorizationToken generateToken(User user) {
		String inputValue = String.format(tokenFormat, new Date().getTime(), user.getName(), user.getId());
		String tokenVal = hasher.hash(inputValue, "authToken");
		return new AuthorizationToken(user.getId(), tokenVal, user.getAccess());
	}

	@Override
	public AuthorizationToken verifyAuthToken(String authToken) throws AuthorizationException {
		AuthorizationToken token = tokens.getOrDefault(authToken, null);
		if (token == null) throw new AuthorizationException();
		return token;
	}

	@Override
	public AuthorizationToken rotateAuthToken(AuthorizationToken token) throws AuthorizationException {
		if (!tokens.containsKey(token.getAuthToken())) throw new AuthorizationException();
		
		tokens.remove(token.getAuthToken());
		User user = userRepo.loadUser(token.getUserId());
		AuthorizationToken newToken = generateToken(user);
		tokens.put(newToken.getAuthToken(), newToken);
		return newToken;
	}

	@Override
	public AuthorizationToken changePassword(String authToken, String newPassword) throws AuthorizationException {
		loggedUser.setPassHash(hasher.hash(newPassword, "salt"));
		userRepo.saveUser(loggedUser);

		tokens.remove(authToken);
		AuthorizationToken token = generateToken(loggedUser);
		tokens.put(token.getAuthToken(), token);

		return token;
	}

	
}
