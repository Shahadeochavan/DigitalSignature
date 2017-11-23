package com.nextech.dscrm.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.nextech.dscrm.constants.ERPConstants;
import com.nextech.dscrm.model.User;
import com.nextech.dscrm.service.PageService;
import com.nextech.dscrm.service.UserService;
import com.nextech.dscrm.service.UserTypeService;
import com.nextech.dscrm.service.UsertypepageassociationService;
public class AjaxLoginProcessingFilter extends HandlerInterceptorAdapter {

	@Autowired
	UserService userService;

	@Autowired
	TokenFactory tokenFactory;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	UserTypeService userTypeService;

	@Autowired
	UsertypepageassociationService usertypepageassociationService;

	@Autowired
	PageService pageservice;

	@Transactional
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (request instanceof HttpServletRequest) {
			String url = ((HttpServletRequest) request).getRequestURL().toString();
			if (!url.contains("login")) {
				try {
					if(((HttpServletRequest) request).getHeader("auth_token") != null){
						String encryptedToken = ((HttpServletRequest) request).getHeader("auth_token");
						User user = userService.getUserByUserId(TokenFactory.getUserId(encryptedToken));
						if(user != null && user.getPassword().equals(TokenFactory.getUserPassword(encryptedToken))){
							Long time = new Long(messageSource.getMessage(ERPConstants.SESSIONTIMEOUT,null, null));
							if (TokenFactory.isValidSession(encryptedToken, time)) {
								String generatedToken = TokenFactory.createAccessJwtToken(user);
								request.setAttribute("current_user", user.getId());
								((HttpServletResponse) response).addHeader("auth_token", generatedToken);
								request.setAttribute("auth_token", true);
								return true;
							} else {
								HttpServletResponse httpServletResponse = setResponse(request, response);
								httpServletResponse.sendError(599,ERPConstants.SESSION_EXPIRED);
							}
						}
						else{
							HttpServletResponse httpServletResponse = setResponse(request, response);
							httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
						}
					}else{
						if(((HttpServletRequest) request).getHeader("Access-Control-Request-Headers")==null){
							HttpServletResponse httpServletResponse = setResponse(request, response);
							httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				request.setAttribute("auth_token", true);
				request.setAttribute("current_token", true);
				return true;
			}
		}
		return false;
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
