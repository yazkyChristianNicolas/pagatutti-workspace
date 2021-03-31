package ar.com.pagatutti.apicorebeans.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown=true)
@NoArgsConstructor
@Data
public class CheckFaceRequestDTO {
	@JsonProperty("identifier")
	private String identifier;
	@JsonProperty("faceURL")
	private String faceURL;
	@JsonProperty("faceB64")
	private String faceB64;
}
