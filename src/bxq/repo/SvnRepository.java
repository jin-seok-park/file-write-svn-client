package bxq.repo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import bxt.repo.dto.SvnInfo;

public class SvnRepository {

	public SvnRepository() {
	}

	/**
	 * 초기화
	 */
	public void init() {
		FSRepositoryFactory.setup();
		DAVRepositoryFactory.setup();
		SVNRepositoryFactoryImpl.setup();
	}

	public String addPath(String rootPath, String subPath) {

		if (rootPath.endsWith(File.separator)) {
			return rootPath + subPath;
		} else {
			return rootPath + File.separator + subPath;
		}

	}

	public void writeFile(String rootPath, String subPath, ByteArrayOutputStream buf) {

		String fullFilePath = addPath(rootPath, subPath);

		File file = new File(fullFilePath);

		if (fullFilePath.lastIndexOf("/") > 0) {
			String fullFileDir = fullFilePath.substring(0, fullFilePath.lastIndexOf("/"));

			File dirFile = new File(fullFileDir);
			if (!new File(fullFileDir).exists()) {
				boolean isMake = dirFile.mkdirs();
				if (isMake) {
					System.out.println("[SVN_REPO] file dir make success : " + dirFile);
				} else {
					System.out.println("[SVN_REPO] file dir make fail : " + dirFile);
				}
			}
		}

		try {
			FileOutputStream fos = new FileOutputStream(file);

			buf.writeTo(fos);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * SVN Repository 와의 연결을 확립하고 소스 코드를 읽어드릴 메소드를 호출하여 소스 코드 반환.
	 */
	public boolean getFile(List<SvnInfo> svnInfoList, String classQn, String charset, String writeFilePath) {

		boolean isFileWrite = false;
		for (SvnInfo svnInfo : svnInfoList) {
			try {
				SVNRepository repository = getSvnRepository(svnInfo.getSvnUrl(), svnInfo.getSvnId(),
						svnInfo.getSvnPwd());
				isFileWrite = _getFile(repository, assembleJavaSourcePath(classQn), charset, writeFilePath);
				if (isFileWrite) {
					return true;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return false;

	}

	/**
	 * SVN Repository 에서 입력 된 타입의 소스 코드를 읽어 드림.
	 */
	public boolean _getFile(SVNRepository repository, String filePath, String charset, String writeFilePath)
			throws Exception {

		SVNNodeKind nodeKind;
		try {
			nodeKind = repository.checkPath(filePath, -1);

			if (nodeKind == SVNNodeKind.FILE) {

				SVNProperties properties = new SVNProperties();
				ByteArrayOutputStream buf = new ByteArrayOutputStream();

				repository.getFile(filePath, -1, properties, buf);

				if (buf.size() <= 0) {
					return false;
				}

				writeFile(writeFilePath, filePath, buf);

			}
		} catch (SVNException e) {
			throw new Exception(e);
		}

		return true;
	}

	/**
	 * SVN Repository 와 연결된 SVNRepository instance 를 반환
	 */
	public SVNRepository getSvnRepository(String svnUrl, String svnId, String svnPwd) throws Exception {
		SVNRepository repository = null;
		try {
			repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(svnUrl));
			ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(svnId, svnPwd);
			repository.setAuthenticationManager(authManager);
		} catch (SVNException e) {
			throw new Exception(e);
		}
		return repository;
	}

	public String assembleJavaSourcePath(String classQn) {

		StringBuilder javaSourcePath = new StringBuilder();		
		return javaSourcePath.append(classQn.replace('.', '/')).append(".java").toString();		
		
	}

	/**
	 * byteArrayOutputStream을 incoding 된 String으로 전달
	 */
	public String getIncodingSource(ByteArrayOutputStream buf, String charset) {

		String fileContents = null;

		try {
			if (charset == null) {
				fileContents = new String(buf.toByteArray());
			} else {
				fileContents = new String(buf.toByteArray(), charset);
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				buf.close();
			} catch (Exception e2) {
			}

		}

		return fileContents;
	}

}
