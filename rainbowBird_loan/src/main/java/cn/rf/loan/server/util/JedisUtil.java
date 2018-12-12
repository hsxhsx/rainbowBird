/**
 * 
 */
package cn.rf.loan.server.util;


import redis.clients.jedis.Jedis;

/**
 * Created by rocky on 11/10/2017.
 */
public class JedisUtil {

    private static Jedis client=new Jedis(Consts.REDIS_SERVER);


    public String getValue(String key){
        return client.get(key);
    }

    public void set(String key,String val){
        client.set(key,val);
    }
    
    public static void main(String[] args) {
    	long start=System.currentTimeMillis();
        JedisUtil test=new JedisUtil();
//        test.set("foo","foo");

        long start1=System.currentTimeMillis();
        String value= test.getValue("REDIS_AUTHENTICATE_PMS_PMS_USER_STEP1_TESTTEST2S99");

        long end =System.currentTimeMillis();
        System.out.print("总耗费时间："+ (end -start)+"毫秒, get耗费："+(end-start1));
    }
}

