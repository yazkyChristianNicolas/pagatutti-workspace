package ar.com.pagatutti.mailapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Component
@Configuration
@Getter @Setter
public class AppConfig {
	
	@Value("${freemarker.templates.path}")
	private String freeMarkerTemplatePath;
	
}
