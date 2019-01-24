package com.wexinc.interview.challenge1.util;

import lombok.Getter;

public class Path {
	@Getter public static final String ThreadList = "/threads/";
	@Getter public static final String OneThread = "/threads/:threadId/";
	@Getter public static final String Login = "/login/";
}
