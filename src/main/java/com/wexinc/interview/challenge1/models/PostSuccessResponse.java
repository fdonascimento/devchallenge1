package com.wexinc.interview.challenge1.models;

public class PostSuccessResponse {
	private int thread;
	private String authToken;

	public PostSuccessResponse(int thread, String authToken) {
		super();
		this.thread = thread;
		this.authToken = authToken;
	}

	public int getThread() {
		return thread;
	}

	public void setThread(int thread) {
		this.thread = thread;
	}

	public String getAuthToken() {
		return authToken;
	}
}
