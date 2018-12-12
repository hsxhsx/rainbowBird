package cn.rf.loan.server;

import cn.rf.loan.server.dao.RedisDao;
import cn.rf.loan.server.support.importdata.MainThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;


public class Application implements InitializingBean {
	static Logger	log			= LoggerFactory.getLogger(Application.class);

	@Autowired
    MainThread service;
	@Autowired
	RedisDao   redis;
	private boolean	isThreadRun	= true;

	public Application(boolean isThreadRun) {
		this.isThreadRun = isThreadRun;
	}

	public void afterPropertiesSet() throws Exception {
		if (isThreadRun) {
			service.startThread();
		}
	}
}
