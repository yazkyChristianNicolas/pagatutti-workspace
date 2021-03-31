package ar.com.pagatutti.clientapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.pagatutti.apicommons.api.ActiveAccountCodeRequest;
import ar.com.pagatutti.apicommons.api.MessageResponse;
import ar.com.pagatutti.apicommons.api.SendSmsCodeRequest;
import ar.com.pagatutti.clientapi.api.CreateClientRequest;
import ar.com.pagatutti.clientapi.services.ClientService;

@RestController()
@RequestMapping("/client")
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	
	@PostMapping(path="/create")
	public ResponseEntity<?> createClient(@RequestBody CreateClientRequest createClientRequest) throws Exception{
		return ResponseEntity.ok(clientService.saveClient(createClientRequest)); 
	}

	@PostMapping(path="/{id}/sms/generateCode")
	public ResponseEntity<?> generateCode(@PathVariable("id") Long id, @RequestBody SendSmsCodeRequest sendSmsCodeRequest) throws Exception{
		clientService.generateCode(id, sendSmsCodeRequest.getTel());
		//dar de alta  el usuario 
		return ResponseEntity.ok(new MessageResponse("Mensaje enviado con exito"));
	}
	
	@PostMapping(path="/{id}/sms/active")
	public ResponseEntity<?> activeClientSmsCode(@PathVariable("id") Long id, @RequestBody ActiveAccountCodeRequest activeAccountCodeRequest) throws Exception{
		clientService.smsCodeValidation(id, activeAccountCodeRequest.getCode());
		return ResponseEntity.ok(new MessageResponse("Codigo validado con exito"));
	}
	
}
