package com.taobao.portal.service;

import com.taobao.pojo.TbUser;

public interface UserService {
	public TbUser getUserByToken(String token);
}
