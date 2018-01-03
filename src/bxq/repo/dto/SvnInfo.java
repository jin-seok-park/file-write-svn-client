package bxq.repo.dto;

public class SvnInfo {

	private String svnUrl;
	private String svnId;
	private String svnPwd;

	public String getSvnUrl() {
		return svnUrl;
	}

	public void setSvnUrl(String svnUrl) {
		this.svnUrl = svnUrl;
	}

	public String getSvnId() {
		return svnId;
	}

	public void setSvnId(String svnId) {
		this.svnId = svnId;
	}

	public String getSvnPwd() {
		return svnPwd;
	}

	public void setSvnPwd(String svnPwd) {
		this.svnPwd = svnPwd;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SvnInfo [svnUrl=");
		builder.append(svnUrl);
		builder.append(", svnId=");
		builder.append(svnId);
		builder.append(", svnPwd=");
		builder.append(svnPwd);
		builder.append("]");
		return builder.toString();
	}

}
