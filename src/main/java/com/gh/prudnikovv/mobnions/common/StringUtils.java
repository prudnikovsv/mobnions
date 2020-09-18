package com.gh.prudnikovv.mobnions.common;

public class StringUtils {


	private StringUtils() {
	}

	public static boolean isNotBlank(final String str) {
		return !isBlank(str);
	}

	public static boolean isBlank(final String str) {
		if (str == null || str.isEmpty()) {
			return true;
		}

		for (int i = 0; i < str.length(); i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}
}