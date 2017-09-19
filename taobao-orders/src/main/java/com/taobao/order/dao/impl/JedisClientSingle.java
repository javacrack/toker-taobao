package com.taobao.order.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.taobao.order.dao.JedisClient;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisClientSingle implements JedisClient{

	@Autowired
	private JedisPool jedisPool;
	@Override
	public String get(String key) {
		Jedis jedis=jedisPool.getResource();
		String value=jedis.get(key);
		jedis.close();
		return value;
	}

	@Override
	public String set(String key, String value) {
		Jedis jedis=jedisPool.getResource();		
		String reply=jedis.set(key, value);
		jedis.close();
		return reply;
	}

	@Override
	public long hset(String hkey, String key, String value) {
		Jedis jedis=jedisPool.getResource();
		long reply=jedis.hset(hkey, key, value);
		jedis.close();
		return reply;
	}

	@Override
	public String hget(String hkey, String key) {
		Jedis jedis=jedisPool.getResource();
		String reply=jedis.hget(hkey, key);
		jedis.close();
		return reply;
	}

	@Override
	public long incr(String key) {
		Jedis jedis=jedisPool.getResource();
		long reply=jedis.incr(key);
		jedis.close();
		return reply;
	}

	@Override
	public long expire(String key, int seconds) {
		Jedis jedis=jedisPool.getResource();
		long reply=jedis.expire(key, seconds);
		jedis.close();
		return reply;
	}

	@Override
	public long ttl(String key) {
		Jedis jedis=jedisPool.getResource();
		long reply=jedis.ttl(key);
		jedis.close();
		return reply;
	}

	@Override
	public long del(String key) {
		Jedis jedis=jedisPool.getResource();
		long reply=jedis.del(key);
		jedis.close();
		return reply;
	}

	@Override
	public long hdel(String hkey, String filed) {
		Jedis jedis=jedisPool.getResource();
		long reply=jedis.hdel(hkey,filed);
		jedis.close();
		return reply;
	}

}
