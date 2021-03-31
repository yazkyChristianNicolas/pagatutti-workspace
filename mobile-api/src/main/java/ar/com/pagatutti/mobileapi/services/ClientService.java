package ar.com.pagatutti.mobileapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import com.google.common.collect.ImmutableMap;
import ar.com.pagatutti.apicommons.api.ActiveAccountCodeRequest;
import ar.com.pagatutti.apicommons.api.MessageResponse;
import ar.com.pagatutti.apicommons.api.SendSmsCodeRequest;
import ar.com.pagatutti.apicommons.rest.RestClient;
import ar.com.pagatutti.mobileapi.api.CreateClientRequest;
import ar.com.pagatutti.mobileapi.api.CreateClientResponse;
import ar.com.pagatutti.mobileapi.config.AppConfig;

@Service
public class ClientService {

	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private RestClient restClient;
	
	public CreateClientResponse createClient(CreateClientRequest clientRequest) throws Exception{
		return this.restClient.doPost(appConfig.getCreateClientEndpoint(), new HttpHeaders(), clientRequest, MediaType.APPLICATION_JSON, CreateClientResponse.class);
	}
	
	public MessageResponse generateSmsCode(Long externalId, SendSmsCodeRequest sendSmsCodeRequest) throws Exception{
		return this.restClient.doPost(appConfig.getSendSmsCodeEndpoint(), sendSmsCodeRequest, ImmutableMap.of("id", String.valueOf(externalId)), null, MediaType.APPLICATION_JSON,  MessageResponse.class);
	}
	
	public MessageResponse activeClientSmsCode(Long externalId, ActiveAccountCodeRequest activeAccountCodeRequest) throws Exception{
		return this.restClient.doPost(appConfig.getActiveClientSmsEndpoint(), activeAccountCodeRequest, ImmutableMap.of("id", String.valueOf(externalId)), null, MediaType.APPLICATION_JSON, MessageResponse.class);
	}
	
}
