package com.taobao.order.dao;

public interface JedisClient {
	String get(String key);
	String set(String key,String value);
	long hset(String hkey,String key,String value);
	String hget(String hkey,String key);
	long incr(String key);
	long ttl(String key);
	long expire(String key, int seconds);
	long del(String key);
	long hdel(String  hkey,String filed);
}
