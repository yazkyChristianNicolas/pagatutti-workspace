package ar.com.pagatutti.clientapi.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import ar.com.pagatutti.clientapi.beans.MessageSent;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class SmsManager {
	
	@Value("${sms.expires-in}")
	private Long minutesLiveSms;
	
	private Map<Long, MessageSent> sentSms = new HashMap<Long, MessageSent>();
	
	public void saveSentSms(Long clientId, MessageSent message) {
		TemporalAmount secondsToAdd = Duration.ofSeconds(minutesLiveSms);
		message.setExpiresIn(LocalDateTime.now().plus(secondsToAdd));
		this.sentSms.put(clientId, message);
	}
	
	
	public void checkSmsCode(Long clientId, String code) throws Exception{
		if(!this.sentSms.containsKey(clientId)) {
			throw new UsernameNotFoundException("Invalid clientId");
		}
		
		MessageSent messageSent = this.sentSms.get(clientId);
		if(!messageSent.getCode().equalsIgnoreCase(code)) {
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid Code");
		}
		
		if(!LocalDateTime.now().isBefore(messageSent.getExpiresIn())) {
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Code expired");
		}
		
		this.sentSms.remove(clientId);
	}
}
