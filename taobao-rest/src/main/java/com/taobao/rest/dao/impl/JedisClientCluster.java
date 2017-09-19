package com.taobao.rest.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.taobao.rest.dao.JedisClient;

import redis.clients.jedis.JedisCluster;

public class JedisClientCluster implements JedisClient{
	@Autowired
	private JedisCluster redisClusterClient;
	@Override
	public String get(String key) {
		String reply=redisClusterClient.get(key);
		return reply;
	}

	@Override
	public String set(String key, String value) {
		String reply=redisClusterClient.set(key, value);
		return reply;
	}

	@Override
	public long hset(String hkey, String key, String value) {
		long reply=redisClusterClient.hset(hkey, key, value);
		return reply;
	}

	@Override
	public String hget(String hkey, String key) {
		return redisClusterClient.hget(hkey, key);
	}

	@Override
	public long incr(String key) {		
		return redisClusterClient.incr(key);
	}

	@Override
	public long ttl(String key) {
		return redisClusterClient.ttl(key);
	}

	@Override
	public long expire(String key, int seconds) {
		
		return redisClusterClient.expire(key, seconds);
	}
	@Override
	public long del(String key) {
		
		return redisClusterClient.del(key);
	}

	@Override
	public long hdel(String hkey, String filed) {
		
		return redisClusterClient.hdel(hkey, filed);
	}

	

}
