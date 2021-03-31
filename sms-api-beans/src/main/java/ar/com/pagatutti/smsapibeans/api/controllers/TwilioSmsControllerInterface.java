package ar.com.pagatutti.smsapibeans.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ar.com.pagatutti.smsapibeans.api.request.CheckCodeSmsRequest;
import ar.com.pagatutti.smsapibeans.api.request.VerifyPhoneNumberResponse;
import ar.com.pagatutti.smsapibeans.api.response.CheckCodeSmsResponse;


public interface TwilioSmsControllerInterface {
	
	@PostMapping("/verify/sms")
	public ResponseEntity<VerifyPhoneNumberResponse> sendSms(@RequestBody String phoneNumber) throws Exception;
	
	@PostMapping("/check/sms")
	public ResponseEntity<CheckCodeSmsResponse> checkSms(@RequestBody CheckCodeSmsRequest checkCodeSms) throws Exception;
}
