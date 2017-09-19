package com.taobao.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.common.utils.HttpClientUtil;
import com.taobao.pojo.TbUser;
import com.taobao.portal.service.UserService;
@Service
public class UserServiceImpl implements UserService{
	/**
	 * 注意这个不能放在controller里面
	 * 因为它是在spring容器内,springmvc容器内无法找到
	 */
	@Value("${SSO_BASE_URL}")
	public String SSO_BASE_URL;
	@Value("${SSO_USER_TOKEN}")
	private String SSO_USER_TOKEN;	
	@Value("${SSO_PAGE_LOGIN}")
	public String SSO_PAGE_LOGIN;

	@Override
	public TbUser getUserByToken(String token) {
		try {
			//调用sso系统的服务，根据token取用户信息
			String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_TOKEN + token);
			//把json转换成TaotaoREsult
			TaoBaoResult result = TaoBaoResult.formatToPojo(json, TbUser.class);
			if (result.getStatus() == 200) {
				TbUser user = (TbUser) result.getData();
				return user;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
}
