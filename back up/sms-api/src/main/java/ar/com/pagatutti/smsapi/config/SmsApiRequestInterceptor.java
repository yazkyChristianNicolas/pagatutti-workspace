package ar.com.pagatutti.smsapi.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Component
public class SmsApiRequestInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(SmsApiRequestInterceptor.class);


	@Override
	public boolean preHandle(
	  HttpServletRequest request, 
	  HttpServletResponse response, 
	  Object handler) {
		
		 HttpServletRequest requestCacheWrapperObject
	      = new ContentCachingRequestWrapper(request);
	    if(null != requestCacheWrapperObject.getParameterMap()) {
	    	try {
				logger.info(requestCacheWrapperObject.getInputStream().toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    return true;
	}
	
	@Override
	public void afterCompletion(
	  HttpServletRequest request, 
	  HttpServletResponse response, 
	  Object handler, 
	  Exception ex) {
	}
}