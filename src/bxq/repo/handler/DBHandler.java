package bxq.repo.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bxq.repo.constants.IREPO;
import bxq.repo.dto.ClassInfo;
import bxq.repo.dto.ConfigInfo;
import bxq.repo.dto.SvnInfo;
import bxq.repo.utils.CloseableUtils;
import bxq.repo.utils.CryptoUtils;
import bxq.repo.utils.ThreadUtils;

public class DBHandler {

	public Connection connection;

	public final String selectTargetSql = "select sys_id sysId, class_qn classQn, repo_uid repoUid, updt_dttm updtDttm "
			+ "from bxt_class where updt_dttm > ? order by updt_dttm";
	public final String updateRepoUidSql = "update bxt_class set repo_uid = ? where sys_id = ? and class_qn = ?";
	public final String selectRepoSql = "select sys_id sysId, repo_uid repoUid, repo_url repoUrl, repo_conn_id repoConnId, repo_conn_pwd repoConnPwd "
			+ "from bxt_src_repo where del_yn != 'Y' and repo_cd = 'S'";

	public String maxUpdtDttm = "0";

	public String jdbcUrl;
	public String jdbcDriver;
	public String jdbcUser;
	public String jdbcPwd;

	public DBHandler(ConfigInfo configInfo) {
		this.jdbcUrl = configInfo.getJdbcUrl();
		this.jdbcDriver = configInfo.getJdbcDriver();
		this.jdbcUser = configInfo.getJdbcUser();
		this.jdbcPwd = configInfo.getJdbcPwd();
	}

	/**
	 * password crypto
	 * 
	 * @return
	 */
	public String decrypt(String password) {
		try {
			return CryptoUtils.instance().decrypt(password);
		} catch (Exception e) {
			System.out.println("[BXQ_REPO] password decrypt error : " + password);
		}

		return null;

	}

	/**
	 * jdbc connection
	 */
	public void connectionMgr() {

		close();
		try {
			Class.forName(jdbcDriver);
			connection = DriverManager.getConnection(jdbcUrl, jdbcUser, decrypt(jdbcPwd));
		} catch (Exception e) {
			System.out.println("[BXQ_REPO] db connection file : " + e.getMessage());
			e.printStackTrace();
			while (true) {
				ThreadUtils.instance().sleep(1000 * 60);
				try {
					connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPwd);
					break;
				} catch (Exception e2) {
				}
			}
		}

		System.out.println("[BXQ_REPO] db connection success");

	}

	/**
	 * jdbc close
	 */
	public void close() {
		if (connection != null) {
			CloseableUtils.instance().tryClose(connection);
		}
	}

	/**
	 * select target list
	 */
	public List<ClassInfo> selectTarget() {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<ClassInfo> targetClassInfoList = new ArrayList<ClassInfo>();
		try {
			stmt = connection.prepareStatement(selectTargetSql);
			stmt.setString(1, maxUpdtDttm);
			rs = stmt.executeQuery();
			while (rs.next()) {
				ClassInfo classInfo = new ClassInfo();
				classInfo.setSysId(rs.getString(IREPO.COLUMN.SYS_ID));
				classInfo.setClassQn(rs.getString(IREPO.COLUMN.CLASS_QN));
				classInfo.setRepoUid(rs.getString(IREPO.COLUMN.REPO_UID));
				maxUpdtDttm = rs.getString(IREPO.COLUMN.UPDT_DTTM);

				targetClassInfoList.add(classInfo);
			}
		} catch (Exception e) {
			System.out.println("[BXQ_REPO] select target fail : " + e.getMessage());
			e.printStackTrace();
			connectionMgr();
		} finally {
			CloseableUtils.instance().tryClose(rs);
			CloseableUtils.instance().tryClose(stmt);
		}

		return targetClassInfoList;

	}

	/**
	 * update repo_uid
	 */
	public int updateRepoUid(ClassInfo classInfo) {

		PreparedStatement stmt = null;
		int updateCnt = 0;
		try {
			stmt = connection.prepareStatement(updateRepoUidSql);
			stmt.setString(1, classInfo.getRepoUid());
			stmt.setString(2, classInfo.getSysId());
			stmt.setString(3, classInfo.getClassQn());
			updateCnt = stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("[BXQ_REPO][ERR] update repo_uid fail, sys_id : " + classInfo.getSysId()
					+ ", class_qn : " + classInfo.getClassQn() + ", repo_uid : " + classInfo.getRepoUid()
					+ ", error message : " + e.getMessage());

			e.printStackTrace();
			connectionMgr();
		} finally {
			CloseableUtils.instance().tryClose(stmt);
		}
		return updateCnt;

	}

	/**
	 * select repoInfo
	 */
	public List<SvnInfo> selectRepoInfo() {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<SvnInfo> svnInfoList = new ArrayList<SvnInfo>();
		try {
			stmt = connection.prepareStatement(selectRepoSql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				SvnInfo svnInfo = new SvnInfo();
				svnInfo.setSysId(rs.getString(IREPO.COLUMN.SYS_ID));
				svnInfo.setSvnUid(rs.getString(IREPO.COLUMN.REPO_UID));
				svnInfo.setSvnUrl(rs.getString(IREPO.COLUMN.REPO_URL));
				svnInfo.setSvnId(rs.getString(IREPO.COLUMN.REPO_CONN_ID));
				svnInfo.setSvnPwd(rs.getString(IREPO.COLUMN.REPO_CONN_PWD));

				svnInfoList.add(svnInfo);
			}
		} catch (Exception e) {
			System.out.println("[BXQ_REPO] select repoInfo fail : " + e.getMessage());
			e.printStackTrace();
			connectionMgr();
		} finally {
			CloseableUtils.instance().tryClose(rs);
			CloseableUtils.instance().tryClose(stmt);
		}

		return svnInfoList;

	}

}
