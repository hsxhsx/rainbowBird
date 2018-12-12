package cn.rf.loan.server.support.importdata;

import cn.rf.loan.server.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 作者：冯小宁 
 * 线程管理
 */
@Service
public class MainThread{
	private Logger				log				= LoggerFactory.getLogger(MainThread.class);
	//核心池的大小
	private final static int	threadPool		= 1;
	//线程池最大线程数
	private final static int	maximumPoolSize	= 30;
	//表示线程没有任务执行时最多保持多久时间会终止
	private long				keepAliveTime;
	@Autowired
	private ImportDataRedis	importService;

	/**
	 * 线程主入口
	 */
	public void startThread() {
		
		ExecutorService executor = Executors.newFixedThreadPool(threadPool);
		
		for (int i = 0; i < threadPool; i++) {
			executor.execute(new Runnable() {
				public void run() {
					while (true) {
						
						if (null == importService) {
							importService = SpringContextUtil.getBean(ImportDataRedis.class);
						}
						
						try {
							importService.doImport();
							Thread.sleep(1200 * 5 * 2);
						} catch (InterruptedException e) {
							log.error("{}，{}", "线程出错", e.getMessage());
							reTry();
						} catch (Exception e) {
							log.error("{}，{}", "非线程出错", e.getMessage());
							reTry();
						}
					}
				}
				
				private void reTry() {
					log.debug("数据同步异常，正在重建连接......");
					try {
						Thread.sleep(500);
						run();
					} catch (InterruptedException e) {
						log.debug(e.getMessage());
						reTry();
					} catch (Exception e) {
						log.debug(e.getMessage());
						reTry();
					}
				}
			});
		}
	}
}
