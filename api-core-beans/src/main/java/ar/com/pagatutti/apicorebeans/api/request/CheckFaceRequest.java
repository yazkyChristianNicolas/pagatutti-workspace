package ar.com.pagatutti.apicorebeans.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown=true)
@Data
public class CheckFaceRequest {
	@JsonProperty("identifier")
	private String identifier;
	@JsonProperty("faceURL")
	private String faceURL;
	@JsonProperty("faceB64")
	private String faceB64;
}
