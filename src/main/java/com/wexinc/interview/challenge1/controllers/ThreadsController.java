package com.wexinc.interview.challenge1.controllers;

import static com.wexinc.interview.challenge1.util.JsonUtil.json;
import static spark.Spark.*;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.wexinc.interview.challenge1.models.Message;
import com.wexinc.interview.challenge1.models.MsgThread;
import com.wexinc.interview.challenge1.models.PostSuccessResponse;
import com.wexinc.interview.challenge1.models.ThreadOverview;
import com.wexinc.interview.challenge1.repositories.ThreadRepo;
import com.wexinc.interview.challenge1.services.AuthManager;
import com.wexinc.interview.challenge1.util.AppUtils;
import com.wexinc.interview.challenge1.util.Path;

import lombok.val;
import spark.Request;
import spark.Response;
import spark.Route;

public class ThreadsController {
	private AuthManager authManager;
	private Logger logger;
	private ThreadRepo threadRepo;

	@Inject
	public ThreadsController(final ThreadRepo threadRepo, final AuthManager authManager) {
		this.threadRepo = threadRepo;
		this.authManager = authManager;
		
		logger = LoggerFactory.getLogger(getClass());
		logger.info("Threads controller started");

		get(Path.ThreadList, (req, resp) -> threadRepo.getAll().stream()
				.map(thrd -> new ThreadOverview(thrd.getId(), thrd.getTitle()))
				.collect(Collectors.toList()), json());

		get(Path.OneThread, (req, resp) -> {
			val threadId = Integer.parseInt(req.params(":threadId"));
			return threadRepo.get(threadId);
		}, json());

		post(Path.ThreadList, handleMessagePost, json());
	}

	private Route handleMessagePost = (Request req, Response resp) -> {
		val message = new Gson().fromJson(req.body(), Message.class);
		if (message == null || AppUtils.isNullOrEmpty(message.getText())) {
			resp.status(400);
			return "";
		}
		
		val authToken = req.headers("X-WEX-AuthToken");
		val token = authManager.verifyAuthToken(authToken);
		MsgThread thread;
		if (message.getThreadId() == 0) {
			thread = threadRepo.createMessage(token.getUserId(), message.getText());
		}
		else {
			thread = threadRepo.createMessage(message.getThreadId(), token.getUserId(), message.getText());
		}
		
		return new PostSuccessResponse(thread.getId());
	};
}
