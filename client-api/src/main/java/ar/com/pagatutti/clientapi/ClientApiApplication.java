package ar.com.pagatutti.clientapi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.com.pagatutti.apicommons.rest.RestClientImpl;

@SpringBootApplication
@ComponentScan({"ar.com.pagatutti.*","com.yazkychristian.*"})
public class ClientApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientApiApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
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
}
