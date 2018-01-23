package bxq.repo.dto;

public class ConfigInfo {

	private String jdbcUrl;
	private String jdbcDriver;
	private String jdbcUser;
	private String jdbcPwd;
	private String fileWritePath;
	private String charset;

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getJdbcDriver() {
		return jdbcDriver;
	}

	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}

	public String getJdbcUser() {
		return jdbcUser;
	}

	public void setJdbcUser(String jdbcUser) {
		this.jdbcUser = jdbcUser;
	}

	public String getJdbcPwd() {
		return jdbcPwd;
	}

	public void setJdbcPwd(String jdbcPwd) {
		this.jdbcPwd = jdbcPwd;
	}

	public String getFileWritePath() {
		return fileWritePath;
	}

	public void setFileWritePath(String fileWritePath) {
		this.fileWritePath = fileWritePath;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConfigInfo [jdbcUrl=");
		builder.append(jdbcUrl);
		builder.append(", jdbcDriver=");
		builder.append(jdbcDriver);
		builder.append(", jdbcUser=");
		builder.append(jdbcUser);
		builder.append(", jdbcPwd=");
		builder.append(jdbcPwd);
		builder.append(", fileWritePath=");
		builder.append(fileWritePath);
		builder.append(", charset=");
		builder.append(charset);
		builder.append("]");
		return builder.toString();
	}

}
