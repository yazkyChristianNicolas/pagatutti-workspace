package ar.com.pagatutti.apicorebeans.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown=true)
@Data
public class CheckFaceResponse {
	@JsonProperty("identifier")
	private String identifier;
	@JsonProperty("faceURL")
	private String faceURL;
}
