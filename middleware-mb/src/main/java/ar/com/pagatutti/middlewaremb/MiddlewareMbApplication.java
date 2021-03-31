package ar.com.pagatutti.middlewaremb;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;


@SpringBootApplication
public class MiddlewareMbApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiddlewareMbApplication.class, args);
	}
	
	@Bean
	public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
	    return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
	}
	
	@KeycloakConfiguration
	public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter
	{
	    /**
	     * Registers the KeycloakAuthenticationProvider with the authentication manager.
	     */
	    @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        auth.authenticationProvider(keycloakAuthenticationProvider());
	    }

	    /**
	     * Defines the session authentication strategy.
	     */
	    @Bean
	    @Override
	    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
	        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
	    }

	    @Override
	    protected void configure(HttpSecurity http) throws Exception
	    {
	        super.configure(http);
	        http
	                .authorizeRequests()
	                .antMatchers("/customers*").hasRole("USER")
	                .antMatchers("/admin*").hasRole("ADMIN")
	                .anyRequest().permitAll();
	    }
	    
	    @Bean
	    public KeycloakConfigResolver keycloakConfigResolver() {
	        return new KeycloakSpringBootConfigResolver();
	    }
	}

}
