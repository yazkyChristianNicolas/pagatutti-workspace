package ar.com.pagatutti.siisaclient.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties("siisa.auth") 
public class SiisaAuthConfig {
	private String clientId;
	private String pinId;
	private String password;
	private Integer expiresIn;
}