package ar.com.pagatutti.mailapi.controllers;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.pagatutti.apicommons.api.MessageResponse;
import ar.com.pagatutti.mailapi.beans.SendMailRequest;
import ar.com.pagatutti.mailapi.services.EmailService;


@RestController
@RequestMapping("/mail")
public class MailController {
	
	@Autowired
	private EmailService emailService;
	
    private static final Logger logger = LogManager.getLogger(MailController.class.getName());
	
	@PostMapping()
	public ResponseEntity<?> sendMail(@Valid @RequestBody SendMailRequest mailRequest) throws Exception{
		logger.info("Send Email");
		logger.info(mailRequest.toString());
		emailService.sendMail(mailRequest);
		return ResponseEntity.ok(new MessageResponse("Mail enviado con éxito"));
	}
}
 