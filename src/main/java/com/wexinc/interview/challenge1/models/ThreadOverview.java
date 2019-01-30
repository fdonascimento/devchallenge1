package com.wexinc.interview.challenge1.models;

public class ThreadOverview {
	private int threadId;
	private String title;

	public ThreadOverview(int threadId, String title) {
		super();
		this.threadId = threadId;
		this.title = title;
	}

	public int getThreadId() {
		return threadId;
	}

	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
