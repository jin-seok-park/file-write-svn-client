# file-write-svn-client

Create a java file that is committed to svn as a file in the desired path.

SVNKit is a pure-Java reverse-engineering of the Subversion API. SVNKit implements the JavaHL interface and SVNClientAdapter uses the library via this interface. Refer to the SVNKit website for Terms and Conditions.

SVNKit - (http://svnkit.com)



### HOW TO USE

```
SvnRepository svnRepo = new SvnRepository();
svnRepo.init();

SvnInfo svnInfo = new SvnInfo();
svnInfo.setSvnUrl(svnUrl);
svnInfo.setSvnId(svnId);
svnInfo.setSvnPwd(svnPwd);

svnRepo.getFile(svnInfoList, classQn, charset, writeFilePath);
```
