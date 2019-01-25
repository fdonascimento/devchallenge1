package com.wexinc.interview.challenge1.controllers;

import static com.wexinc.interview.challenge1.util.JsonUtil.json;
import static spark.Spark.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.wexinc.interview.challenge1.models.LoginRequest;
import com.wexinc.interview.challenge1.repositories.UserRepo;
import com.wexinc.interview.challenge1.services.AuthManager;
import com.wexinc.interview.challenge1.util.AppUtils;
import com.wexinc.interview.challenge1.util.Path;

import lombok.NonNull;
import lombok.val;
import spark.Request;
import spark.Response;
import spark.Route;

public class AuthController {
	@NonNull private UserRepo userRepo;
	@NonNull private AuthManager authManager;
	private Logger logger;
	
	@Inject
	public AuthController(AuthManager authManager, UserRepo userRepo) {
		this.authManager = authManager;
		this.userRepo = userRepo;
		
		logger = LoggerFactory.getLogger(getClass());
		
		logger.info("Starting AuthController");
		
		post(Path.Login, handleLogin, json());
	}
	
	private Route handleLogin = (Request req, Response resp) -> {
		val loginRequest = new Gson().fromJson(req.body(), LoginRequest.class);
		if (loginRequest == null || 
				AppUtils.isNullOrEmpty(loginRequest.getPassword()) ||
				AppUtils.isNullOrEmpty(loginRequest.getUserName())) {
			resp.status(400);
			return "";
		}
		
		val user = userRepo.getByName(loginRequest.getUserName());
		if (user == null) {
			resp.status(403);
			return "";
		}
	
		val token = authManager.login(user.getId(), loginRequest.getPassword());
		return token.getAuthToken();
	};
	
}
