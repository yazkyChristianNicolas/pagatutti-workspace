package ar.com.pagatutti.clientapi.services;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import ar.com.pagatutti.apicommons.rest.RestClient;
import ar.com.pagatutti.clientapi.api.CreateClientRequest;
import ar.com.pagatutti.clientapi.api.SmsRequest;
import ar.com.pagatutti.clientapi.beans.MessageSent;
import ar.com.pagatutti.clientapi.config.AppConfig;
import ar.com.pagatutti.clientapi.entities.ClientEntity;
import ar.com.pagatutti.clientapi.repositories.ClientRepository;

@Service
public class ClientService {
	
	private static final Logger logger = LoggerFactory.getLogger(ClientService.class);
	

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private SmsManager smsManager;
	
	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private RestClient restClient;
	
	public ClientEntity saveClient(CreateClientRequest createClientRequest) throws Exception {
		ClientEntity newClient = modelMapper.map(createClientRequest, ClientEntity.class);
		newClient.setActiveDate((newClient.getActive())? new Date(): null);
		return this.clientRepository.save(newClient); 
	}
	
	public ClientEntity findByExternalId(Long externarId) {
		return  this.clientRepository.findByExternalId(externarId);
	}
	
	public void generateCode(Long externarId, String cel) throws Exception {
		MessageSent messageSent = new MessageSent();
		int randomCode = generateRandomDigits(4);
		messageSent.setCode(String.valueOf(randomCode));
		messageSent.setCel(cel);
		String messageCode = "Tu codigo para verificar la cuenta es: " + messageSent.getCode();
		SmsRequest smsRequest = new SmsRequest(cel, messageCode);
		List<String> sentMessage = null;
		try {
			sentMessage = this.restClient.doPost(appConfig.getTwilioSendMessageEndpoint(), smsRequest, MediaType.APPLICATION_JSON, new ParameterizedTypeReference<List<String>>(){});
			messageSent.setSid(sentMessage.get(0));
			smsManager.saveSentSms(externarId, messageSent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getLocalizedMessage());
			e.printStackTrace();
			throw  new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	public void smsCodeValidation(Long externarId, String code) throws Exception{
		smsManager.checkSmsCode(externarId, code);
	}
	
	
	// Generates a random int with n digits
	private int generateRandomDigits(int n) {
	    int m = (int) Math.pow(10, n - 1);
	    return m + new Random().nextInt(9 * m);
	}
}
