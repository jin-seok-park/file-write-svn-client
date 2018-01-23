package bxq.repo.constants;

public interface IREPO {
	
	
	String JAVA = ".java";

	interface COLUMN {
		String SYS_ID = "sysId";
		String CLASS_QN = "classQn";
		String UPDT_DTTM = "updtDttm";
		String REPO_UID = "repoUid";
		String REPO_URL = "repoUrl";
		String REPO_CONN_ID = "repoConnId";
		String REPO_CONN_PWD = "repoConnPwd";							
	}
	
	interface CFG {
		String JDBC_DRIVER = "JDBC_DRIVER";
		String JDBC_URL = "JDBC_URL";
		String JDBC_USER = "JDBC_USER";
		String JDBC_PWD = "JDBC_PWD";
		String FILE_WRITE_PATH = "FILE_WRITE_PATH";
		String CHARSET = "CHARSET";
	}
}
