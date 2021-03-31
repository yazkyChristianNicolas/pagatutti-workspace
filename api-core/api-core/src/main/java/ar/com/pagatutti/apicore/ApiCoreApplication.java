package ar.com.pagatutti.apicore;

import java.time.LocalDate;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ar.com.pagatutti.apicommons.rest.RestClientImpl;
import ar.com.pagatutti.apicore.config.LocalDateDeserializer;
import ar.com.pagatutti.apicore.config.LocalDateSerializer;
import ar.com.pagatutti.encriptor.EncryptorAES256;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("ar.com.pagatutti.*")
@EnableFeignClients("ar.com.pagatutti.smsapiclient.clients")
public class ApiCoreApplication extends SpringBootServletInitializer {

	static Logger logger = LoggerFactory.getLogger(ApiCoreApplication.class);

	
	public static void main(String[] args) {
		SpringApplication.run(ApiCoreApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ApiCoreApplication.class);
	}
	
	/*@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		logger.info("Load properties file");
	    PropertySourcesPlaceholderConfigurer properties = 
	      new PropertySourcesPlaceholderConfigurer();
	    properties.setLocation(new FileSystemResource("/home/pagatutti/core/application.properties"));
	    properties.setIgnoreResourceNotFound(false);
	    return properties;
	}*/

	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
	@Bean(name= "mappingJackson2HttpMessageConverter")
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());        
        objectMapper.registerModule(javaTimeModule); 
        objectMapper.findAndRegisterModules();
		mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
		return mappingJackson2HttpMessageConverter;
	}
	
	@Bean
	public RestClientImpl restClient() {
		return new RestClientImpl(mappingJackson2HttpMessageConverter());
	}
	
    @Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		return restTemplate;
	}
    
    
	@Bean 
	public EncryptorAES256 encriptor(){
		return new EncryptorAES256();
	}
}
