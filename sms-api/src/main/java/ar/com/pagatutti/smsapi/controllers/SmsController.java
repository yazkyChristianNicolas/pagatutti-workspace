package ar.com.pagatutti.smsapi.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.Verification.Channel;
import com.twilio.rest.verify.v2.service.VerificationCheck;

import ar.com.pagatutti.encriptor.BaseEncryptor;
import ar.com.pagatutti.encriptor.EncryptorAES256;
import ar.com.pagatutti.smsapi.config.AppConfig;
import ar.com.pagatutti.smsapibeans.api.controllers.TwilioSmsControllerInterface;
import ar.com.pagatutti.smsapibeans.api.request.CheckCodeSmsRequest;
import ar.com.pagatutti.smsapibeans.api.request.VerifyPhoneNumberResponse;
import ar.com.pagatutti.smsapibeans.api.response.CheckCodeSmsResponse;

@RestController()
@RequestMapping("/twilio")
public class SmsController implements TwilioSmsControllerInterface {
	
	private static final String ARG_CODE = "+54";
	
	private static final Logger logger = LoggerFactory.getLogger(SmsController.class);

	@Autowired
	EncryptorAES256 encriptor;
	
	@Autowired()
	private AppConfig appConfig;

	@PostMapping("/verify/sms")
	public ResponseEntity<VerifyPhoneNumberResponse> sendSms(@RequestBody String phoneNumber) throws Exception{
		 initTwilio();
		 phoneNumber = (phoneNumber.startsWith(ARG_CODE))? phoneNumber : ARG_CODE.concat(phoneNumber);
		 logger.info("Phonenumber " + phoneNumber);
		 String serviceId = (appConfig.getVerifyServiceId().startsWith(BaseEncryptor.ENCRYPTED_PREFIX))?  this.encriptor.decryptPhrase(appConfig.getVerifyServiceId()):appConfig.getVerifyServiceId();
		 Verification verification = Verification.creator(
				 	serviceId,
				 	phoneNumber,
	                Channel.SMS.toString())
				 .setLocale("es")
	            .create();
		
		logger.info(verification.toString());

		return ResponseEntity.ok(new VerifyPhoneNumberResponse(verification.getSid(), verification.getStatus(), verification.getTo(), verification.getValid()));
	}
	
	@PostMapping("/check/sms")
	public ResponseEntity<CheckCodeSmsResponse> checkSms(@RequestBody CheckCodeSmsRequest checkCodeSms) throws Exception{
	      initTwilio();
	      String phoneNumber = checkCodeSms.getPhoneNumber();
	      phoneNumber = (phoneNumber.startsWith(ARG_CODE))? phoneNumber : ARG_CODE.concat(phoneNumber);
		  String serviceId = (appConfig.getVerifyServiceId().startsWith(BaseEncryptor.ENCRYPTED_PREFIX))?  this.encriptor.decryptPhrase(appConfig.getVerifyServiceId()):appConfig.getVerifyServiceId();
		
		  /*VerificationCheck verificationCheck = VerificationCheck.creator(
				  serviceId,
				  checkCodeSms.getCode())
		        .setVerificationSid(checkCodeSms.getVerificationSid()).create();*/
		  
		  VerificationCheck verificationCheck = VerificationCheck.creator(
				  serviceId,
				  checkCodeSms.getCode()).setTo(phoneNumber).create();
		
		return ResponseEntity.ok(new CheckCodeSmsResponse(verificationCheck.getStatus(), verificationCheck.getValid()));
	}
	
	
	private  void initTwilio(){
	     // init code goes here
		String accountSid = appConfig.getTwilioAccountSId();
		String authToken = appConfig.getTwilioAuthToken();
		try {
			accountSid = (accountSid.startsWith(BaseEncryptor.ENCRYPTED_PREFIX))? this.encriptor.decryptPhrase(accountSid):accountSid;
		    authToken = (authToken.startsWith(BaseEncryptor.ENCRYPTED_PREFIX))? this.encriptor.decryptPhrase(authToken):authToken;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Twilio.init(accountSid, authToken);
	}
	
}
