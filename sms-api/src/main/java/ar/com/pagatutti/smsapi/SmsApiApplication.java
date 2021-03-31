package ar.com.pagatutti.smsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.com.pagatutti.apicommons.rest.RestClientImpl;
import ar.com.pagatutti.encriptor.EncryptorAES256;

@SpringBootApplication
@ComponentScan({"ar.com.pagatutti.*","com.yazkychristian.*"})
public class SmsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmsApiApplication.class, args);
	}
	
	@Bean
	public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	    PropertySourcesPlaceholderConfigurer properties = 
	      new PropertySourcesPlaceholderConfigurer();
	    properties.setLocation(new FileSystemResource("/home/pagatutti/sms-api/application.properties"));
	    properties.setIgnoreResourceNotFound(false);
	    return properties;
	}
	
	
	@Bean(name= "mappingJackson2HttpMessageConverter")
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
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
	
	@Configuration
	public class RequestLoggingFilterConfig {
	 
		    @Bean
		    public CommonsRequestLoggingFilter logFilter() {
		        CommonsRequestLoggingFilter filter
		          = new CommonsRequestLoggingFilter();
		        filter.setIncludeQueryString(true);
		        filter.setIncludePayload(true);
		        filter.setMaxPayloadLength(10000);
		        filter.setIncludeHeaders(false);
		        filter.setAfterMessagePrefix("REQUEST DATA : ");
		        return filter;
		    }
	}
    
	@Bean 
	public EncryptorAES256 encriptor(){
		return new EncryptorAES256();
	}

}
