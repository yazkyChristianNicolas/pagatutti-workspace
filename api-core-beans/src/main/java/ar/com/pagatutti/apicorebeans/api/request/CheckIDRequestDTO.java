package ar.com.pagatutti.apicorebeans.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@JsonIgnoreProperties(ignoreUnknown=true)
@Data
public class CheckIDRequestDTO {
	@JsonProperty("imageURL")
	private String imageURL;
	@JsonProperty("imageB64")
	private String imageB64;
}
