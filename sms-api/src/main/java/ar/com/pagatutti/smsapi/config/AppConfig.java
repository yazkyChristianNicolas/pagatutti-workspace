package ar.com.pagatutti.smsapi.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Component
@Configuration
@Getter @Setter
public class AppConfig {
	
	@Value("${twilio.accountSId}")
    private String twilioAccountSId;
	
	@Value("${twilio.authToken}")
    private String twilioAuthToken;
	
	/*@Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;*/
	
	@Value("${twilio.verify-service.id}")
    private String verifyServiceId;
	
	/*@Autowired
	EncryptorAES256 encriptor;
	
	@PostConstruct
	public void init(){
	     // init code goes here
		String accountSid = null;
		String authToken = null;
		try {
			accountSid = (this.twilioAccountSId.startsWith(BaseEncryptor.ENCRYPTED_PREFIX))? this.encriptor.decryptPhrase(this.twilioAccountSId):this.twilioAccountSId;
		    authToken = (this.twilioAuthToken.startsWith(BaseEncryptor.ENCRYPTED_PREFIX))? this.encriptor.decryptPhrase(this.twilioAuthToken):this.twilioAuthToken;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Twilio.init(accountSid, authToken);
	}*/
}
