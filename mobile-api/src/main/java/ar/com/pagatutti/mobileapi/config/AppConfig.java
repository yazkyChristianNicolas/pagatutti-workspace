package ar.com.pagatutti.mobileapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter @Setter
public class AppConfig {
	
	@Value("${api.client.create}")
	private String createClientEndpoint;
	
	@Value("${api.client.sms.active-client}")
	private String activeClientSmsEndpoint;
	
	@Value("${api.client.sms.generate-code}")
	private String sendSmsCodeEndpoint;
	
}
