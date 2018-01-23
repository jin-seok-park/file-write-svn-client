package bxq.repo.utils;

public class ThreadUtils {

	private static final ThreadUtils instance = new ThreadUtils();

	private ThreadUtils() {
	}

	public static ThreadUtils instance() {
		return instance;
	}

	/**
	 * 특정 밀리초 동안 현재 쓰레드를 sleep 상태로 만든다.
	 */
	public void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException("fail to Sleep", e);
		}
	}

}
