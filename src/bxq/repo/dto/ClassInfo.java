package bxq.repo.dto;

public class ClassInfo {

	private String sysId;
	private String classQn;
	private String repoUid;

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getClassQn() {
		return classQn;
	}

	public void setClassQn(String classQn) {
		this.classQn = classQn;
	}

	public String getRepoUid() {
		return repoUid;
	}

	public void setRepoUid(String repoUid) {
		this.repoUid = repoUid;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ClassInfo [sysId=");
		builder.append(sysId);
		builder.append(", classQn=");
		builder.append(classQn);
		builder.append(", repoUid=");
		builder.append(repoUid);
		builder.append("]");
		return builder.toString();
	}

}
