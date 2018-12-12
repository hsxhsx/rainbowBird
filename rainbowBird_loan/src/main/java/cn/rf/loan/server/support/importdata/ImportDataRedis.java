package cn.rf.loan.server.support.importdata;


import cn.rf.loan.server.dao.RedisDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author yuzaizhou
 * @date 2018/8/31
 */
@Service
public class ImportDataRedis {
    Logger logger = LoggerFactory.getLogger(ImportDataRedis.class);
    /**
     * 存入redis有效期:-1表示永久或者以及到期被删除
     */
    private final int					INTERVAL_TIME	= -1;

    @Autowired
    RedisDao redis;

    public void doImport() {

    }

        /**
         * 删除已失效的数据，保持redis缓存数据与数据库数据一致,"*"符号表示匹配所有key
         * @param patten
         * @param validKeys
         */
        public void synchKeys(String patten, Set<String> validKeys){
            Set<String> keys = redis.keys(patten);
            for (String key : keys) {
                logger.debug(key);
                if (!validKeys.contains(key)) {
                    redis.remove(key);
                }
            }
        }
}
