package ar.com.pagatutti.clientapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter @Setter
public class AppConfig {
	
	@Value("${sms.api.twilio.send-message}")
	private String twilioSendMessageEndpoint;
	
}
