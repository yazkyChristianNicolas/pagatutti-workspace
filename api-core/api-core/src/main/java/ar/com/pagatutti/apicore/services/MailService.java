package ar.com.pagatutti.apicore.services;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import ar.com.pagatutti.apicommons.api.MessageResponse;
import ar.com.pagatutti.apicommons.rest.RestClientImpl;
import ar.com.pagatutti.apicore.beans.SendMailRequest;
import ar.com.pagatutti.apicore.config.AppConfig;
import ar.com.pagatutti.apicore.entities.IndividualOpportunity;

@Service
public class MailService {
	
	private static final Logger logger = LoggerFactory.getLogger(MailService.class);

	private static final String WELCOME_TEMPLATE = "welcome.ftl";
	
	private static final String DETAIL_TEMPLATE = "detail.ftl";

	
	@Autowired
	private AppConfig appConfig;

	
	@Autowired
	private RestClientImpl restClient;
	
	public MessageResponse sendEmail(IndividualOpportunity opportunity, String token) throws Exception{
		SendMailRequest request = new SendMailRequest();
		request.setFrom("noreply@encodemmerce.com");
		request.setTemplate(WELCOME_TEMPLATE);
		request.setSubject("Solicitud Prestamo");
		request.setTo(opportunity.getClient().getEmail().trim());
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("nombre", opportunity.getClient().getName());
		data.put("validationUrl", appConfig.getMailValidationUrl().concat(token));
		request.setData(data);
		
		return this.restClient.doPost(appConfig.getMailApiEndpoint().concat("/mail"), request, MediaType.APPLICATION_JSON, MessageResponse.class);
	}
	
	public MessageResponse sendLoanRequestDetailEmail(IndividualOpportunity opportunity) throws Exception{
		SendMailRequest request = new SendMailRequest();
		request.setFrom("noreply@encodemmerce.com");
		request.setTemplate(DETAIL_TEMPLATE);
		request.setSubject("Solicitud Prestamo");
		request.setTo(opportunity.getClient().getEmail().trim());
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("loanRequest", opportunity);
		request.setData(data);
		
		return this.restClient.doPost(appConfig.getMailApiEndpoint().concat("/mail"), request, MediaType.APPLICATION_JSON, MessageResponse.class);
	}
	
	
}
