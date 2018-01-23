package bxq.repo.utils;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CloseableUtils {

	private static final CloseableUtils instance = new CloseableUtils();

	private CloseableUtils() {
	}

	public static CloseableUtils instance() {
		return instance;
	}

	public void tryClose(Closeable... closeables) {

		for (Closeable closeable : closeables) {
			if (closeable != null) {
				try {
					closeable.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public void tryClose(Statement... closeables) {

		for (Statement closeable : closeables) {
			if (closeable != null) {
				try {
					closeable.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public void tryClose(ResultSet... closeables) {

		for (ResultSet closeable : closeables) {
			if (closeable != null) {
				try {
					closeable.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public void tryClose(Connection... closeables) {

		for (Connection closeable : closeables) {
			if (closeable != null) {
				try {
					closeable.close();
				} catch (Exception e) {
				}
			}
		}
	}

}
