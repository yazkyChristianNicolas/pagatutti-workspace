package ar.com.pagatutti.webappback.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.pagatutti.apicorebeans.api.request.StartOportunityRequest;
import ar.com.pagatutti.apicorebeans.api.request.ValidateTokenRequest;
import ar.com.pagatutti.apicorebeans.dto.CheckFaceRequestDTO;
import ar.com.pagatutti.apicorebeans.dto.CheckIDRequestDTO;
import ar.com.pagatutti.apicorebeans.dto.IndividualOpportunityDTO;
import ar.com.pagatutti.apicoreclient.clients.OpportunityClient;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/opportunity")
public class OpportunityController {
	
	private final OpportunityClient opportunityClient;

	@PostMapping(value="/new")
	public ResponseEntity<?> startLoan(@RequestBody StartOportunityRequest request) throws Exception {	
		ResponseEntity<IndividualOpportunityDTO> newOpportunity = opportunityClient.saveNewOpportunity(request);
		return ResponseEntity.ok(opportunityClient.evalIndividualRequest(newOpportunity.getBody().getId(), request.getFingerprint()));
	}
	

	@PostMapping(value="/{id}/verify/sms")
	public ResponseEntity<?> verifyPhoneNumberSms(@PathVariable("id") Long id,@RequestBody String phoneNumber) throws Exception {		
		return ResponseEntity.ok(opportunityClient.sendSms(id, phoneNumber));
	}
	
	@PostMapping(value="/{id}/check/sms")
	public ResponseEntity<?> checkSmsCode(@PathVariable("id") Long id, @RequestBody String code) throws Exception {		
		ResponseEntity<IndividualOpportunityDTO> opportunity = opportunityClient.checkSms(id, code);
		
		if(null != opportunity.getBody().getPhoneVerified() && opportunity.getBody().getPhoneVerified()) {
			opportunityClient.sendNotificationResult(id);
		}
		
		return ResponseEntity.ok(opportunity);
	}
	
	@PostMapping(value="/{id}/eval")
	public ResponseEntity<?> evalOpportunityRequest(@PathVariable("id") Long id, @RequestBody String siisaFingerprint) throws Exception {		
		return ResponseEntity.ok(opportunityClient.evalIndividualRequest(id,siisaFingerprint));
	}
	
	@PostMapping(value="/check/mail")
	public ResponseEntity<?> checkMail(@RequestBody String token) throws Exception {		
		return ResponseEntity.ok(opportunityClient.checkEmail(new ValidateTokenRequest(token)));
	}
	
	@PostMapping(value="/{id}/checkId")
	public ResponseEntity<?> checkId(@PathVariable("id") Long id, @RequestBody String imageBase64) throws Exception {		
		CheckIDRequestDTO request = new CheckIDRequestDTO();
		request.setImageB64(imageBase64);
		return ResponseEntity.ok(opportunityClient.validateId(id, request));
	}
	
	@PostMapping(value="/{id}/checkIdFace")
	public ResponseEntity<?> checkIdFace(@PathVariable("id") Long id, @RequestBody String imageBase64) throws Exception {		
		CheckFaceRequestDTO request = new CheckFaceRequestDTO();
		request.setFaceB64(imageBase64);
		return ResponseEntity.ok(opportunityClient.validateIdFace(id, request));
	}
	
}
