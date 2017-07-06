package com.nextech.erp.filter;

import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.model.User;
import com.nextech.erp.service.PageService;
import com.nextech.erp.service.UserService;
import com.nextech.erp.service.UserTypeService;
import com.nextech.erp.service.UsertypepageassociationService;
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


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (request instanceof HttpServletRequest) {
			String url = ((HttpServletRequest) request).getRequestURL().toString();
			if (!url.contains("login")) {
				try {
					if(((HttpServletRequest) request).getHeader("auth_token") != null){
						String token = TokenFactory.decrypt(((HttpServletRequest) request).getHeader("auth_token"), TokenFactory.getSecretKeySpec());
						String[] string = token.split("-");
						User user = userService.getUserByUserId(string[0]);
//						Page page = pageservice.getPageByUrl(url);
						if(user != null && user.getPassword().equals(string[1])){
							String str = string[string.length - 1];
							Long time = new Long(messageSource.getMessage(ERPConstants.SESSIONTIMEOUT,null, null));
//							System.out.println(new Long(str) + time > new Date().getTime());
//							System.out.println(new Long(str) + time );
//							System.out.println(new Date().getTime());
							if (new Long(str) > new Date().getTime() - time) {
								String generatedToken = TokenFactory.createAccessJwtToken(user);
								request.setAttribute("current_user", user.getId());
								((HttpServletResponse) response).addHeader("auth_token", generatedToken);
								request.setAttribute("auth_token", true);
								return true;
							} else {
								HttpServletResponse httpServletResponse = setResponse(request, response);
								httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
							}
						}
						else{
							HttpServletResponse httpServletResponse = setResponse(request, response);
							httpServletResponse.sendError(599,ERPConstants.SESSION_EXPIRED);
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
