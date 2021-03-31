package ar.com.pagatutti.mobileapi.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ar.com.pagatutti.apicommons.beans.AuthenticatedUserBean;
import ar.com.pagatutti.apicommons.enums.AuthorizationTypesEnum;
import ar.com.pagatutti.apicommons.utils.JwtTokenUtils;

import ar.com.pagatutti.mobileapi.entities.UserEntity;
import ar.com.pagatutti.mobileapi.services.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtTokenUtils jwtTokenUtil;
	
	@Autowired
	private UserService userService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		final String authorizationHeader = request.getHeader("Authorization");
		logger.info("auth header " + authorizationHeader);
		String userName = null;
		String jwt = null;
		
		if (null != authorizationHeader && authorizationHeader.startsWith(AuthorizationTypesEnum.BEARER.getValue().toString())) {
			jwt = authorizationHeader.replace(AuthorizationTypesEnum.BEARER.getValue().toString(), "");
			logger.info("token: " + jwt);
			try {
				userName = jwtTokenUtil.getUserNameFromToken(jwt);
				if (!StringUtils.isEmpty(userName)  && SecurityContextHolder.getContext().getAuthentication() == null) {
					UserEntity user = this.userService.findByName(userName);
					//jwtTokenUtil.validateToken(jwt, user.getName())
					UserDetails userDetails = this.userService.getUserDetailFromUser(user);
					request.setAttribute(JwtTokenUtils.AUTHENTICATED_USER, new AuthenticatedUserBean(user.getId(), user.getName()));
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
				return;
			}
			
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}
		
		
		
		chain.doFilter(request, response);
	}
}
