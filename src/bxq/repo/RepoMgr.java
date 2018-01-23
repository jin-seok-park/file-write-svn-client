package bxq.repo;

import bxq.repo.dto.ConfigInfo;
import bxq.repo.handler.DBHandler;
import bxq.repo.handler.FileHandler;
import bxq.repo.handler.ScheduleHandler;
import bxq.repo.type.SvnRepository;

public class RepoMgr implements IRepo {

	@Override
	public void getRepoFile(String configPath) {
		// TODO Auto-generated method stub
		
		FileHandler fileHandler = new FileHandler();
		ConfigInfo cfgInfo = fileHandler.readConfigFile(configPath);		
		DBHandler dbHandler = new DBHandler(cfgInfo);
		
		dbHandler.connectionMgr();
		
		new ScheduleHandler().getRepoScheduler(dbHandler, cfgInfo.getFileWritePath(), cfgInfo.getCharset());
		

	}

	/*
	 * 입력받은 classQn 가져오기.
	 */
	@Override
	public void getRepoFileOne(String configPath, String classQn, String sysId) {
		// TODO Auto-generated method stub
		
		System.out.println("[BXQ_REPO] [" + classQn + "] write start");
		FileHandler fileHandler = new FileHandler();
		ConfigInfo cfgInfo = fileHandler.readConfigFile(configPath);		
		DBHandler dbHandler = new DBHandler(cfgInfo);
		
		dbHandler.connectionMgr();
		
		SvnRepository svnRepo = new SvnRepository();
		svnRepo.getFile(dbHandler.selectRepoInfo(), classQn, cfgInfo.getCharset(), cfgInfo.getFileWritePath(), sysId);
		System.out.println("[BXQ_REPO] [" + classQn + "] write end");
	}
	


}
