package ar.com.pagatutti.siisaclient.service;

import java.beans.BeanInfo;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import ar.com.pagatutti.apicommons.rest.RestClientImpl;
import ar.com.pagatutti.siisaclient.api.SiisaAuthConfig;
import ar.com.pagatutti.siisaclient.api.request.AuthRequest;
import ar.com.pagatutti.siisaclient.api.request.CheckFaceRequest;
import ar.com.pagatutti.siisaclient.api.request.CheckIDRequest;
import ar.com.pagatutti.siisaclient.api.request.ExecutePolicyWithDetailsRequest;
import ar.com.pagatutti.siisaclient.api.response.AuthResponse;
import ar.com.pagatutti.siisaclient.api.response.CheckFaceResponse;
import ar.com.pagatutti.siisaclient.api.response.CheckIDResponse;
import ar.com.pagatutti.siisaclient.api.response.ExecutePolicyWithDetailsResponse;

@Service
public class SiisaService {
	
	private static String BEARER = "Bearer ";
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(SiisaService.class);

	/** The Siisa Auth config. */
	@Autowired
	private SiisaAuthConfig siisaAuthConfig;
	
	@Value("${siisa.api.url}")
	private String siisaEndpoint;
	
	@Value("${siisa.api.checkId.url}")
	private String siisaCheckIdEndpoint;

	/** The rest template. */
	@Autowired
	private RestClientImpl restClient;

	/** The token. */
	private String token;

	/** The token expiration date. */
	private LocalDateTime tokenExpirationDate;
	
	
	private void authenticate() throws Exception {
		LocalDateTime currentDate = LocalDateTime.now();
		logger.info("autenticando Siisa...");	
		if((null == tokenExpirationDate || currentDate.isAfter(tokenExpirationDate))) {
			
			
			AuthResponse authResponse = restClient.doPost(siisaEndpoint.concat("/Login/Token"), new AuthRequest(siisaAuthConfig.getClientId(), 
					siisaAuthConfig.getPinId(), 
					siisaAuthConfig.getPassword(),
					siisaAuthConfig.getExpiresIn()), MediaType.APPLICATION_JSON, AuthResponse.class);
			
			this.token = BEARER + authResponse.getAccessToken();
			
			TemporalAmount secondsToAdd = Duration.ofSeconds(Long.valueOf(authResponse.getExpiresIn()));
			tokenExpirationDate = LocalDateTime.now().plus(secondsToAdd);			
		}
		
		logger.info("autenticado fecha expiracion:" + tokenExpirationDate);	
	}
	
	public ExecutePolicyWithDetailsResponse executePolicyWithDetailsRequest(Long policyId, ExecutePolicyWithDetailsRequest request) throws Exception{
		logger.info("inicio POST hacia ExecutePolicyWithDetailsResponse");
		authenticate();
		logger.info(request.toString());
		try {
			ExecutePolicyWithDetailsResponse response = this.doPost(siisaEndpoint.concat("/Requests/ExecutePolicyWithDetail/" + policyId), request, ExecutePolicyWithDetailsResponse.class);
			logger.info("fin POST hacia Siisa [http status code: 200]");
			return response;
		}
		catch (HttpClientErrorException e) {			
			Integer httpStatus = e.getRawStatusCode();			
			logger.info("fin POST hacia Siisa [http status code: " + httpStatus + "]");
			logger.info("throws ->" + e.getClass().getSimpleName());
			throw e;
		}catch (Exception e) {
			logger.info("fin POST hacia Siisa [http status code: 500]");
			logger.info("throws ->" + e.getClass().getSimpleName());
			throw e;
		} 
	}
	
	public CheckIDResponse checkId(CheckIDRequest request) throws Exception{
		logger.info("inicio POST hacia checkId");
		authenticate();
		logger.info(request.toString());
		try {
			CheckIDResponse response = this.doPost(siisaCheckIdEndpoint.concat("/validarDNI"), request, CheckIDResponse.class);
			logger.info("fin POST hacia Siisa [http status code: 200]");
			return response;
		}
		catch (HttpClientErrorException e) {			
			Integer httpStatus = e.getRawStatusCode();			
			logger.info("fin POST hacia Siisa [http status code: " + httpStatus + "]");
			logger.info("throws ->" + e.getClass().getSimpleName());
			throw e;
		}catch (Exception e) {
			logger.info("fin POST hacia Siisa [http status code: 500]");
			logger.info("throws ->" + e.getClass().getSimpleName());
			throw e;
		} 
	}
	
	
	
	public CheckFaceResponse checkFace(CheckFaceRequest request) throws Exception{
		logger.info("inicio POST hacia checkFace");
		authenticate();
		logger.info(request.toString());
		try {
			CheckFaceResponse response = this.doPost(siisaCheckIdEndpoint.concat("/validarCaras"), request, CheckFaceResponse.class);
			logger.info("fin POST hacia Siisa [http status code: 200]");
			return response;
		}
		catch (HttpClientErrorException e) {			
			Integer httpStatus = e.getRawStatusCode();			
			logger.info("fin POST hacia Siisa [http status code: " + httpStatus + "]");
			logger.info("throws ->" + e.getClass().getSimpleName());
			throw e;
		}catch (Exception e) {
			logger.info("fin POST hacia Siisa [http status code: 500]");
			logger.info("throws ->" + e.getClass().getSimpleName());
			throw e;
		} 
	}
	
	
	
	
	
	
	private <T> T  doPost(String restServiceUrl, Object bodyBean,  Class<T> resultClass) throws Exception {
		MediaType mediaType = MediaType.APPLICATION_JSON;
		
		HttpHeaders httpHeaders = buildHttpHeaders(mediaType, this.token);

		return restClient.doPost(restServiceUrl, httpHeaders, bodyBean, mediaType, resultClass);
	}
	
	
	private HttpHeaders buildHttpHeaders (MediaType mediaType, String authorization){

		HttpHeaders headers = new HttpHeaders();

		if(null!= mediaType) {
			headers.setContentType(mediaType);
		}
		headers.add("Authorization", authorization);

		return headers;
	}

	private HttpEntity<Object> buildHttpEntity(HttpHeaders headers, MediaType mediaType, Object bodyBean) throws Exception{
		HttpEntity<Object> httpEntity = null;

		if(null != bodyBean) {

			if(null != mediaType && MediaType.APPLICATION_FORM_URLENCODED.equals(mediaType)){
				MultiValueMap<String, Object> entityToMap;

				entityToMap = introspect(bodyBean);
				httpEntity = new HttpEntity<Object>(entityToMap,headers);				
			}else{
				httpEntity = new HttpEntity<Object>(bodyBean,headers);
			}

		}else {
			httpEntity = new HttpEntity<Object>(headers);
		}


		return httpEntity;
	}

	private static MultiValueMap<String, Object>  introspect(Object obj) throws Exception {
		MultiValueMap<String, Object> result = new LinkedMultiValueMap<>();
		BeanInfo info = Introspector.getBeanInfo(obj.getClass());
		for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
			if(!"class".equals(pd.getName())){
				Method reader = pd.getReadMethod();
				if (reader != null)
					result.add(pd.getName(), reader.invoke(obj));
			}
		}
		return result;
	}
	
	
	
	
}
