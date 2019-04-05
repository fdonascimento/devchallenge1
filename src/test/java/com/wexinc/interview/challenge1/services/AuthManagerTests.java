package com.wexinc.interview.challenge1.services;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.wexinc.interview.challenge1.AppModule;
import com.wexinc.interview.challenge1.AuthorizationException;
import com.wexinc.interview.challenge1.models.AccessLevel;
import com.wexinc.interview.challenge1.models.AuthorizationToken;
import com.wexinc.interview.challenge1.repositories.UserRepo;

public class AuthManagerTests {
	private static final String pwd = "password";
	private AuthManager mgr;
	private UserRepo userRepo;
	private int userId;
	private PasswordHasher hasher;

	@Before
	public void setup() {
		Injector injector = Guice.createInjector(new AppModule());
		mgr = injector.getInstance(AuthManager.class);
		userRepo = injector.getInstance(UserRepo.class);
		hasher = injector.getInstance(PasswordHasher.class);

		userId = userRepo.createUser("Test User", hasher.hash(pwd, "salt"), AccessLevel.User).getId();
	}

	@Test
	public void canLogIn() throws AuthorizationException {
		AuthorizationToken token = mgr.login(userId, pwd);

		assertNotNull("Authorization token was null", token);
		assertEquals("Wrong access level", AccessLevel.User, token.getAccess());
	}
	
	@Test
	public void canValidateToken() throws AuthorizationException {
		AuthorizationToken token = mgr.login(userId, pwd);
		
		assertNotNull("Token validation failed", mgr.verifyAuthToken(token.getAuthToken()));
	}
	
	@Test
	public void canRotateToken() throws AuthorizationException {
		AuthorizationToken token = mgr.login(userId, pwd);
		
		try {
		Thread.sleep(5);
		}
		catch (InterruptedException ex) {
			// Meh.  We don't care.
		}
		
		AuthorizationToken newToken = mgr.rotateAuthToken(token);
		
		assertNotNull("New token is null", newToken);
		assertNotEquals("Token values are identical", token.getAuthToken(), newToken.getAuthToken());
		assertNotNull("New token is invalid", mgr.verifyAuthToken(newToken.getAuthToken()));
	}
	
	@Test
	public void authenticate_whenPasswordIsCorrect_thenAuthenticate() throws AuthorizationException {
		//when
		String correctPassword = "password";
		
		mgr.authenticate(userId, correctPassword);
	}
	
	@Test(expected=AuthorizationException.class)
	public void authenticate_whenPasswordIsIncorrect_thenThrowsAuthorizationException() throws AuthorizationException {
		String worngPassword = "wrong";
		
		mgr.authenticate(userId, worngPassword);
	}
	
	@Test(expected=AuthorizationException.class)
	public void changePassword_whenChangePassword_oldPasswordDontLogin() throws AuthorizationException {
		String currentPassword = "password";
		AuthorizationToken token = mgr.login(userId, currentPassword);
		
		String newPassword = "newPassword";
		mgr.changePassword(token, newPassword);
		
		mgr.login(userId, currentPassword);
	}
	
	@Test
	public void changePassword_whenChangePassword_newPasswordLogin() throws AuthorizationException {
		String currentPassword = "password";
		AuthorizationToken token = mgr.login(userId, currentPassword);
		
		String newPassword = "newPassword";
		mgr.changePassword(token, newPassword);
		
		mgr.login(userId, newPassword);
	}
}
