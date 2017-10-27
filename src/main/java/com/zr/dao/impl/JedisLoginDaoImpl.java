package com.zr.dao.impl;

import com.zr.dao.JedisLoginDao;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisLoginDaoImpl implements JedisLoginDao{

	@Override
	public void insertUsernameAndSessionId(String userName, String sessionId) {
		//获得连接池的配置对象
		JedisPoolConfig config = new JedisPoolConfig();
		//设置最大连接数
		config.setMaxTotal(30);
		//设置空闲连接数
		config.setMaxIdle(10);
		JedisPool jedisPool = new JedisPool(config, "172.18.10.73", 6379);
		//获得核心对象
		Jedis jedis = null;
	
		try {
			//通过连接池获得连接
			jedis = jedisPool.getResource();
			jedis.set(userName, sessionId);
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
			if(jedis != null){
				jedis.close();
			}
			if(jedisPool != null){
				jedisPool.close();
			}
		}
	}

	@Override
	public String getSessionIdByUsername(String userName) {
		String sessionId = null;
		//获得连接池的配置对象
				JedisPoolConfig config = new JedisPoolConfig();
				//设置最大连接数
				config.setMaxTotal(30);
				//设置空闲连接数
				config.setMaxIdle(10);
				JedisPool jedisPool = new JedisPool(config, "172.18.10.73", 6379);
				//获得核心对象
				Jedis jedis = null;
			
				try {
					//通过连接池获得连接
					jedis = jedisPool.getResource();
					sessionId = jedis.get(userName);
					return sessionId;
				} catch (Exception e) {
					// TODO: handle exception
				} finally{
					if(jedis != null){
						jedis.close();
					}
					if(jedisPool != null){
						jedisPool.close();
					}
				}
				return sessionId;
	}

}
