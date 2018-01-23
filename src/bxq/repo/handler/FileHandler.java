package bxq.repo.handler;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Properties;

import bxq.repo.constants.IREPO;
import bxq.repo.dto.ConfigInfo;
import bxq.repo.utils.CloseableUtils;

public class FileHandler {
	
	
	public ConfigInfo readConfigFile(String cfgFilePath){
		
		ConfigInfo cfgInfo = new ConfigInfo();
		
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try {
			Properties props = new Properties();
			
			fis = new FileInputStream(cfgFilePath);
			
			bis = new BufferedInputStream(fis);
			
			props.load(bis);
			
			if( props.size() == 0){
				System.out.println("[BXQ_REPO] please check config file path");
			}			
			cfgInfo.setJdbcDriver(props.getProperty(IREPO.CFG.JDBC_DRIVER));
			cfgInfo.setJdbcUrl(props.getProperty(IREPO.CFG.JDBC_URL));
			cfgInfo.setJdbcUser(props.getProperty(IREPO.CFG.JDBC_USER));
			cfgInfo.setJdbcPwd(props.getProperty(IREPO.CFG.JDBC_PWD));		
			cfgInfo.setFileWritePath(props.getProperty(IREPO.CFG.FILE_WRITE_PATH));
			cfgInfo.setCharset(props.getProperty(IREPO.CFG.CHARSET));
			
		} catch (Exception e) {
			System.out.println("[BXQ_REPO] get config file error : " + e.getMessage());
			e.printStackTrace();
		} finally {
			CloseableUtils.instance().tryClose(bis);
			CloseableUtils.instance().tryClose(fis);			
		}
		
		return cfgInfo;
		
	}

}
