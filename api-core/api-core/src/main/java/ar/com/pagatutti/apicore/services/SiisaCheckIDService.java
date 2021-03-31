package ar.com.pagatutti.apicore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import ar.com.pagatutti.apicommons.rest.RestClient;
import ar.com.pagatutti.apicore.config.AppConfig;
import ar.com.pagatutti.apicorebeans.api.request.CheckFaceRequest;
import ar.com.pagatutti.apicorebeans.api.request.CheckIDRequestDTO;
import ar.com.pagatutti.apicorebeans.api.response.CheckFaceResponse;
import ar.com.pagatutti.apicorebeans.api.response.CheckIDResponse;

@Service
public class SiisaCheckIDService {
	
	@Autowired
	private RestClient restClient;
	
	@Autowired
	private AppConfig appConfig;
	
	
	public CheckIDResponse checkId(CheckIDRequestDTO request) throws Exception {
		return restClient.doPost(appConfig.getSiisaIdUrl(), request, MediaType.APPLICATION_JSON, CheckIDResponse.class);
	}
	
	
	public CheckFaceResponse checkFace(CheckFaceRequest request) throws Exception{
		return restClient.doPost(appConfig.getSiisaIdUrl(), request, MediaType.APPLICATION_JSON, CheckFaceResponse.class);
	}
	
}
