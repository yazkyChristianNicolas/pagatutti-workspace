package ar.com.pagatutti.apicore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Configuration
@Getter @Setter
public class AppConfig {
	
	@Value("${mail.api.baseurl}")
	private String mailApiEndpoint;
	
	@Value("${mail.validation.url}")
	private String mailValidationUrl;
	
	@Value("${siisa.id.entidad}")
	private String siisaIdEntidad;
	
	@Value("${siisa.id.pin}")
	private String siisaIdPin;
	
	@Value("${siisa.id.clave}")
	private String siisaIdClave;
}
