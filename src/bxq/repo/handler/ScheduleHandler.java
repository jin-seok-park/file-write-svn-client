package bxq.repo.handler;

import java.util.ArrayList;
import java.util.List;

import bxq.repo.dto.ClassInfo;
import bxq.repo.dto.SvnInfo;
import bxq.repo.type.SvnRepository;
import bxq.repo.utils.StringUtils;
import bxq.repo.utils.ThreadUtils;

public class ScheduleHandler {

	public void getRepoScheduler(DBHandler dbHandler, String writeFilePath, String charset) {

		SvnRepository svnRepo = new SvnRepository();

		while (true) {
			
			System.out.println("[BXQ_REPO] scheduler start");

			List<ClassInfo> classInfoList = dbHandler.selectTarget();

			if (classInfoList.size() > 0) {
				System.out.println("[BXQ_REPO] change class count : " + classInfoList.size());

				List<SvnInfo> svnInfoList = dbHandler.selectRepoInfo();

				for (ClassInfo classInfo : classInfoList) {

					if (StringUtils.instance().isEmpty(classInfo.getRepoUid())) {
						// repo_uid 가 null 이면 전체로 조회
						// - 해당 class_qn 을 bxt_class 에 update
						unknowSvnUid(svnRepo, svnInfoList, classInfo, charset, writeFilePath, dbHandler, classInfo.getSysId());
					} else {
						// repo_uid 가 null 이 아니면 해당 svn 으로 조회
						List<SvnInfo> oneSvnInfo = getSvnInfo(svnInfoList, classInfo.getRepoUid());
						if(oneSvnInfo.size() == 1){
							svnRepo.getFile(oneSvnInfo, classInfo.getClassQn(), charset, writeFilePath, classInfo.getSysId());
						}else{
							unknowSvnUid(svnRepo, svnInfoList, classInfo, charset, writeFilePath, dbHandler, classInfo.getSysId());
						}
					}

				}

			}else{
				System.out.println("[BXQ_REPO] no change class");
			}

			ThreadUtils.instance().sleep(1000 * 60 * 10);			
		}

	}
	
	/**
	 * 전체 svnInfo 중 대상 조회
	 */
	public void unknowSvnUid(SvnRepository svnRepo, List<SvnInfo> svnInfoList, ClassInfo classInfo, String charset, String writeFilePath, DBHandler dbHandler, String sysId){
		String svnUid = svnRepo.getFile(svnInfoList, classInfo.getClassQn(), charset, writeFilePath, sysId);
		if(!StringUtils.instance().isEmpty(svnUid)){
			dbHandler.updateRepoUid(classInfo);
		}else{
			System.out.println("[BXQ_REPO] not found class_qn info : " + classInfo.getClassQn());
		}
	}

	/**
	 * uid 가 같은 대상 조회
	 */
	public List<SvnInfo> getSvnInfo(List<SvnInfo> svnInfoList, String repoUid) {

		List<SvnInfo> out = new ArrayList<SvnInfo>();
		
		for (SvnInfo svnInfo : svnInfoList) {
			if(svnInfo.getSvnUid().equals(repoUid)){
				out.add(svnInfo);
				break;
			}
		}
		
		return out;

	}
}
