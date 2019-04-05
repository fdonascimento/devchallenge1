package com.wexinc.interview.challenge1.controllers;

import static com.wexinc.interview.challenge1.util.JsonUtil.json;
import static spark.Spark.post;

import com.wexinc.interview.challenge1.models.ChangePassword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.wexinc.interview.challenge1.models.AuthorizationToken;
import com.wexinc.interview.challenge1.models.LoginRequest;
import com.wexinc.interview.challenge1.models.User;
import com.wexinc.interview.challenge1.repositories.UserRepo;
import com.wexinc.interview.challenge1.services.AuthManager;
import com.wexinc.interview.challenge1.util.AppUtils;
import com.wexinc.interview.challenge1.util.Path;

import spark.Request;
import spark.Response;
import spark.Route;

public class AuthController {
	private UserRepo userRepo;
	private AuthManager authManager;
	private Logger logger;

	@Inject
	public AuthController(AuthManager authManager, UserRepo userRepo) {
		if (authManager == null)
			throw new IllegalArgumentException("AuthManager cannot be null");
		if (userRepo == null)
			throw new IllegalArgumentException("UserRepo cannot be null");

		this.authManager = authManager;
		this.userRepo = userRepo;

		logger = LoggerFactory.getLogger(getClass());

		logger.info("Starting AuthController");

		post(Path.Login, handleLogin, json());
		post(Path.ChangePassword, handleChangePassword, json());
	}

	private Route handleLogin = (Request req, Response resp) -> {
		final LoginRequest loginRequest = new Gson().fromJson(req.body(), LoginRequest.class);
		if (loginRequest == null || AppUtils.isNullOrEmpty(loginRequest.getPassword())
				|| AppUtils.isNullOrEmpty(loginRequest.getUserName())) {
			resp.status(400);
			return "";
		}

		final User user = userRepo.getByName(loginRequest.getUserName());
		if (user == null) {
			resp.status(403);
			return "";
		}

		final AuthorizationToken token = authManager.login(user.getId(), loginRequest.getPassword());
		return token.getAuthToken();
	};

	private Route handleChangePassword = (Request req, Response resp) -> {
		final String authToken = req.headers("X-WEX-AuthToken");
		final AuthorizationToken token = authManager.verifyAuthToken(authToken);

		final ChangePassword changePassword = new Gson().fromJson(req.body(), ChangePassword.class);
		authManager.authenticate(token.getUserId(), changePassword.getCurrentPassword());

		if (!changePassword.getNewPassword().equals(changePassword.getVerifyPassword())) {
			resp.status(400);
			return "New passwords do not match";
		}

		final AuthorizationToken newToken = authManager.changePassword(token, changePassword.getNewPassword());
		return newToken.getAuthToken();
	};

}
