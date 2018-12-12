package cn.rf.loan.server.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * ParentDao  操作字符串redis缓存方法
 *            list中的操作全是按照right方式
 *
 * @author littlehow
 * @time 2016-08-12 09:02
 */
@Component
public class RedisDao {
	/**
	 * 日志记录
	 */
	private Logger							logger				= LoggerFactory.getLogger(this.getClass());
	/**
	 * 前缀
	 */
	public static final String				KEY_PREFIX_VALUE	= "EWELL:ESB:VALUE:";
	public static final String				KEY_PREFIX_SET		= "EWELL:ESB:SET:";
	public static final String				KEY_PREFIX_LIST		= "EWELL:ESB:LIST:";
	public static final String				KEY_PREFIX_HASH		= "EWELL:ESB:HASH:";
	@Autowired
	protected RedisTemplate<String, String>	redisTemplate;
	
	/**
	 * @return the redisTemplate
	 */
	public RedisTemplate<String, String> getRedisTemplate() {
		return redisTemplate;
	}

	public Set<String> keys(String pattenKey) {
//		String pattern = KEY_PREFIX_VALUE + pattenKey;
		try {
			Set<String> keys = redisTemplate.keys(pattenKey);
			return keys;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 缓存value操作
	 * @param k
	 * @param v
	 * @param time
	 * @return
	 */
	public boolean cacheValue(String k, String v, long time) {
		String key = KEY_PREFIX_VALUE + k;
		try {
			ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
			valueOps.set(key, v);
			if (time > 0)
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * pipline方式插入redis，测试10w条插入时间为9.764秒
	 * @param map
	 */
	public void cacheValueByPipeline(final Map<String, String> map) {
		List<Object> results = redisTemplate.executePipelined(new RedisCallback() {

			@Override
			public Object doInRedis(RedisConnection conn) throws DataAccessException {
				for(String key : map.keySet()) {
					String value = map.get(key);
					conn.set(key.getBytes(), value.getBytes()); 
				}
				
				return null;
			}
		});
	}

	/**
	 * 缓存value操作
	 * @param k
	 * @param v
	 * @return
	 */
	public boolean cacheValue(String k, String v) {
		return cacheValue(k, v, -1);
	}

	/**
	 * 判断缓存value是否存在
	 * @param k
	 * @return
	 */
	public boolean containsValueKey(String k) {
		return containsKey(KEY_PREFIX_VALUE + k);
	}

	/**
	 * 判断缓存是否存在
	 * @param k
	 * @return
	 */
	public boolean containsSetKey(String k) {
		return containsKey(KEY_PREFIX_SET + k);
	}

	/**
	 * 判断LIST缓存是否存在
	 * @param k
	 * @return
	 */
	public boolean containsListKey(String k) {
		return containsKey(KEY_PREFIX_LIST + k);
	}

	public boolean containsKey(String key) {
		try {
			return redisTemplate.hasKey(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取缓存
	 * @param k
	 * @return
	 */
	public String getValue(String k) {
		try {
//			ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
			BoundValueOperations<String, String> valueOps = redisTemplate.boundValueOps(KEY_PREFIX_VALUE + k);
			return valueOps.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 移除缓存
	 * @param k
	 * @return
	 */
	public boolean removeValue(String k) {
		return remove(KEY_PREFIX_VALUE + k);
	}

	public boolean removeSet(String k) {
		return remove(KEY_PREFIX_SET + k);
	}

	public boolean removeList(String k) {
		return remove(KEY_PREFIX_LIST + k);
	}

	/**
	 * 移除缓存
	 * @param key
	 * @return
	 */
	public boolean remove(String key) {
		try {
			redisTemplate.delete(key);
			return true;
		} catch (Throwable t) {
			logger.error("获取缓存失败key[" + key + ", error[" + t + "]");
		}
		return false;
	}

	/**
	 * 缓存set操作
	 * @param k
	 * @param v
	 * @param time
	 * @return
	 */
	public boolean cacheSet(String k, String v, long time) {
		String key = KEY_PREFIX_SET + k;
		try {
			SetOperations<String, String> valueOps = redisTemplate.opsForSet();
			valueOps.add(key, v);
			if (time > 0)
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 缓存set
	 * @param k
	 * @param v
	 * @return
	 */
	public boolean cacheSet(String k, String v) {
		return cacheSet(k, v, -1);
	}

	/**
	 * 缓存set
	 * @param k
	 * @param v
	 * @param time
	 * @return
	 */
	public boolean cacheSet(String k, Set<String> v, long time) {
		String key = KEY_PREFIX_SET + k;
		try {
			SetOperations<String, String> setOps = redisTemplate.opsForSet();
			setOps.add(key, v.toArray(new String[v.size()]));
			if (time > 0)
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 缓存set
	 * @param k
	 * @param v
	 * @return
	 */
	public boolean cacheSet(String k, Set<String> v) {
		return cacheSet(k, v, -1);
	}

	/**
	 * 获取缓存set数据
	 * @param k
	 * @return
	 */
	public Set<String> getSet(String k) {
		try {
			SetOperations<String, String> setOps = redisTemplate.opsForSet();
			return setOps.members(KEY_PREFIX_SET + k);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * list缓存
	 * @param k
	 * @param v
	 * @param time
	 * @return
	 */
	public boolean cacheList(String k, String v, long time) {
		String key = KEY_PREFIX_LIST + k;
		try {
			ListOperations<String, String> listOps = redisTemplate.opsForList();
			listOps.rightPush(key, v);
			if (time > 0)
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 缓存list
	 * @param k
	 * @param v
	 * @return
	 */
	public boolean cacheList(String k, String v) {
		return cacheList(k, v, -1);
	}

	/**
	 * 缓存list
	 * @param k
	 * @param v
	 * @param time
	 * @return
	 */
	public boolean cacheList(String k, List<String> v, long time) {
		String key = KEY_PREFIX_LIST + k;
		try {
			ListOperations<String, String> listOps = redisTemplate.opsForList();
			long l = listOps.rightPushAll(key, v);
			if (time > 0)
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 缓存list
	 * @param k
	 * @param v
	 * @return
	 */
	public boolean cacheList(String k, List<String> v) {
		return cacheList(k, v, -1);
	}

	/**
	 * 获取list缓存
	 * @param k
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> getList(String k, long start, long end) {
		try {
			BoundListOperations<String, String> listOps = redisTemplate.boundListOps(KEY_PREFIX_LIST + k);
//			ListOperations<String, String> listOps = redisTemplate.opsForList();
//			return listOps.range(KEY_PREFIX_LIST + k, start, end);
			return listOps.range(start, end);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取总条数, 可用于分页
	 * @param k
	 * @return
	 */
	public long getListSize(String k) {
		try {
			ListOperations<String, String> listOps = redisTemplate.opsForList();
			return listOps.size(KEY_PREFIX_LIST + k);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取总条数, 可用于分页
	 * @param listOps
	 * @param k
	 * @return
	 */
	public long getListSize(ListOperations<String, String> listOps, String k) {
		try {
			return listOps.size(KEY_PREFIX_LIST + k);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 移除list缓存
	 * @param k
	 * @return
	 */
	public boolean removeOneOfList(String k) {
		String key = KEY_PREFIX_LIST + k;
		try {
			ListOperations<String, String> listOps = redisTemplate.opsForList();
			listOps.rightPop(KEY_PREFIX_LIST + k);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * hash方式，新增一个hash
	 * @param k
	 * @param hashKey
	 * @param value
	 */
	public void cacheHash(String k, String hashKey, String value) {

		String key = KEY_PREFIX_HASH + k;
		try {
			HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
			hashOperations.put(key, hashKey, value);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
	
	/**
	 * hash方式，新增一批hash
	 * @param k
	 * @param m
	 */
	public void cacheAllHash(String k,  Map<String, Object> m) {

		String key = KEY_PREFIX_HASH + k;
		try {
			HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
			hashOperations.putAll(key, m);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
	
	/**
	 * 获取hash key下的所有数据
	 * @param k
	 * @return 
	 */
	public Map<String, String> getHash(String k) {

		String key = KEY_PREFIX_HASH + k;
		try {
			HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
			return hashOperations.entries(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * hash方式，获取key下hashKey的内容
	 * @param k
	 * @param hashKey
	 * @return
	 */
	public String getHash(String k, String hashKey) {

		String key = KEY_PREFIX_HASH + k;
		try {
			HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
			return hashOperations.get(key, hashKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
