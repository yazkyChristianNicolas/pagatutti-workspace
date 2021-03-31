package ar.com.pagatutti.mailapi;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

import ar.com.pagatutti.mailapi.config.AppConfig;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

@SpringBootApplication
@ComponentScan({"ar.com.pagatutti.*"})
public class MailApiApplication {
	
	@Autowired
	private AppConfig appConfig;

	
	Logger logger = LoggerFactory.getLogger(MailApiApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(MailApiApplication.class, args);
	}

	
	@Bean
	public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	    PropertySourcesPlaceholderConfigurer properties = 
	      new PropertySourcesPlaceholderConfigurer();
	    properties.setLocation(new FileSystemResource("/etc/pagatutti/mail-api/application.properties"));
	    properties.setIgnoreResourceNotFound(false);
	    return properties;
	}
	
	
	@Bean
	public Configuration freeMarkerConfig() throws IOException{
		logger.info("Freemarker templates directory: " + appConfig.getFreeMarkerTemplatePath());
		Configuration cfg = (Configuration) new Configuration(freemarker.template.Configuration.VERSION_2_3_29);
		cfg.setDirectoryForTemplateLoading( new File(appConfig.getFreeMarkerTemplatePath()));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);
		cfg.setWrapUncheckedExceptions(true);
		cfg.setFallbackOnNullLoopVariable(false);
		return cfg;
	}
	
}
