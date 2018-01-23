package bxq.repo.dto;

public class SvnInfo {

	private String sysId;
	private String svnUid;
	private String svnUrl;
	private String svnId;
	private String svnPwd;

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getSvnUid() {
		return svnUid;
	}

	public void setSvnUid(String svnUid) {
		this.svnUid = svnUid;
	}

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
		builder.append("SvnInfo [sysId=");
		builder.append(sysId);
		builder.append(", svnUid=");
		builder.append(svnUid);
		builder.append(", svnUrl=");
		builder.append(svnUrl);
		builder.append(", svnId=");
		builder.append(svnId);
		builder.append(", svnPwd=");
		builder.append(svnPwd);
		builder.append("]");
		return builder.toString();
	}

}
