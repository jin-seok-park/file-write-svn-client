package test;

import bxq.repo.RepoMgr;

public class Test {
	
	public static void main(String[] args) {
		
		
		RepoMgr repoMgr = new RepoMgr();
		
		repoMgr.getRepoFile("C:\\git\\mars-workspace\\bxt-q\\bxt.repo\\config\\cfg.properties");
		
		//repoMgr.getRepoFileOne("C:\\git\\mars-workspace\\bxt-q\\bxt.repo\\config\\cfg.properties", "bxs.dep.dpm.service.SDPM0100");
	}
	

}
