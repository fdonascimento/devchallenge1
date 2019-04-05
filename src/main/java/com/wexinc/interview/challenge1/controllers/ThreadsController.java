package com.wexinc.interview.challenge1.controllers;

import static com.wexinc.interview.challenge1.util.JsonUtil.json;
import static spark.Spark.get;
import static spark.Spark.post;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.wexinc.interview.challenge1.models.AuthorizationToken;
import com.wexinc.interview.challenge1.models.Message;
import com.wexinc.interview.challenge1.models.MsgThread;
import com.wexinc.interview.challenge1.models.PostSuccessResponse;
import com.wexinc.interview.challenge1.models.ThreadOverview;
import com.wexinc.interview.challenge1.repositories.ThreadRepo;
import com.wexinc.interview.challenge1.services.AuthManager;
import com.wexinc.interview.challenge1.util.AppUtils;
import com.wexinc.interview.challenge1.util.Path;

import spark.Request;
import spark.Response;
import spark.Route;

public class ThreadsController {
	private AuthManager authManager;
	private Logger logger;
	private ThreadRepo threadRepo;

	@Inject
	public ThreadsController(final ThreadRepo threadRepo, final AuthManager authManager) {
		if (authManager == null)
			throw new IllegalArgumentException("AuthManager cannot be null");
		if (threadRepo == null)
			throw new IllegalArgumentException("ThreadRepo cannot be null");

		this.threadRepo = threadRepo;
		this.authManager = authManager;

		logger = LoggerFactory.getLogger(getClass());
		logger.info("Threads controller started");

		get(Path.ThreadList,
				(req, resp) -> threadRepo.getAll().stream()
						.map(thrd -> new ThreadOverview(thrd.getId(), thrd.getTitle())).collect(Collectors.toList()),
				json());

		get(Path.OneThread, (req, resp) -> {
			String param = req.params(":threadId");
			try {
				final int threadId = Integer.parseInt(param);
				return threadRepo.get(threadId);
			} catch (NumberFormatException e) {
				resp.status(404);
				return "404 error";
			}

		}, json());

		post(Path.ThreadList, handleMessagePost, json());
	}

	private Route handleMessagePost = (Request req, Response resp) -> {
		final Message message = new Gson().fromJson(req.body(), Message.class);
		if (message == null || AppUtils.isNullOrEmpty(message.getText())) {
			resp.status(400);
			return "";
		}

		final String authToken = req.headers("X-WEX-AuthToken");
		final AuthorizationToken token = authManager.verifyAuthToken(authToken);
		MsgThread thread;
		if (message.getThreadId() == 0) {
			thread = threadRepo.createMessage(token.getUserId(), message.getText());
		} else {
			thread = threadRepo.createMessage(message.getThreadId(), token.getUserId(), message.getText());
		}

		AuthorizationToken newAuthToken = authManager.rotateAuthToken(token);

		return new PostSuccessResponse(thread.getId(), newAuthToken.getAuthToken());
	};
}
