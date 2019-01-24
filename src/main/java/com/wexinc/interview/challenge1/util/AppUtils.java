package com.wexinc.interview.challenge1.util;

import spark.utils.StringUtils;

public class AppUtils {
	public static boolean isNullOrEmpty(String str) {
		return str == null || StringUtils.isBlank(str);
	}
}
