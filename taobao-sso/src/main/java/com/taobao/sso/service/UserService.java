package com.taobao.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taobao.common.pojo.TaoBaoResult;
import com.taobao.pojo.TbUser;

public interface UserService {
	public TaoBaoResult checkData(String content, Integer type) ;
	
	public TaoBaoResult getUserByToken(String token);
	public TaoBaoResult createUser(TbUser user) ;
	public TaoBaoResult loginOut(String token);
	public TaoBaoResult userLogin(String username, String password, HttpServletRequest request,
			HttpServletResponse response);
}
