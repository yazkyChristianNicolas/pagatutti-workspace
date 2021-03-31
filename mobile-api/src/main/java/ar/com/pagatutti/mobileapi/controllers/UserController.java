package ar.com.pagatutti.mobileapi.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.pagatutti.apicommons.api.ActiveAccountCodeRequest;
import ar.com.pagatutti.apicommons.api.SendSmsCodeRequest;
import ar.com.pagatutti.apicommons.beans.AuthenticatedUserBean;
import ar.com.pagatutti.apicommons.utils.JwtTokenUtils;

import ar.com.pagatutti.mobileapi.api.CreateClientRequest;
import ar.com.pagatutti.mobileapi.services.ClientService;


@RestController()
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private ClientService clientService;

	@PostMapping("/createAccount")
	public ResponseEntity<?> createAccount(@RequestBody CreateClientRequest clientRequest, HttpServletRequest request) throws Exception{
		AuthenticatedUserBean authenticatedUser = ((AuthenticatedUserBean) request.getAttribute(JwtTokenUtils.AUTHENTICATED_USER));
		clientRequest.setExternalId(authenticatedUser.getId());
		return ResponseEntity.ok(clientService.createClient(clientRequest));
	} 
	
	@PostMapping(path="/sms/generateCode")
	public ResponseEntity<?> generateCode(@RequestBody SendSmsCodeRequest sendSmsCodeRequest,  HttpServletRequest request) throws Exception{
		AuthenticatedUserBean authenticatedUser = ((AuthenticatedUserBean) request.getAttribute(JwtTokenUtils.AUTHENTICATED_USER));
		return ResponseEntity.ok(clientService.generateSmsCode(authenticatedUser.getId(), sendSmsCodeRequest));
	}
	
	@PostMapping(path="/sms/active")
	public ResponseEntity<?> activeClientSmsCode(@RequestBody ActiveAccountCodeRequest activeAccountCodeRequest, HttpServletRequest request) throws Exception{
		AuthenticatedUserBean authenticatedUser = ((AuthenticatedUserBean) request.getAttribute(JwtTokenUtils.AUTHENTICATED_USER));
		return ResponseEntity.ok(clientService.activeClientSmsCode(authenticatedUser.getId(), activeAccountCodeRequest));
	}
	
}
