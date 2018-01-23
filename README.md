# file-write-svn-client

Create a java file that is committed to svn as a file in the desired path.

SVNKit is a pure-Java reverse-engineering of the Subversion API. SVNKit implements the JavaHL interface and SVNClientAdapter uses the library via this interface. Refer to the SVNKit website for Terms and Conditions.

SVNKit - (http://svnkit.com)

<br>

### HOW TO USE
-------------------------------------------
```
RepoMgr repoMgr = new RepoMgr();
		
repoMgr.getRepoFile("C:\\sonting\\cfg.properties");
		
//repoMgr.getRepoFileOne("C:\\sonting\\cfg.properties", "com.bxq.TestClass", "SYSID");
```

### cfg.properties
-------------------------------------------
```
JDBC_DRIVER=oracle.jdbc.OracleDriver
JDBC_URL=jdbc:oracle:thin:@127.0.0.1:1521:orcl
JDBC_USER=user
JDBC_PWD=4zqdihIiH/eyXPH+vZ9BKQ==
FILE_WRITE_PATH=C:\\sonting\\temp\\java
CHARSET=UTF-8
```
