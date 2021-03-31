package  ar.com.pagatutti.apicommons.rest;


import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class RestClientImpl implements RestClient {

	protected RestTemplate restTemplate;

	
	public RestClientImpl(){

		this.restTemplate = buildRestTemplate(null, null);
	}
	
	public RestClientImpl(String proxyHost, int proxyPort){

		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

		Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
		requestFactory.setProxy(proxy);

		this.restTemplate = buildRestTemplate(requestFactory, null);		

	}
	
	public RestClientImpl(MappingJackson2HttpMessageConverter messageConverter){

		this.restTemplate = buildRestTemplate(null, messageConverter);
	}

	public RestClientImpl(MappingJackson2HttpMessageConverter messageConverter, String proxyHost, int proxyPort){

		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

		Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
		requestFactory.setProxy(proxy);

		this.restTemplate = buildRestTemplate(requestFactory, messageConverter);		

	}

	private RestTemplate buildRestTemplate(ClientHttpRequestFactory requestFactory, MappingJackson2HttpMessageConverter messageConverter){

		RestTemplate restTemplate;

		if(requestFactory != null){
			restTemplate = new RestTemplate(requestFactory);
		}else{
			restTemplate = new RestTemplate();
		}

		if(null != messageConverter) {
			
			//Verificar si ya existe un converter del tipo enviado por parametro
			AbstractHttpMessageConverter<?> existentConverter = CollectionUtils.findValueOfType(restTemplate.getMessageConverters(), messageConverter.getClass());

			if(existentConverter != null){
				//Remover converter existente para reemplazarlo por el recibido por parametro
				restTemplate.getMessageConverters().remove(existentConverter);
			}

			restTemplate.getMessageConverters().add(messageConverter);
		}
		
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

		return restTemplate;


	}

	private HttpHeaders buildHttpHeaders (MediaType mediaType, String authorization){

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);		

		if (!StringUtils.isEmpty(authorization)){
			headers.add("Authorization", authorization);
		}

		return headers;
	}

	private HttpHeaders buildHttpHeaders (MediaType mediaType){

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);	

		return headers;
	}

	private HttpEntity<Object> buildHttpEntity(HttpHeaders headers, MediaType mediaType, Object bodyBean) throws Exception{
		HttpEntity<Object> httpEntity = null;


		if(MediaType.APPLICATION_FORM_URLENCODED.equals(mediaType)){
			MultiValueMap<String, Object> entityToMap;

			entityToMap = this.introspect(bodyBean);

			httpEntity = new HttpEntity<>(entityToMap,headers);
		}else{
			httpEntity = new HttpEntity<>(bodyBean,headers);
		}

		return httpEntity;
	}


	public <T> T doPost (String restServiceUrl, Object bodyBean, MediaType mediaType, Class<T> resultClass) throws Exception  {
		HttpHeaders headers = buildHttpHeaders(mediaType, null);

		HttpEntity<Object> entity = buildHttpEntity(headers, mediaType, bodyBean);

		return this.restTemplate.postForObject(restServiceUrl, entity, resultClass);
	}

	public <T> T doPost (String restServiceUrl, HttpHeaders headers , Object bodyBean, MediaType mediaType, Class<T> resultClass) throws Exception  {

		headers.setContentType(mediaType);

		HttpEntity<Object> entity = buildHttpEntity(headers, mediaType, bodyBean);

		return this.restTemplate.postForObject(restServiceUrl, entity, resultClass);
	}


	public <T> List<T> doPost (String restServiceUrl, Object bodyBean, MediaType mediaType, ParameterizedTypeReference<List<T>> resultClass) throws Exception {
		HttpHeaders headers = buildHttpHeaders(mediaType, null);

		HttpEntity<Object> entity = buildHttpEntity(headers, mediaType, bodyBean);

		ResponseEntity<List<T>> response = this.restTemplate.exchange(restServiceUrl, HttpMethod.POST, entity, resultClass);

		return response.getBody();
	}


	public <T> List<T> doGet (String restServiceUrl, MediaType mediaType, ParameterizedTypeReference<List<T>> resultClass) {

		HttpHeaders headers = buildHttpHeaders(mediaType, null);

		HttpEntity<Object> entity = new HttpEntity<>(headers);

		ResponseEntity<List<T>> response = this.restTemplate.exchange(restServiceUrl , HttpMethod.GET, entity, resultClass);

		return response.getBody();
	}


	@Override
	public <T> T doGet (String restServiceUrl, MediaType mediaType, Class<T> resultClass, String token) {

		HttpHeaders headers = buildHttpHeaders(mediaType, token);

		HttpEntity<Object> entity = new HttpEntity<>(headers);

		ResponseEntity<T> response = this.restTemplate.exchange(restServiceUrl , HttpMethod.GET, entity, resultClass);

		return response.getBody();
	}


	@Override
	public <T> T doGet (String restServiceUrl, MediaType mediaType, Class<T> resultClass) {

		HttpHeaders headers = buildHttpHeaders(mediaType);

		HttpEntity<Object> entity = new HttpEntity<>(headers);

		ResponseEntity<T> response = this.restTemplate.exchange(restServiceUrl , HttpMethod.GET, entity, resultClass);

		return response.getBody();
	}

	@Override
	public <T> T doGet(String restServiceUrl, Map<String, String> pathParams, MediaType mediaType, Class<T> resultClass) {

		RestTemplate restTemplate = new RestTemplate();

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(restServiceUrl);
		URI uri = builder.buildAndExpand(pathParams).toUri();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);	

		HttpEntity<?> entity = new HttpEntity<>(headers);		

		ResponseEntity<T> response = restTemplate.exchange(uri, HttpMethod.GET, entity, resultClass);

		if(null != response){
			return response.getBody();
		}

		return null;
	}

	@Override
	public <T> T doGet(String restServiceUrl, Map<String, String> pathParams, HttpHeaders headers, Class<T> resultClass) {


		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(restServiceUrl);
		URI uri = builder.buildAndExpand(pathParams).toUri();

		HttpEntity<?> entity = new HttpEntity<>(headers);		

		ResponseEntity<T> response = restTemplate.exchange(uri, HttpMethod.GET, entity, resultClass);

		if(null != response){
			return response.getBody();
		}

		return null;
	}
	/**
	 * Do get.
	 *
	 * @param <T> the generic type
	 * @param keyRestURL the key rest URL
	 * @param params the params
	 * @param resultClass the result class
	 * @return the list
	 */
	@Override
	public <T> List<T> doGet(String restServiceUrl, Map<String, String> params, MediaType mediaType, ParameterizedTypeReference<List<T>> resultClass){

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);	

		HttpEntity<?> entity = new HttpEntity<>(headers);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(restServiceUrl);
		URI uri = builder.buildAndExpand(params).toUri();	

		ResponseEntity<List<T>> response = restTemplate.exchange(uri, HttpMethod.GET, entity, resultClass);

		if(null != response){
			return response.getBody();
		}

		return null;
	}

	@Override
	public <T> T doGet(String restServiceUrl, Map<String, String> uriParams, Map<String, String> queryParams, HttpHeaders headers, Class<T> resultClass) throws Exception {
		
		MultiValueMap<String, String> qParams = new LinkedMultiValueMap<>();

		queryParams.forEach((key,value) -> {
			qParams.add(key, value);
		});

		// Query parameters
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(restServiceUrl)
				// Add query parameter
				.queryParams(qParams);

		URI uri;

		if(CollectionUtils.isEmpty(uriParams)) {
			uri = builder.build().toUri();
		}else {
			uri = builder.buildAndExpand(uriParams).toUri();
		}
		
		//headers.setContentType(mediaType);	

		HttpEntity<?> entity = new HttpEntity<>(headers);		
		
		ResponseEntity<T> response = restTemplate.exchange(uri, HttpMethod.GET, entity, resultClass);
		
		if(null != response){
			return response.getBody();
		}
		
		return null;
	}
	
	@Override
	public <T> T doGet(String restServiceUrl, Map<String, String> uriParams, Map<String, String> queryParams, Class<T> resultClass) throws Exception {



		MultiValueMap<String, String> qParams = new LinkedMultiValueMap<>();

		queryParams.forEach((key,value) -> {
			qParams.add(key, value);
		});

		// Query parameters
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(restServiceUrl)
				// Add query parameter
				.queryParams(qParams);

		URI uri;

		if(CollectionUtils.isEmpty(uriParams)) {
			uri = builder.build().toUri();
		}else {
			uri = builder.buildAndExpand(uriParams).toUri();
		}

		return restTemplate.getForObject(uri, resultClass);		
	}

	@Override
	public <T> T doPost(String restServiceUrl, Object bodyBean, Map<String, String> uriParams,
			Map<String, String> queryParams, MediaType mediaType, Class<T> resultClass) throws Exception {

		if(CollectionUtils.isEmpty(uriParams) && CollectionUtils.isEmpty(queryParams)) {
			return this.doPost(restServiceUrl, bodyBean, mediaType, resultClass);
		}

		HttpHeaders headers = buildHttpHeaders(mediaType, null);

		HttpEntity<Object> entity = buildHttpEntity(headers, mediaType, bodyBean);

		MultiValueMap<String, String> qParams = new LinkedMultiValueMap<>();
		
		 if(!CollectionUtils.isEmpty(queryParams)) {
				queryParams.forEach((key,value) -> {
					qParams.add(key, value);
				});;
		 }

		// Query parameters
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(restServiceUrl)
				// Add query parameter
				.queryParams(qParams);

		URI uri;

		if(CollectionUtils.isEmpty(uriParams)) {
			uri = builder.build().toUri();
		}else {
			uri = builder.buildAndExpand(uriParams).toUri();
		}


		ResponseEntity<T> response = restTemplate.exchange(uri, HttpMethod.POST, entity, resultClass);

		T responseBody = response != null? response.getBody() : null;

		return responseBody;

	}	

	@Override
	public <T> T doPost(String restServiceUrl, Object bodyBean, Map<String, String> pathParams,Map<String, String> queryParams, 
			HttpHeaders header, Class<T> resultClass) throws Exception {

		
		HttpEntity<Object> entity = new HttpEntity<>(bodyBean,header);
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(restServiceUrl);
		
		 if(!CollectionUtils.isEmpty(queryParams)) {
			 MultiValueMap<String, String> qParams = new LinkedMultiValueMap<>();
				queryParams.forEach((key,value) -> {
				qParams.add(key, value);
			});
				builder.queryParams(qParams)	;
		 }
		
		URI uri;

		if(CollectionUtils.isEmpty(pathParams)) {
			uri = builder.build().toUri();
		}else {
			uri = builder.buildAndExpand(pathParams).toUri();
		}


		ResponseEntity<T> response = restTemplate.exchange(uri, HttpMethod.POST, entity, resultClass);

		T responseBody = response != null? response.getBody() : null;

		return responseBody;

	}
	
	public <T> T doPut(String restServiceUrl, String authorization, Map<String, String> pathParams, Object bodyBean, MediaType mediaType, Class<T> resultClass) throws Exception {

		HttpHeaders httpHeaders = buildHttpHeaders(mediaType, authorization);

		HttpEntity<Object> entity = buildHttpEntity(httpHeaders, mediaType, bodyBean);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(restServiceUrl);
		URI uri = builder.buildAndExpand(pathParams).toUri();

		ResponseEntity<T> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, resultClass);


		if(null != response){
			return response.getBody();
		}

		return null;

	}

	public <T> T doPut(String restServiceUrl, HttpHeaders httpHeaders, Map<String, String> pathParams, Object bodyBean, MediaType mediaType, Class<T> resultClass) throws Exception {

		httpHeaders.setContentType(mediaType);

		HttpEntity<Object> entity = buildHttpEntity(httpHeaders, mediaType, bodyBean);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(restServiceUrl);
		URI uri = builder.buildAndExpand(pathParams).toUri();

		ResponseEntity<T> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, resultClass);


		if(null != response){
			return response.getBody();
		}

		return null;

	}

	@Override
	public <T> T doDelete(String restServiceUrl, Map<String, String> pathParams, MediaType mediaType, Class<T> resultClass) {

		RestTemplate restTemplate = new RestTemplate();

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(restServiceUrl);
		URI uri = builder.buildAndExpand(pathParams).toUri();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);	

		HttpEntity<?> entity = new HttpEntity<>(headers);		

		ResponseEntity<T> response = restTemplate.exchange(uri, HttpMethod.DELETE, entity, resultClass);

		if(null != response){
			return response.getBody();
		}

		return null;
	}

	@Override
	public <T> T doDelete(String restServiceUrl, HttpHeaders headers, Map<String, String> pathParams, MediaType mediaType, Class<T> resultClass) {

		RestTemplate restTemplate = new RestTemplate();

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(restServiceUrl);
		URI uri = builder.buildAndExpand(pathParams).toUri();

		headers.setContentType(mediaType);	

		HttpEntity<?> entity = new HttpEntity<>(headers);		

		ResponseEntity<T> response = restTemplate.exchange(uri, HttpMethod.DELETE, entity, resultClass);

		if(null != response){
			return response.getBody();
		}

		return null;
	}	
	
	
	
	@Override
	public <T> List<T> doPost(String restServiceUrl, Object bodyBean, Map<String, String> uriParams,
			Map<String, String> queryParams, MediaType mediaType,  ParameterizedTypeReference<List<T>> resultClass) throws Exception {

		if(CollectionUtils.isEmpty(uriParams) && CollectionUtils.isEmpty(queryParams)) {
			return this.doPost(restServiceUrl, bodyBean, mediaType, resultClass);
		}

		HttpHeaders headers = buildHttpHeaders(mediaType, null);

		HttpEntity<Object> entity = buildHttpEntity(headers, mediaType, bodyBean);

		MultiValueMap<String, String> qParams = new LinkedMultiValueMap<>();

		queryParams.forEach((key,value) -> {
			qParams.add(key, value);
		});

		// Query parameters
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(restServiceUrl)
				// Add query parameter
				.queryParams(qParams);

		URI uri;

		if(CollectionUtils.isEmpty(uriParams)) {
			uri = builder.build().toUri();
		}else {
			uri = builder.buildAndExpand(uriParams).toUri();
		}


		ResponseEntity<List<T>>  response = restTemplate.exchange(uri, HttpMethod.POST, entity, resultClass);

		return response.getBody();
	}

	@Override
	public <T> List<T> doGet(String restServiceUrl, Map<String, String> uriParams, Map<String, String> queryParams, HttpHeaders headers,ParameterizedTypeReference<List<T>> resultClass) {
		
		MultiValueMap<String, String> qParams = new LinkedMultiValueMap<>();

		queryParams.forEach((key,value) -> {
			qParams.add(key, value);
		});

		// Query parameters
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(restServiceUrl)
				// Add query parameter
				.queryParams(qParams);

		URI uri;

		if(CollectionUtils.isEmpty(uriParams)) {
			uri = builder.build().toUri();
		}else {
			uri = builder.buildAndExpand(uriParams).toUri();
		}
		
		//headers.setContentType(mediaType);	

		HttpEntity<?> entity = new HttpEntity<>(headers);		
		
		ResponseEntity<List<T>> response = restTemplate.exchange(uri, HttpMethod.GET, entity, resultClass);
		
		if(null != response){
			return response.getBody();
		}
		
		return null;
	}
	@Override
	public <T> List<T> doGet(String restServiceUrl, Map<String, String> queryParams,Map<String, String> params, MediaType mediaType, ParameterizedTypeReference<List<T>> resultClass){

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);	
		MultiValueMap<String, String> qParams = new LinkedMultiValueMap<>();

		queryParams.forEach((key,value) -> {
			qParams.add(key, value);
		});
		
		HttpEntity<?> entity = new HttpEntity<>(headers);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(restServiceUrl).queryParams(qParams);
		
		URI uri;

		if(CollectionUtils.isEmpty(params)) {
			uri = builder.build().toUri();
		}else {
			uri = builder.buildAndExpand(params).toUri();
		}

		ResponseEntity<List<T>> response = restTemplate.exchange(uri, HttpMethod.GET, entity, resultClass);

		if(null != response){
			return response.getBody();
		}

		return null;
	}
	
	
	private  MultiValueMap<String, Object>  introspect(Object obj) throws Exception {
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

