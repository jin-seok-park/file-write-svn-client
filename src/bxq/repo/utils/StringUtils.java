package bxq.repo.utils;

public class StringUtils {

	private static final StringUtils instance = new StringUtils();

	private StringUtils() {
	}

	public static StringUtils instance() {
		return instance;
	}

	public boolean isEmpty(final CharSequence str) {
		if (null == str)
			return true;

		for (int i = 0, n = str.length(); i < n; ++i) {
			if (Character.isWhitespace(str.charAt(i))) {
				continue;
			}
			return false;
		}
		return true;
	}

	public boolean isEmpty(final String str) {
		if (null == str)
			return true;

		for (int i = 0, n = str.length(); i < n; ++i) {
			if (Character.isWhitespace(str.charAt(i))) {
				continue;
			}
			return false;
		}
		return true;
	}

}
