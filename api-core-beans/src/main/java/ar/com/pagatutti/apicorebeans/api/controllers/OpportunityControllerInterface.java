package ar.com.pagatutti.apicorebeans.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ar.com.pagatutti.apicorebeans.api.request.StartOportunityRequest;
import ar.com.pagatutti.apicorebeans.api.request.ValidateTokenRequest;
import ar.com.pagatutti.apicorebeans.dto.CheckFaceRequestDTO;
import ar.com.pagatutti.apicorebeans.dto.CheckIDRequestDTO;
import ar.com.pagatutti.apicorebeans.dto.IndividualOpportunityDTO;

public interface OpportunityControllerInterface {
	
	@PostMapping()
	public ResponseEntity<IndividualOpportunityDTO>  saveNewOpportunity(@RequestBody StartOportunityRequest request) throws Exception;
	
	@PostMapping("/{id}/verify/sms")
	public ResponseEntity<IndividualOpportunityDTO> sendSms(@PathVariable("id") Long id, @RequestBody String phoneNumber) throws Exception;
	
	@PostMapping("/{id}/check/sms")
	public ResponseEntity<IndividualOpportunityDTO> checkSms(@PathVariable("id") Long id, @RequestBody String code) throws Exception;
	
	@PostMapping("/{id}/eval")
	public ResponseEntity<IndividualOpportunityDTO> evalIndividualRequest(@PathVariable("id") Long id, @RequestBody String siisaFingerprint) throws Exception;

	@GetMapping("/{id}/notification/result")
	public ResponseEntity<IndividualOpportunityDTO> sendNotificationResult(@PathVariable("id") Long id) throws Exception;
	
	@PostMapping("/check/mail")
	public ResponseEntity<IndividualOpportunityDTO> checkEmail(@RequestBody ValidateTokenRequest request) throws Exception;
	
	@PostMapping("/{id}/validate/id")
	public ResponseEntity<IndividualOpportunityDTO> validateId(@PathVariable("id") Long id, @RequestBody CheckIDRequestDTO request) throws Exception;
	
	@PostMapping("/{id}/validate/id/face")
	public ResponseEntity<IndividualOpportunityDTO> validateIdFace(@PathVariable("id") Long id, @RequestBody CheckFaceRequestDTO request) throws Exception;
	
	@GetMapping("/{id}/mail/loanDetail")
	public ResponseEntity<IndividualOpportunityDTO> sendLoanDetailEmail(@PathVariable("id") Long id) throws Exception;
	
}
