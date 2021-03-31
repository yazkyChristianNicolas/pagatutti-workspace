package  ar.com.pagatutti.apicommons.rest;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public interface RestClient {
	
	public <T> T doPost(String restServiceUrl, Object bodyBean, MediaType mediaType, Class<T> resultClass) throws Exception;
	
	public <T> T doPost(String restServiceUrl, HttpHeaders headers, Object bodyBean, MediaType mediaType, Class<T> resultClass) throws Exception;
	
	public <T> T doPost(String restServiceUrl, Object bodyBean, Map<String, String> pathParams, Map<String, String> queryParams, MediaType mediaType, Class<T> resultClass) throws Exception;
	
	public <T> T doPost(String restServiceUrl, Object bodyBean, Map<String, String> uriParams, Map<String, String> queryParams, HttpHeaders header, Class<T> resultClass) throws Exception;
	public <T> List<T> doPost(String restServiceUrl, Object bodyBean, MediaType mediaType, ParameterizedTypeReference<List<T>> resultClass) throws Exception;
	
	public <T> List<T> doPost(String restServiceUrl, Object bodyBean, Map<String, String> uriParams, Map<String, String> queryParams, MediaType mediaType,  ParameterizedTypeReference<List<T>> resultClass) throws Exception;
	
	public <T> List<T> doGet (String restServiceUrl, MediaType mediaType, ParameterizedTypeReference<List<T>> resultClass) throws Exception;
	public <T> List<T> doGet(String restServiceUrl, Map<String, String> queryParams,Map<String, String> params, MediaType mediaType, ParameterizedTypeReference<List<T>> resultClass);
	public <T> T doGet (String restServiceUrl, MediaType mediaType, Class<T> resultClass, String token) throws Exception;

	public <T> T doGet(String restServiceUrl, MediaType mediaType, Class<T> resultClass);
	
	public <T> T doGet(String restServiceUrl, Map<String, String> pathParams, HttpHeaders headers, Class<T> resultClass)throws Exception;
	
	public <T> T doGet(String restServiceUrl, Map<String, String> params, MediaType mediaType, Class<T> resultClass);
	
	public <T> T doGet(String restServiceUrl, Map<String, String> uriParams, Map<String, String> queryParams, HttpHeaders header,  Class<T> resultClass) throws Exception;
	public <T> T doGet(String restServiceUrl, Map<String, String> uriParams, Map<String, String> queryParams, Class<T> resultClass) throws Exception;
	
	public <T> List<T> doGet(String restServiceUrl, Map<String, String> params, MediaType mediaType, ParameterizedTypeReference<List<T>> resultClass);
	
	public <T> T doPut(String restServiceUrl, String authorization, Map<String, String> pathParams, Object bodyBean, MediaType mediaType, Class<T> resultClass) throws Exception;
	
	public <T> T doPut(String restServiceUrl, HttpHeaders headers, Map<String, String> pathParams, Object bodyBean, MediaType mediaType, Class<T> resultClass) throws Exception;

	public <T> T doDelete(String restServiceUrl, Map<String, String> pathParams, MediaType mediaType, Class<T> resultClass);
	
	public <T> T doDelete(String restServiceUrl, HttpHeaders headers, Map<String, String> pathParams, MediaType mediaType, Class<T> resultClass);

	public <T> List<T> doGet(String restServiceUrl, Map<String, String> uriParams,Map<String, String> queryParams, HttpHeaders headers, ParameterizedTypeReference<List<T>> resultClass);
	
}
