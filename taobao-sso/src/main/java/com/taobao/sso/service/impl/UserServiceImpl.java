package com.taobao.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taobao.common.pojo.CookieUtils;
import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.common.utils.JsonUtils;
import com.taobao.mapper.TbUserMapper;
import com.taobao.pojo.TbUser;
import com.taobao.pojo.TbUserExample;
import com.taobao.pojo.TbUserExample.Criteria;
import com.taobao.sso.dao.JedisClient;
import com.taobao.sso.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	@Qualifier(value="jedisClientSingle")
	private JedisClient jedisClient;
	@Value("${REDIS_USER_SESSION_KEY}")
	private String REDIS_USER_SESSION_KEY;
	@Value("${SSO_SESSION_EXPIRE}")
	private int SSO_SESSION_EXPIRE;
	
	
	/**
	 * 校验用户数据
	 */
	@Override
	public TaoBaoResult checkData(String content, Integer type) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		//对数据进行校验：1、2、3分别代表username、phone、email
		//用户名校验
		if (1 == type) {
			criteria.andUsernameEqualTo(content);
		//电话校验
		} else if ( 2 == type) {
			criteria.andPhoneEqualTo(content);
		//email校验
		} else {
			criteria.andEmailEqualTo(content);
		}
		//执行查询
		List<TbUser> list = userMapper.selectByExample(example);
		if (list == null || list.size() == 0) {
			return TaoBaoResult.ok(true);
		}
		return TaoBaoResult.ok(false);

	}

	@Override
	public TaoBaoResult userLogin(String username, String password,HttpServletRequest request,
			HttpServletResponse response) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = userMapper.selectByExample(example);
		//如果没有此用户名
		if (null == list || list.size() == 0) {
			return TaoBaoResult.build(400, "用户名或密码错误");
		}
		TbUser user = list.get(0);
		//比对密码
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
			return TaoBaoResult.build(400, "用户名或密码错误");
		}
		//生成token
		String token = UUID.randomUUID().toString();
		//保存用户之前，把用户对象中的密码清空。
		user.setPassword(null);
		//把用户信息写入redis
		jedisClient.set(REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(user));
		//设置session的过期时间
		jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
		//默认Cookie浏览器关了就没有
		CookieUtils.setCookie(request, response, "TB_TOKEN", token);
		//返回token
		return TaoBaoResult.ok(token);

	}
	@Override
	public TaoBaoResult getUserByToken(String token) {
		
		//根据token从redis中查询用户信息
		String json = jedisClient.get(REDIS_USER_SESSION_KEY + ":" + token);
		//判断是否为空
		if (StringUtils.isBlank(json)) {
			return TaoBaoResult.build(400, "此session已经过期，请重新登录");
		}
		//更新过期时间
		jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
		//返回用户信息
		return TaoBaoResult.ok(JsonUtils.jsonToPojo(json, TbUser.class));
	}

	@Override
	public TaoBaoResult createUser(TbUser user) {
		user.setUpdated(new Date());
		user.setCreated(new Date());
		//md5加密
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		userMapper.insert(user);
		return TaoBaoResult.ok();

	}

	@Override
	public TaoBaoResult loginOut(String token) {
		long id=jedisClient.del(REDIS_USER_SESSION_KEY + ":" + token);
		return TaoBaoResult.ok(id);
	}


}
