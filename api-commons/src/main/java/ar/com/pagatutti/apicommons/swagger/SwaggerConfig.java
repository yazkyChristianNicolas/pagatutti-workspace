package  ar.com.pagatutti.apicommons.swagger;


import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Setter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@ConditionalOnClass(name = "springfox.documentation.spring.web.plugins.Docket")
@Configuration
@EnableSwagger2
@PropertySource("classpath:swagger.properties")
@ConfigurationProperties(prefix = "api.swagger.config")
@Setter
public class SwaggerConfig {                                    

	private String version;
	
	private String description;

	private String title;

	private String license;

	private String contactName;

	private String contactUrl;

	private String contactEmail;
	
	private String basePackage;

	@Bean
	public Docket api() { 
		return new Docket(DocumentationType.SWAGGER_2)  
				.select()                                  
				.apis(RequestHandlerSelectors.basePackage(this.basePackage))              
				.paths(PathSelectors.any())                         
				.build()
				.apiInfo(apiInfo());                                           
	}

	private ApiInfo apiInfo() {
		ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();

		Contact contact = new Contact(this.contactName, this.contactUrl, this.contactEmail);

		return apiInfoBuilder
				.version(this.version)
				.title(this.title)
				.description(this.description)
				.license(this.license)
				.contact(contact)
				.build();


	}
}
