package com.taobao.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taobao.common.pojo.CookieUtils;
import com.taobao.pojo.TbUser;
import com.taobao.portal.service.impl.UserServiceImpl;

public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	private UserServiceImpl  userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token=CookieUtils.getCookieValue(request, "TB_TOKEN");
		TbUser user=userService.getUserByToken(token);
		//取不到用户信息
		if (null == user) {
			//跳转到登录页面，把用户请求的url作为参数传递给登录页面。
			response.sendRedirect(userService.SSO_BASE_URL + userService.SSO_PAGE_LOGIN 
					+ "?redirect=" + request.getRequestURL());
			//返回false
			return false;
		}
		request.setAttribute("user", user);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// handler执行之后，返回ModelAndView之前

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// handler执行之后，返回ModelAndView之前

	}

}
