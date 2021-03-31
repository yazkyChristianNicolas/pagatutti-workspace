package ar.com.pagatutti.siisaclient.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class ExecutePolicyWithDetailsRequest {
	
	@JsonProperty("params")
	private ExecutePolicyWithDetailsRequestParams params;

}
