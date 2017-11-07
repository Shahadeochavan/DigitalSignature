package com.nextech.erp.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.exception.InvalidUserException;
import com.nextech.erp.model.User;
import com.nextech.erp.service.UserService;
public class AjaxLoginProcessingFilter extends HandlerInterceptorAdapter {

	@Autowired
	UserService userService;

	@Autowired
	private MessageSource messageSource;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!(request instanceof HttpServletRequest)) {
			return false;
		}
		String url = ((HttpServletRequest) request).getRequestURL().toString();
		if (url.contains("login")) {
			request.setAttribute("auth_token", true);
			request.setAttribute("current_token", true);
			return true;
		}
		if(((HttpServletRequest) request).getHeader("auth_token") == null){
			setResponse(request, response);
			throw new InvalidUserException("Unauthorized user requested. Please check credentials.");
		}
		String token = ((HttpServletRequest) request).getHeader("auth_token");
		User user = userService.getUserByUserId(TokenFactory.getUserId(token));
		if(user == null || !user.getPassword().equals(TokenFactory.getUserPassword(token))){
			setResponse(request, response);
			throw new InvalidUserException("Unauthorized user requested. Please check credentials.");
		}
		Long time = new Long(messageSource.getMessage(ERPConstants.SESSIONTIMEOUT,null, null));
		if (TokenFactory.isValidSession(token, time)) {
			setResponse(request, response);
			throw new InvalidUserException("User Session has expired. Please login again");
		}
		String generatedToken = TokenFactory.createAccessJwtToken(user);
		request.setAttribute("current_user", user.getId());
		((HttpServletResponse) response).addHeader("auth_token", generatedToken);
		request.setAttribute("auth_token", true);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

	private HttpServletResponse setResponse(ServletRequest request,ServletResponse response){
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.reset();
		httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
		httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
		httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
		httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me, auth_token, Origin");
		httpServletResponse.setHeader("Access-Control-Expose-Headers", "auth_token, Origin");
		return httpServletResponse;
	}
}
